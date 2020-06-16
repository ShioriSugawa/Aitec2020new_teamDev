package dao;

import java.sql.Connection;
import java.sql.Date;
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
	public List<Career> getAllCareer(String employee_numver) throws SQLException{

		List<Career> careList = new ArrayList<>();
		final String sql = "";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = conn.prepareStatement(sql);
				ResultSet resultSet = pStmt.executeQuery()) {

			// -------------------
			// 値の取得
			// -------------------
			while(resultSet.next()) {

				//ResultSetから各値を取得
				int owned_career_id = resultSet.getInt("owned_career_id");
				Date business_start = resultSet.getDate("business_start");
				Date business_end = resultSet.getDate("business_end");
				String business_name = resultSet.getString("business_name");


				//結果をリストに格納


			}
		}

		return careList;
	}


	/**
	 * 該当従業員のマスター登録有資格一覧を取得
	 *
	 */
	public List<Employee> getMastercertification(String employee_number) throws SQLException{
		List<Employee> masterCertificationList = new ArrayList<>();
		final String sql = "";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = conn.prepareStatement(sql);
				ResultSet resultSet = pStmt.executeQuery()) {

			// -------------------
			// 値の取得
			// -------------------
			while(resultSet.next()) {

				//ResultSetから各値を取得
				int owned_certification_id = resultSet.getInt("owned_certification_id");
				String genreName = resultSet.getString("certification_genre_name");
				String certificationName = resultSet.getString("certification_name");
				Date certificationDate = resultSet.getDate("certification_date");


				//結果をリストに格納
				Employee emp = new Employee(genreName,certificationName,owned_certification_id,certificationDate);
				masterCertificationList.add(emp);
			}
		}
		return masterCertificationList;

	}

	/**
	 * 該当従業員のその他資格一覧を取得
	 *
	 */


	/**
	 * 該当従業員のスキル一覧を取得
	 */
}
