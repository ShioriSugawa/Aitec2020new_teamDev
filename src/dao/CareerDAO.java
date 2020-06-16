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
	public Career findOneCareer(String businessNumber, String employeeNumber) throws SQLException{

		Career career = null;
		String sql = "SELECT * FROM employee WHERE business_number = ?, employee_number=?";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)){

			pStmt.setString(1, businessNumber);
			pStmt.setString(2, employeeNumber);

			try(ResultSet resultSet = pStmt.executeQuery()){
				// -------------------
				// 値の取得
				// -------------------
				if(resultSet.next()) {

					// ResultSetから各値を取得
					String business_number = resultSet.getString("business_number");
					String employee_number = resultSet.getString("employee_number");
					String business_name = resultSet.getString("business_name");
					String business_start = resultSet.getString("business_start");
					String business_end = resultSet.getString("business_end");
					// 結果を格納
					career = new Career(business_number, employee_number, business_name, business_start, business_end);

				}
			}

		}

		return career;
	}

	/**
	 * 業務経歴を登録するメソッド
	 * @param businessNumber 業務経歴番号
	 * @param employeeNumber 従業員番号
	 * @param businessName 業務名
	 * @param businessStart 開始日
	 * @param businessEnd 終了日
	 * @throws SQLException
	 */
	public void registerOneCareer(String businessNumber, String employeeNumber, String businessName, String businessStart, String businessEnd) throws SQLException{
		String sql = "INSERT INTO career (business_number, employee_number, business_name, business_start, business_end) VALUES (?, ?, ?, ?, ?)";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)){
			pStmt.setString(1, businessNumber);
			pStmt.setString(2, employeeNumber);
			pStmt.setString(3, businessName);
			pStmt.setString(4, businessStart);
			pStmt.setString(4, businessEnd);
			pStmt.executeUpdate();
		}
	}

	/**
	 * 業務経歴を更新するメソッド
	 * @param businessNumber 業務経歴番号
	 * @param employeeNumber 従業員番号
	 * @param businessName 業務名
	 * @param businessStart 開始日
	 * @param businessEnd 終了日
	 * @throws SQLException
	 */
	public void updateOneCareer(String businessNumber, String employeeNumber, String businessName, String businessStart, String businessEnd) throws SQLException{
		String sql = "UPDATE career SET career_name = ?, career_start = ?, career_end = ? WHERE career_number = ?, employee_number = ?,";

		// -------------------
		// SQL発行
		// -------------------
		try(PreparedStatement pStmt = connection.prepareStatement(sql)) {
			pStmt.setString(1, businessName);
			pStmt.setString(2, businessStart);
			pStmt.setString(3, businessEnd);
			pStmt.setString(4, employeeNumber);
			pStmt.setString(4, businessNumber);
			pStmt.executeUpdate();
		}
	}

	/**
	 * 従業員を削除するメソッド
	 * @param businessNumber 業務経歴番号
	 * @param employeeNumber 従業員番号
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
