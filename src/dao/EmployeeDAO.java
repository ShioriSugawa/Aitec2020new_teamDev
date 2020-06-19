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
 * 2020/6/18 ソート機能追加に伴い従業員リストArrayListに変更
 * 2020/6/18 資格ジャンル、資格名、スキルジャンル一覧取得メソッド追加
 * 2020/6/18 各種検索メソッド追加
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
	public ArrayList<Employee> findAllEmployee() throws SQLException {

		// 2020/6/18 ソートのためArrayListに
		ArrayList<Employee> empList = new ArrayList<>();
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

	//資格ジャンル一覧取得
	public ArrayList<String> getGenreList() throws SQLException{

		ArrayList<String> list = new ArrayList<String>();
		final String sql ="SELECT certification_genre_name FROM certification_genre";
		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)){
				ResultSet resultSet = pStmt.executeQuery();

			// -------------------
			// 値の取得
			// -------------------
			while(resultSet.next()) {

				// ResultSetから各値を取得
				String genreName = resultSet.getString("certification_genre_name");

				// 結果リストに格納
				list.add(genreName);
			}
		}
		return list;
	}

	//資格名一覧取得
		public ArrayList<String> getCertificationNameList() throws SQLException{

			ArrayList<String> list = new ArrayList<String>();
			final String sql ="";
			// -------------------
			// SQL発行
			// -------------------
			try(PreparedStatement pStmt = connection.prepareStatement(sql)){
					ResultSet resultSet = pStmt.executeQuery();

				// -------------------
				// 値の取得
				// -------------------
				while(resultSet.next()) {

					// ResultSetから各値を取得
					String certificationName = resultSet.getString("certification_name");

					// 結果リストに格納
					list.add(certificationName);
				}
			}
			return list;
		}

		/**
		 * 所属で従業員を検索するメソッド
		 * @param deployment　検索条件に指定された部署名
		 * @return 該当する部署に所属する従業員の一覧
		 * @throws SQLException
		 */
		public ArrayList<Employee> deploymentSearch(String deployment) throws SQLException {

			ArrayList<Employee> list = new ArrayList<Employee>();
			final String sql = "SELECT * FROM employee WHERE employee_deployment = ? AND employment = 1";

			// -------------------
			// SQL発行
			// -------------------
			try(PreparedStatement pStmt = connection.prepareStatement(sql)){

					pStmt.setString(1, deployment);
					ResultSet resultSet = pStmt.executeQuery();

				// -------------------
				// 値の取得
				// -------------------
				while(resultSet.next()) {

					// ResultSetから各値を取得
					String employee_number = resultSet.getString("employee_number");
					String employee_name = resultSet.getString("employee_name");
					String employee_profile = resultSet.getString("employee_profile");
					String employee_deployment = resultSet.getString("employee_deployment");

					// 結果リストに格納
					// 2020/6/15 所属追加
					// 2020/6/16 現在の業務経歴リスト追加
					List<String>careerList = getCareerList(employee_number);
					int count = countCertification(employee_number);
					Employee emp = new Employee(employee_number, employee_name, employee_profile, employee_deployment, count, careerList);
					list.add(emp);
				}
			}
			return list;
		}

		/**
		 * その他資格名で従業員を検索するメソッド　複数の該当する資格を所持している場合重複する　
		 * @param otherCertification　検索条件に入力されたその他資格名
		 * @return　該当するその他資格を保有する従業員のリスト
		 * @throws SQLException
		 */
		public ArrayList<Employee> otherSearch(String otherCertification) throws SQLException{

			ArrayList<Employee> list = new ArrayList<Employee>();
			final String sql = 		"SELECT DISTINCT employee.employee_number,employee_name,employee_profile,employee_deployment,employee_year,employment\n" +
									"FROM employee\n" +
									"INNER JOIN owned_other_certification\n" +
									"ON owned_other_certification.employee_number = employee.employee_number\n" +
									"WHERE other_certification_name LIKE ? AND employment = 1";

			// -------------------
			// SQL発行
			// -------------------
			try(PreparedStatement pStmt = connection.prepareStatement(sql)){

					pStmt.setString(1, "%" + otherCertification + "%");
					ResultSet resultSet = pStmt.executeQuery();

				// -------------------
				// 値の取得
				// -------------------
				while(resultSet.next()) {

					// ResultSetから各値を取得
					String employee_number = resultSet.getString("employee_number");
					String employee_name = resultSet.getString("employee_name");
					String employee_profile = resultSet.getString("employee_profile");
					String employee_deployment = resultSet.getString("employee_deployment");

					// 結果リストに格納
					List<String>careerList = getCareerList(employee_number);
					int count = countCertification(employee_number);
					Employee emp = new Employee(employee_number, employee_name, employee_profile, employee_deployment, count, careerList);
					list.add(emp);
				}
			}
			return list;
		}


		/**
		 * スキル名で従業員を検索するメソッド
		 * @param skillName
		 * @return
		 * @throws SQLException
		 */
		public ArrayList<Employee> skillNameSearch(String skillName) throws SQLException{
			ArrayList<Employee> list = new ArrayList<Employee>();
			final String sql = 		"SELECT DISTINCT employee.employee_number,employee_name,employee_profile,employee_deployment,employee_year,employment\n" +
									"FROM employee\n" +
									"INNER JOIN owned_skill\n" +
									"ON owned_skill.employee_number = employee.employee_number\n" +
									"WHERE skill_name LIKE ? AND employment = 1";

			// -------------------
			// SQL発行
			// -------------------
			try(PreparedStatement pStmt = connection.prepareStatement(sql)){

					pStmt.setString(1, "%" + skillName + "%");
					ResultSet resultSet = pStmt.executeQuery();

				// -------------------
				// 値の取得
				// -------------------
				while(resultSet.next()) {

					// ResultSetから各値を取得
					String employee_number = resultSet.getString("employee_number");
					String employee_name = resultSet.getString("employee_name");
					String employee_profile = resultSet.getString("employee_profile");
					String employee_deployment = resultSet.getString("employee_deployment");

					// 結果リストに格納
					List<String>careerList = getCareerList(employee_number);
					int count = countCertification(employee_number);
					Employee emp = new Employee(employee_number, employee_name, employee_profile, employee_deployment, count, careerList);
					list.add(emp);
				}
			}
			return list;

		}


}
