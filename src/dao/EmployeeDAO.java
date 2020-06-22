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
				"WHERE employee_number = ? AND situation = 1";

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

	/**
	 * 資格所持数取得メソッド
	 * @param employeeNumber　資格所持数を取得したい従業員番号
	 * @return 該当従業員の資格所持数
	 * @throws SQLException
	 */
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
		 * 入力された検索条件で従業員を検索するメソッド　条件が二項目以上ある場合全てに該当する従業員のみ表示
		 * @param deployment　検索条件に入力された所属部署
		 * @param masterCertification　検索条件に入力されたマスタ登録有資格or
		 * @param otherCertification　検索条件に入力されたその他資格名
		 * @param skillGenre　検索条件に入力されたスキルジャンル
		 * @param skillName　検索条件に入力されたスキル名
		 * @return 検索条件に該当する従業員一覧
		 * @throws SQLException
		 */
		public ArrayList<Employee> searchEmployee(String deployment,String certificationGenre, String masterCertification, String otherCertification, String skillGenre, String skillName) throws SQLException{
			ArrayList<Employee> list = new ArrayList<Employee>();

			/*-------------SQL文構成詳細-------------------------------
			 * baseを基に検索条件の入力の有無に応じてWHERE句変更
			 *
			 * base : employee,owned_other_certification,owned_skill_テーブルを外部結合しemployment(在職フラグ)が1の従業員一覧を取得
			 * (↑全テーブル結合すればいい？？？？)
			 *
			 * 所属の検索条件が入力されていた場合 : where句に入力された所属部署を追加
			 * 資格ジャンルの検索条件が入力されていた場合 : where	句に入力された資格ジャンルのジャンルコードを追加
			 * マスター登録有資格の検索条件が入力されていた場合 : where句に入力されたマスター登録有資格の資格コードを追加
			 * その他資格名の検索条件が入力されていた場合 : where句に入力されたその他資格名を追加（前後にワイルドカードがあるため一部一致で抽出される）
			 * スキルジャンルの検索条件が入力されていた場合 : where句に入力されたスキルジャンルのジャンルコードを追加
			 * スキル名の検索条件が入力されていた場合 : where句に入力されたスキル名を追加（前後にワイルドカードがあるため一部一致で抽出される）
			 *----------------------------------------------------------
			 */
			final String base = 	"SELECT DISTINCT employee.employee_number,employee_name,employee_profile,employee_deployment,employee_year,employment\n" +
									"FROM employee\n" +
									"LEFT OUTER JOIN owned_other_certification\n" +
									"ON owned_other_certification.employee_number = employee.employee_number\n" +
									"LEFT OUTER JOIN owned_skill\n" +
									"ON owned_skill.employee_number = employee.employee_number\n" +
									"WHERE employment = 1 ";

			final String whereDeployment = " AND employee_deployment = ";
			final String whereCertificationGenre = " AND other_certification_code = ";
			final String whereMaster = " AND certification_code = ";
			final String whereOther = " AND other_certification_name LIKE ";
			final String whereSkillGenre = " AND skill_genre_code = ";
			final String whereSkill = " AND skill_name LIKE ";
			StringBuffer buf = new StringBuffer();

			buf.append(base);
			//所属の検索条件が入力されていた場合
			if(!(deployment.equals("所属を選択してください"))) {
				buf.append(whereDeployment);
				buf.append("\'" + deployment + "\'");
			}
			//資格ジャンルの検索条件が入力されていた場合
			if(!(masterCertification == null || masterCertification.equals(""))) {
				buf.append(whereCertificationGenre);
				buf.append("\'" + masterCertification + "\'");
			}
			//マスター登録有資格の検索条件が入力されていた場合
			if(!(masterCertification == null || masterCertification.equals(""))) {
				buf.append(whereMaster);
				buf.append("\'" + masterCertification + "\'");
			}
			//その他資格名の検索条件が入力されていた場合
			if(!(otherCertification.equals(""))) {
				buf.append(whereOther);
				buf.append("\'");
				buf.append("%");
				buf.append(otherCertification);
				buf.append("%");
				buf.append("\'");
			}
			//スキルジャンルの検索条件が入力されていた場合
			if(!(skillGenre == null || skillGenre.equals(""))) {
				buf.append(whereSkillGenre);
				buf.append("\'" + skillGenre + "\'");
			}
			//スキル名の検索条件が入力されていた場合
			if(!(skillName == null || skillName.equals(""))) {
				buf.append(whereSkill);
				buf.append("\'");
				buf.append("%");
				buf.append(skillName);
				buf.append("%");
				buf.append("\'");
			}

			final String sql = buf.toString();

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
