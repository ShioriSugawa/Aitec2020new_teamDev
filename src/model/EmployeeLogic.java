package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import dao.EmployeeDAO;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 *  従業員に関するビジネスロジックを記述するクラス<br>
 */
public class EmployeeLogic {

	/** データベースへの接続オブジェクト */
	Connection connection;

	/**
	 * コンストラクタ
	 * @param connection データベースへの接続オブジェクト
	 */
	public EmployeeLogic(Connection connection) {
		this.connection = connection;
	}

	/**
	 * 従業員一覧データを取得するメソッド
	 * @return 従業員一覧データ
	 * @throws ServletException
	 */
	public List<Employee> getEmployeeList() throws ServletException {
		EmployeeDAO empDAO = new EmployeeDAO(connection);
		List<Employee> empList = null;

		try {
			// DB処理実行
			empList = empDAO.findAllEmployee();
		} catch (SQLException e) {
			throw new ServletException(e);
		}

		return empList;
	}

	/**
	 * 従業員データを取得するメソッド
	 * @param employeeNumber 従業員番号
	 * @return 従業員データ
	 * @throws ServletException
	 */
	public Employee getEmployee(String employeeNumber) throws ServletException {
		EmployeeDAO empDAO = new EmployeeDAO(connection);
		Employee emp = null;

		try {
			// DB処理実行
			emp = empDAO.findOneEmployee(employeeNumber);
			// 存在チェック
			if(emp == null) {
				throw new IllegalArgumentException("従業員番号：" + employeeNumber + " の従業員データは存在しません。");
			}
		} catch (SQLException | IllegalArgumentException e) {
			throw new ServletException(e);
		}

		return emp;
	}

	/**
	 * 従業員を登録するメソッド
	 * @param employeeNumber 従業員番号
	 * @param employeeName 氏名
	 * @param employeeProfile プロフィール
	 * @return 登録エラー有無（true：登録失敗（既に同じ従業員番号のデータが存在）, false：登録成功）
	 * @throws ServletException
	 */
	public Boolean registerEmployee(String employeeNumber, String employeeName, String employeeProfile) throws ServletException {
		Boolean hasError = false;
		EmployeeDAO empDAO = new EmployeeDAO(connection);
		Employee emp = null;

		try {
			// 入力値桁数チェック
			if(employeeNumber == null){
				throw new IllegalArgumentException("従業員番号に不正な値が入力されています。");
			}
			if(employeeName == null){
				throw new IllegalArgumentException("氏名に不正な値が入力されています。");
			}
			if(employeeProfile == null){
				throw new IllegalArgumentException("プロフィールに不正な値が入力されています。");
			}
			if(employeeNumber.length() != 6) {
				throw new IllegalArgumentException("従業員番号が6桁ではありません。");
			}
			if(employeeName.length() > 30) {										//2020.05.28 10→30桁に変更
				throw new IllegalArgumentException("氏名が30桁を超えています。");
			}
			if(employeeName.strip().length() == 0){
				throw new IllegalArgumentException("氏名に値が入力されていません。");
			}
			if(employeeProfile.length() > 100) {
				throw new IllegalArgumentException("プロフィールが100桁を超えています。");
			}
			if(employeeProfile.strip().length() == 0){
				throw new IllegalArgumentException("プロフィールに値が入力されていません。");
			}
			// DB処理実行
			emp = empDAO.findOneEmployee(employeeNumber);

			if(emp != null) {
				// 取得できたら（既に登録済の従業員番号であれば）エラーメッセージを表示
				hasError = true;
			} else {
				// 取得できなかったら（未登録の従業員番号であれば）登録処理を実施
				empDAO.registerOneEmployee(employeeNumber, employeeName, employeeProfile);
				hasError = false;
			}
		} catch (SQLException | IllegalArgumentException e) {
			throw new ServletException(e);
		}

		return hasError;
	}

	/**
	 * 従業員を更新するメソッド
	 * @param employeeNumber 従業員番号
	 * @param employeeName 氏名
	 * @param employeeProfile プロフィール
	 * @throws ServletException
	 */
	public void updateEmployee(String employeeNumber, String employeeName, String employeeProfile) throws ServletException {
		EmployeeDAO empDAO = new EmployeeDAO(connection);

		try {
			// 入力値桁数チェック
			if(employeeName == null){
				throw new IllegalArgumentException("氏名に不正な値が入力されています。");
			}
			if(employeeProfile == null){
				throw new IllegalArgumentException("プロフィールに不正な値が入力されています。");
			}
			if(employeeName.strip().length() == 0){
				throw new IllegalArgumentException("氏名に値が入力されていません。");
			}
			if(employeeName.length() > 30) {											//2020.05.28 10→30桁に変更
				throw new IllegalArgumentException("氏名が30桁を超えています。");
			}
			if(employeeProfile.strip().length() == 0){
				throw new IllegalArgumentException("プロフィールに値が入力されていません。");
			}
			if(employeeProfile.length() > 100) {
				throw new IllegalArgumentException("プロフィールが100桁を超えています。");
			}
			// DB処理実行
			empDAO.updateOneEmployee(employeeNumber, employeeName, employeeProfile);
		} catch (SQLException | IllegalArgumentException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * 従業員を削除するメソッド
	 * @param employeeNumber 従業員番号
	 * @throws ServletException
	 */
	public void deleteEmployee(String employeeNumber) throws ServletException {
		EmployeeDAO empDAO = new EmployeeDAO(connection);

		try {
			// DB処理実行
			empDAO.deleteOneEmployee(employeeNumber);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}
}

