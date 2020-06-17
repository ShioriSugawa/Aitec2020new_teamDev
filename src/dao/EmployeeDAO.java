package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Employee;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 *  更新、削除等、データベースの各種操作を行うクラス<br>
 */
/*
 * 修正内容まとめ
 * 2020/6/15 register 所属追加対応
 * 2020/6/16 update 所属追加対応
 * 2020/6/16 一覧表示変更に伴い従業員全員分のデータを取得変更
 * 2020/6/16 現在の業務経歴一覧取得追加
 * 2020/6/16 資格所持数取得追加
 * 2020/6/17 従業員削除論理削除に変更対応
 *
 */
public class EmployeeDAO {

	/** データベースへの接続オブジェクト */
	Connection connection;

	/**
	 * コンストラクタ
	 * @param connection データベースの接続オブジェクト
	 */
	public EmployeeDAO (Connection connection) {
		this.connection = connection;
	}

	/**
	 * 従業員全員分のデータを取得
	 * @return 従業員リスト
	 * @throws SQLException
	 */
	public List<Employee> findAllEmployee() throws SQLException {

		List<Employee> empList = new ArrayList<>();
		// 2020/6/17 従業員削除を論理削除にするため変更
		String sql = "SELECT * FROM employee WHERE employment = 1 order by employee_number";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql);
				ResultSet resultSet = pStmt.executeQuery()) {

			// -------------------
			// 値の取得
			// -------------------
			while(resultSet.next()) {

				// ResultSetから各値を取得
				String employee_number = resultSet.getString("employee_number");
				String employee_name = resultSet.getString("employee_name");
				String employee_profile = resultSet.getString("employee_profile");
				// 6/15 追加
				String employee_deployment = resultSet.getString("employee_deployment");

				// 結果リストに格納
				// 2020/6/15 所属追加
				// 2020/6/16 現在の業務経歴リスト追加
				List<String>careerList = getCareerList(employee_number);
				int count = countCertification(employee_number);
				Employee emp = new Employee(employee_number, employee_name, employee_profile, employee_deployment, count, careerList);
				empList.add(emp);
			}
		}

		return empList;
	}

	/**
	 * 一人分の従業員データを取得
	 * @param employeeNumber 従業員番号
	 * @return 一人分の従業員データ（対象が存在しない場合はnullを返却）
	 * @throws SQLException
	 */
	public Employee findOneEmployee(String employeeNumber) throws SQLException {

		Employee emp = null;
		String sql = "SELECT * FROM employee WHERE employee_number=?";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)) {

			pStmt.setString(1, employeeNumber);

			try(ResultSet resultSet = pStmt.executeQuery()) {
				// -------------------
				// 値の取得
				// -------------------
				if(resultSet.next()) {

					// ResultSetから各値を取得
					String employee_number = resultSet.getString("employee_number");
					String employee_name = resultSet.getString("employee_name");
					String employee_profile = resultSet.getString("employee_profile");
					// 6/15　追加
					String employee_deployment = resultSet.getString("employee_deployment");
					// 結果を格納
					// 6/15 所属追加
					emp = new Employee(employee_number, employee_name, employee_profile, employee_deployment);
				}
			}

			return emp;
		}
	}

	/**
	 * 従業員を登録するメソッド
	 * @param employeeNumber 従業員番号
	 * @param employeeName 氏名
	 * @param employeeProfile プロフィール
	 * @throws SQLException
	 */
	public void registerOneEmployee(String employeeNumber, String employeeName, String employeeProfile, String employeeDeployment) throws SQLException {
		// 2020/6/17 従業員削除を論理削除にするため変更
		String sql = "INSERT INTO employee (employee_number, employee_name, employee_profile, employee_deployment, employment) VALUES (?, ?, ?, ?,'1')";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)) {
			pStmt.setString(1, employeeNumber);
			pStmt.setString(2, employeeName);
			pStmt.setString(3, employeeProfile);
			pStmt.setString(4, employeeDeployment);
			pStmt.executeUpdate();
		}
	}

	/**
	 * 従業員を更新するメソッド
	 * @param employeeNumber 従業員番号
	 * @param employeeName 氏名
	 * @param employeeProfie プロフィール
	 * @throws SQLException
	 */
	public void updateOneEmployee(String employeeNumber, String employeeName, String employeeProfie, String employeeDeployment) throws SQLException {
		String sql = "UPDATE employee SET employee_name = ?, employee_profile = ?, employee_deployment = ? WHERE employee_number = ?";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)) {
			pStmt.setString(1, employeeName);
			pStmt.setString(2, employeeProfie);
			pStmt.setString(3, employeeDeployment);
			pStmt.setString(4, employeeNumber);
			pStmt.executeUpdate();
		}
	}

	/**
	 * 従業員を削除するメソッド
	 * @param employeeNumber 従業員番号
	 * @throws SQLException
	 */
	public void deleteOneEmployee(String employeeNumber) throws SQLException {
		// 2020/6/17 従業員削除論理削除にするため変更
		String sql = "UPDATE employee SET employment = 0 WHERE employee_number = ?";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)) {
			pStmt.setString(1, employeeNumber);
			pStmt.executeUpdate();

		}
	}
	/**
	 * 該当従業員の現在の業務一覧取得
	 * @param employeeNumber 現在の業務一覧を取得したい従業員の従業員番号
	 * @return 該当従業員の現在の業務一覧
	 * @throws SQLException
	 */
	private List<String> getCareerList(String employeeNumber) throws SQLException{

		List<String> careerList = new ArrayList<>();
		String sql = "SELECT business_name\n" +
				"FROM career\n" +
				"WHERE employee_number = ? AND situation = 1;";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)){
				pStmt.setString(1, employeeNumber);
				ResultSet resultSet = pStmt.executeQuery();

			// -------------------
			// 値の取得
			// -------------------
			while(resultSet.next()) {

				// ResultSetから各値を取得
				String businessName = resultSet.getString("business_name");

				// 結果リストに格納
				careerList.add(businessName);
			}
		}
		return careerList;
	}

	private int countCertification(String employeeNumber) throws SQLException {
		DetailDAO dtlDAO = new DetailDAO(connection);
		int countMaster = dtlDAO.getAllMasterCertification(employeeNumber).size();
		int countOther =  dtlDAO.getAllOthers(employeeNumber).size();

		int count = countMaster + countOther;

		return count;
	}
}
