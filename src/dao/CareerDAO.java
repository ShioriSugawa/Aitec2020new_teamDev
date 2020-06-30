package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Career;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * Copyright 2020 AitecTresur＆Toraja Co.,Ltd<br>
 * システム名：自己紹介システム<br>
 * クラス概要：業務経歴に関する更新、削除等、データベースの各種操作を行うクラス<br>
 */

public class CareerDAO {

	/** データベースへの接続オブジェクト */
	Connection connection;

	/**
	 * コンストラクタ
	 * @param connection データベースの接続オブジェクト
	 */
	public CareerDAO (Connection connection) {
		this.connection = connection;
	}

	/**
	 * 業務経歴データを取得
	 * @param businessNumber 従業員番号
	 * @return 業務経歴データ（対象が存在しない場合はnullを返却）
	 * @throws SQLException
	 */
	public Career findOneCareer(int businessNumber) throws SQLException{

		Career career = null;
		String sql = "SELECT * FROM career WHERE owned_career_id = ?";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)){

			pStmt.setInt(1, businessNumber);

			try(ResultSet resultSet = pStmt.executeQuery()){
				// -------------------
				// 値の取得
				// -------------------
				if(resultSet.next()) {

					// ResultSetから各値を取得
					String business_number = resultSet.getString("owned_career_id");
					String employee_number = resultSet.getString("employee_number");
					String business_start = resultSet.getString("business_start");
					String business_end = resultSet.getString("business_end");
					String business_name = resultSet.getString("business_name");
					String situation = resultSet.getString("situation");
					// 結果を格納
					career = new Career(business_number, employee_number, business_start, business_end, business_name, situation);

				}
			}

		}

		return career;
	}

	/**
	 * 業務経歴を登録するメソッド
	 * @param employeeNumber 従業員番号
	 * @param businessStart 開始日
	 * @param businessEnd 終了日
	 * @param businessName 業務名
	 * @param situation 状況
	 * @throws SQLException
	 */
	public void registerOneCareer(String employeeNumber, String businessStart, String businessEnd, String businessName, int situation) throws SQLException{
		String sql = "INSERT INTO career (employee_number, business_start, business_end, business_name, situation) VALUES (?, ?, ?, ?, ?)";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)){
			pStmt.setString(1, employeeNumber);
			pStmt.setString(2, businessStart);
			pStmt.setString(3, businessEnd);
			pStmt.setString(4, businessName);
			pStmt.setInt(5, situation);
			pStmt.executeUpdate();
		}
	}

	/**
	 * 業務経歴を更新するメソッド
	 * @param businessNumber 業務経歴番号
	 * @param businessStart 開始日
	 * @param businessEnd 終了日
	 * @param businessName 業務名
	 * @param situation 状況
	 * @throws SQLException
	 */
	public void updateOneCareer(int businessNumber, String businessStart, String businessEnd, String businessName, int situation) throws SQLException{
		String sql = "UPDATE career SET business_start = ?, business_end = ?, business_name = ?, situation = ? WHERE owned_career_id = ?";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)) {
			pStmt.setString(1, businessStart);
			pStmt.setString(2, businessEnd);
			pStmt.setString(3, businessName);
			pStmt.setInt(4, situation);
			pStmt.setInt(5, businessNumber);
			pStmt.executeUpdate();
		}
	}

	/**
	 * 業務経歴を削除するメソッド
	 * @param businessNumber 業務経歴番号
	 * @throws SQLException
	 */
	public void deleteOneCareer(int businessNumber) throws SQLException{
		String sql = "DELETE FROM career WHERE owned_career_id =?";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)) {
			pStmt.setInt(1, businessNumber);
			pStmt.executeUpdate();
		}
	}

}
