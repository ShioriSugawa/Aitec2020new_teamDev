package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Career;


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
	 * @param employeeNumber 従業員番号
	 * @return 業務経歴データ（対象が存在しない場合はnullを返却）
	 * @throws SQLException
	 */
	public Career findOneCareer(String businessNumber) throws SQLException{

		Career career = null;
		String sql = "SELECT * FROM employee WHERE business_number = ?";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)){

			pStmt.setString(1, businessNumber);

			try(ResultSet resultSet = pStmt.executeQuery()){
				// -------------------
				// 値の取得
				// -------------------
				if(resultSet.next()) {

					// ResultSetから各値を取得
					String business_number = resultSet.getString("business_number");
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
	public void registerOneCareer(String employeeNumber, String businessStart, String businessEnd, String businessName, String situation) throws SQLException{
		String sql = "INSERT INTO career (employee_number, business_start, business_end, business_name, situation) VALUES (?, ?, ?, ?, ?)";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)){
			pStmt.setString(1, employeeNumber);
			pStmt.setString(2, businessStart);
			pStmt.setString(3, businessEnd);
			pStmt.setString(4, businessName);
			pStmt.setString(5, situation);
			pStmt.executeUpdate();
		}
	}

	/**
	 * 業務経歴を更新するメソッド
	 * @param businessNumber 業務経歴番号
	 * @param employeeNumber 従業員番号
	 * @param businessStart 開始日
	 * @param businessEnd 終了日
	 * @param businessName 業務名
	 * @param situation 状況
	 * @throws SQLException
	 */
	public void updateOneCareer(String businessNumber, String employeeNumber, String businessStart, String businessEnd, String businessName, String situation) throws SQLException{
		String sql = "UPDATE career SET career_start = ?, career_end = ?, career_name = ?, situation = ? WHERE career_number = ?, employee_number = ?,";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)) {
			pStmt.setString(1, businessStart);
			pStmt.setString(2, businessEnd);
			pStmt.setString(3, businessName);
			pStmt.setString(4, situation);
			pStmt.setString(5, employeeNumber);
			pStmt.setString(6, businessNumber);
			pStmt.executeUpdate();
		}
	}

	/**
	 * 業務経歴を削除するメソッド
	 * @param businessNumber 業務経歴番号
	 * @throws SQLException
	 */
	public void deleteOneCareer(String businessNumber) throws SQLException{
		String sql = "DELETE FROM career WHERE business_number =?";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)) {
			pStmt.setString(1, businessNumber);
			pStmt.executeUpdate();
		}
	}

}
