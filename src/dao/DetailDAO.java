package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Career;
import model.Employee;

/**
 * システム名:自己紹介システム<br>
 * クラス概要:<br>
 * 従業員詳細情報画面に表示する資格スキルや業務経歴の情報取得のためデータベースを操作するクラス<br>
 *
 */
public class DetailDAO {

	/** データベースへの接続オブジェクト*/
	Connection conn;

	/**
	 * コンストラクタ
	 * @param conn データベースへの接続オブジェクト
	 */
	public DetailDAO(Connection conn) {
		this.conn = conn;
	}
	/**
	 * 該当従業員の業務経歴一覧を取得
	 * @param employee_number 業務経歴一覧を取得したい従業員の従業員番号
	 * @return 該当従業員の業務経歴一覧
	 * @throws SQLException
	 */
	public List<Career> getAllCareer(String employee_number) throws SQLException{

		List<Career> careList = new ArrayList<>();
		final String sql = "SELECT * FROM career  WHERE employee_number=? ORDER BY business_start DESC";

		// -------------------
		// SQL発行
		// -------------------

		try(PreparedStatement pStmt = conn.prepareStatement(sql)){

				pStmt.setString(1, employee_number);
				ResultSet resultSet = pStmt.executeQuery();

			// -------------------
			// 値の取得
			// -------------------
			while(resultSet.next()) {

				//ResultSetから各値を取得
				int owned_career_id = resultSet.getInt("owned_career_id");
				String business_start = resultSet.getString("business_start");
				String business_end = resultSet.getString("business_end");
				String business_name = resultSet.getString("business_name");


				//結果をリストに格納
				String id = String.valueOf(owned_career_id);
				 Career career = new Career(id, employee_number, business_name, business_start, business_end);

				careList.add(career);


			}
		}

		return careList;
	}


	/**
	 * 該当従業員の所持マスター登録有資格一覧を取得
	 * @param employee_number 所持マスター登録有資格一覧を取得したい従業員番号
	 * @return マスター登録有資格一覧
	 * @throws SQLException
	 */
	public List<Employee> getAllMasterCertification(String employee_number) throws SQLException{

		List<Employee> masterCertificationList = new ArrayList<>();
		final String sql = 		"SELECT owned_certification_id,certification_genre_name, certification_name, certification_date\r\n" +
								"FROM owned_certification\r\n" +
								"INNER JOIN certification\r\n" +
								"ON owned_certification.certification_code = certification.certification_code\r\n" +
								"INNER JOIN certification_genre\r\n" +
								"ON certification.certification_genre_code = certification_genre.certification_genre_code\r\n" +
								"WHERE employee_number = ? ORDER BY certification_date DESC";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
				pStmt.setString(1, employee_number);
				ResultSet resultSet = pStmt.executeQuery();

			// -------------------
			// 値の取得
			// -------------------
			while(resultSet.next()) {

				//ResultSetから各値を取得
				int owned_certification_id = resultSet.getInt("owned_certification_id");
				String genreName = resultSet.getString("certification_genre_name");
				String certificationName = resultSet.getString("certification_name");
				String certificationDate = resultSet.getString("certification_date");


				//結果をリストに格納
				Employee emp = new Employee(genreName,certificationName,owned_certification_id,certificationDate);
				masterCertificationList.add(emp);
			}
		}
		return masterCertificationList;

	}
	/**
	 * 該当従業員の所持その他保有資格一覧を取得
	 * @param employee_number　所持その他保有資格一覧を取得したい従業員番号
	 * @return　所持その他保有資格一覧
	 * @throws SQLException
	 */
	public List<Employee> getAllOthers(String employee_number) throws SQLException {

		List<Employee> otherCertificationList = new ArrayList<>();
		final String sql = 	"SELECT owned_other_certification_id,certification_genre_name, other_certification_name, other_certification_date\r\n" +
							"FROM owned_other_certification\r\n" +
							"INNER JOIN certification_genre\r\n" +
							"ON owned_other_certification.certification_genre_code = certification_genre.certification_genre_code\r\n" +
							"WHERE employee_number = ? ORDER BY other_certification_date DESC";


		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
				pStmt.setString(1, employee_number);
				ResultSet resultSet = pStmt.executeQuery();

			// -------------------
			// 値の取得
			// -------------------
			while(resultSet.next()) {

				//ResultSetから各値を取得
				int owned_other_certification_id = resultSet.getInt("owned_other_certification_id");
				String genreName = resultSet.getString("certification_genre_name");
				String otherCertificationName = resultSet.getString("other_certification_name");
				String otherCertificationDate = resultSet.getString("other_certification_date");


				//結果をリストに格納
				Employee emp = new Employee(genreName,otherCertificationName,owned_other_certification_id,otherCertificationDate);
				otherCertificationList.add(emp);
			}
		}
		return otherCertificationList;
	}


	/**
	 * 該当従業員の所持スキル一覧を取得
	 * @param employee_number　所持スキル一覧を取得したい従業員番号
	 * @return　所持スキル一覧
	 * @throws SQLException
	 */
	public List<Employee> getAllSkill(String employee_number) throws SQLException {

		List<Employee> skillList = new ArrayList<>();
		final String sql =	"SELECT owned_skill_id,skill_genre_name, skill_name\r\n" +
							"FROM owned_skill\r\n" +
							"INNER JOIN skill_genre\r\n" +
							"ON owned_skill.skill_genre_code = skill_genre.skill_genre_code\r\n" +
							"WHERE employee_number = ?";
		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
				pStmt.setString(1, employee_number);
				ResultSet resultSet = pStmt.executeQuery();

			// -------------------
			// 値の取得
			// -------------------
			while(resultSet.next()) {

				//ResultSetから各値を取得
				int owned_skill_id = resultSet.getInt("owned_skill_id");
				String genreName = resultSet.getString("skill_genre_name");
				String skillName = resultSet.getString("skill_name");


				//結果をリストに格納
				Employee emp = new Employee(genreName,skillName,owned_skill_id,"");
				skillList.add(emp);
			}
		}
		return skillList;
	}
}
