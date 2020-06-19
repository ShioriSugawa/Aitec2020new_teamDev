package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import dao.DetailDAO;
import dao.EmployeeDAO;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 *  従業員に関するビジネスロジックを記述するクラス<br>
 */
/*
 * 修正内容まとめ
 *  2020/6/15 従業員登録メソッド所属追加対応
 *  2020/6/16 従業員更新メソッド所属追加対応
 *  2020/6/18 ArrayListソートメソッド追加
 *  2020/6/18 ソート追加に伴い従業員リストArrayListに
 *  2020/6/18 資格ジャンル、資格名、スキルジャンルのリスト取得メソッド追加
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
	public ArrayList<Employee> getEmployeeList() throws ServletException {
		EmployeeDAO empDAO = new EmployeeDAO(connection);
		ArrayList<Employee> empList = new ArrayList<Employee>();

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
	public Boolean registerEmployee(String employeeNumber, String employeeName, String employeeProfile,String employeeDeployment) throws ServletException {
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
			// 6/15 追加
			if(employeeDeployment.equals("所属を選択してください")) {
				throw new IllegalArgumentException("所属が未選択です。");
			}
			// DB処理実行
			emp = empDAO.findOneEmployee(employeeNumber);

			if(emp != null) {
				// 取得できたら（既に登録済の従業員番号であれば）エラーメッセージを表示
				hasError = true;
			} else {
				// 取得できなかったら（未登録の従業員番号であれば）登録処理を実施
				// 6/15 所属追加
				empDAO.registerOneEmployee(employeeNumber, employeeName, employeeProfile,employeeDeployment);
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
	public void updateEmployee(String employeeNumber, String employeeName, String employeeProfile, String employeeDeployment) throws ServletException {
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
			// 6/16 追加
			if(employeeDeployment.equals("所属を選択してください")) {
				throw new IllegalArgumentException("所属が未選択です。");
			}
			// DB処理実行
			empDAO.updateOneEmployee(employeeNumber, employeeName, employeeProfile, employeeDeployment);
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
	/**
	 * 業務経歴一覧を取得するメソッド
	 * @param employeeNumber 業務経歴一覧を取得したい従業員番号
	 * @return　業務経歴一覧
	 * @throws ServletException
	 */
	public List<Career> getCareerList(String employeeNumber) throws  ServletException{
		DetailDAO dtlDAO = new DetailDAO(connection);
		List<Career> list = null;

		try {
			//DB処理実行
			list = dtlDAO.getAllCareer(employeeNumber);
		} catch(SQLException e) {
			throw new ServletException(e);
		}
		return list;
	}

	/**
	 * 該当従業員の所持マスター登録有資格一覧を取得するメソッド
	 * @param employeeNumber　所持マスター登録有資格一覧を取得したい従業員番号
	 * @return　所持マスター登録有資格一覧
	 * @throws ServletException
	 */
	public List<Employee> getMasterCertificationList(String employeeNumber) throws ServletException{
		DetailDAO dtlDAO = new DetailDAO(connection);
		List<Employee> list = null;

		try {
			//DB処理実行
			list = dtlDAO.getAllMasterCertification(employeeNumber);
			} catch(SQLException e) {
				throw new ServletException(e);
		}
		return list;
	}

	/**
	 * 該当従業員の所持その他資格一覧を取得するメソッド
	 * @param employeeNumber　所持その他資格一覧を取得したい従業員番号
	 * @return　所持その他資格一覧
	 * @throws ServletException
	 */
	public List<Employee> getOtherCertificationList(String employeeNumber) throws ServletException{
		DetailDAO dtlDAO = new DetailDAO(connection);
		List<Employee> list = null;

		try {
			//DB処理実行
			list = dtlDAO.getAllOthers(employeeNumber);
			} catch(SQLException e) {
				throw new ServletException(e);
		}
		return list;
	}

	//資格ジャンル一覧
	public ArrayList<String> getGenreList() throws ServletException{
		EmployeeDAO empDAO = new EmployeeDAO(connection);
		ArrayList<String> list = null;

		try {
			//DB処理実行
			list = empDAO.getGenreList();
		}catch(SQLException e) {
			throw new ServletException(e);
	}
		return list;
	}

	//資格名一覧
	public ArrayList<String> getCertificationName(){


		return null;
	}

	/**
	 * 該当従業員の所持スキル一覧を取得するメソッド
	 * @param employeeNumber　所持スキル一覧を取得したい従業員番号
	 * @return　所持スキル一覧
	 * @throws ServletException
	 */
	public List<Employee> getSkillList(String employeeNumber) throws ServletException{
		DetailDAO dtlDAO = new DetailDAO(connection);
		List<Employee> list = null;

		try {
			//DB処理実行
			list = dtlDAO.getAllSkill(employeeNumber);
			} catch(SQLException e) {
				throw new ServletException(e);
		}
		return list;
	}

	//スキルジャンル一覧
	public ArrayList<String> getSkillGenreList(){



		return null;
	}

	/**
	 * 該当従業員の所持資格数を取得するメソッド
	 * @param employeeNumber　所持資格数を取得したい従業員番号
	 * @return 所持資格数
	 * @throws ServletException
	 */
	public int countCertification(String employeeNumber) throws ServletException {
		int countMaster = getMasterCertificationList(employeeNumber).size();
		int countOther =  getOtherCertificationList(employeeNumber).size();

		int count = countMaster + countOther;

		return count;
	}

	/**
	 * 従業員一覧ソート　従業員番号でソートor資格所持数でソート
	 * @param item　押下されたボタン　資格所持ランキング or 従業員番号でソート
	 * @param list　従業員一覧
	 */
	public void sortArrayList(String item, ArrayList<Employee> list) {
		switch(item){

			case "従業員番号":
				list.sort((a,b)-> a.getEmployeeNumber().compareTo(b.getEmployeeNumber()) );
				break;

			case "資格所持数":
				list.sort((a,b)-> b.getCount() - a.getCount() );
				break;
		}
	}

	//検索
	public ArrayList<Employee> searchEmployee(String deployment, String masterCertification, String otherCertification, String skillGenre, String skill) throws SQLException{

		EmployeeDAO empDAO = new EmployeeDAO(connection);
		ArrayList<Employee> searchList = new ArrayList<Employee>();


		searchList = empDAO.searchEmployee(deployment, masterCertification, otherCertification, skillGenre, skill);

		return searchList;
	}

}

