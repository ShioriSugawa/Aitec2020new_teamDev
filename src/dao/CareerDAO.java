package dao;

import java.sql.Connection;
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
	 * 一人分の従業員データを取得
	 * @param employeeNumber 従業員番号
	 * @return 一人分の従業員データ（対象が存在しない場合はnullを返却）
	 * @throws SQLException
	 */
	public Career findOneCareer() throws SQLException{
		return null;
	}

	/**
	 * 業務経歴を登録するメソッド
	 * @param employeeNumber 従業員番号
	 * @param businessNumber 業務番号
	 * @param businessName 業務名
	 * @throws SQLException
	 */
	public void registerOneCareer() throws SQLException{
	}

	/**
	 * 業務経歴を更新するメソッド
	 * @param employeeNumber 従業員番号
	 * @param businessNumber 業務番号
	 * @param businessName 業務名
	 * @throws SQLException
	 */
	public void deleteOneCareer() throws SQLException{

	}

}
