package model;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;

import dao.CareerDAO;

public class CareerLogic {

	/** データベースへの接続オブジェクト */
	Connection connection;

	/**
	 * コンストラクタ
	 * @param connection データベースへの接続オブジェクト
	 */
	public CareerLogic(Connection connection) {
		this.connection = connection;
	}

	/**
	 * 業務経歴データを取得するメソッド
	 * @return 業務経歴データ
	 * @throws ServletException
	 */
	public Career getCareer(String businessNumber, String employeeNumber) throws ServletException{
		CareerDAO careerDAO = new CareerDAO(connection);
		Career career = null;

		try {
			// DB処理実行
			career = careerDAO.findOneCareer(businessNumber, employeeNumber);
		}catch (SQLException | IllegalArgumentException e) {
			throw new ServletException(e);
		}

		return career;
	}

	/**
	 * 従業員を登録するメソッド
	 * @param businessNumber 業務経歴番号
	 * @param employeeNumber 従業員番号
	 * @param businessName 業務名
	 * @param businessStart 開始日
	 * @param businessEnd 終了日
	 * @throws ServletException
	 */
	public void registerCareer(String businessNumber, String employeeNumber, String businessName, String businessStart, String businessEnd) throws ServletException{
		CareerDAO careerDAO = new CareerDAO(connection);
		@SuppressWarnings("unused")
		Career career = null;

		try {
			// DB処理実行
			career = careerDAO.findOneCareer(businessNumber, employeeNumber);
			//登録処理を実施
			careerDAO.registerOneCareer(businessNumber, employeeNumber, businessName, businessStart, businessEnd);
		}catch (SQLException | IllegalArgumentException e) {
			throw new ServletException(e);
		}

	}

	/**
	 * 従業員を更新するメソッド
	 * @param employeeNumber 従業員番号
	 * @param employeeName 氏名
	 * @param employeeProfile プロフィール
	 * @throws ServletException
	 */
	public void updateCareer(String businessNumber, String employeeNumber, String businessName, String businessStart, String businessEnd) throws ServletException{
		CareerDAO careerDAO = new CareerDAO(connection);

		try {
			// DB処理実行
			careerDAO.updateOneCareer(businessNumber, employeeNumber, businessName, businessStart, businessEnd);
		}catch (SQLException | IllegalArgumentException e) {
			throw new ServletException(e);
		}

	}

	/**
	 * 従業員を削除するメソッド
	 * @param employeeNumber 従業員番号
	 * @throws ServletException
	 */
	public void deleteCareer(String businessNumber) throws ServletException{
		CareerDAO careerDAO = new CareerDAO(connection);

		try {
			// DB処理実行
			careerDAO.deleteOneCareer(businessNumber);
		} catch (SQLException e) {
			throw new ServletException(e);
		}

	}

}
