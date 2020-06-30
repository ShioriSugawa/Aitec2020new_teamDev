package model;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;

import dao.CareerDAO;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：業務経歴に関するビジネスロジックを記述するクラス<br>
 */

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
	 * @param businessNumber 業務経歴番号
	 * @return 業務経歴データ
	 * @throws ServletException
	 */
	public Career getCareer(String businessNumber) throws ServletException{
		CareerDAO careerDAO = new CareerDAO(connection);
		Career career = null;
		int businessNum = Integer.parseInt(businessNumber);

		try {
			// DB処理実行
			career = careerDAO.findOneCareer(businessNum);
		}catch (SQLException | IllegalArgumentException e) {
			throw new ServletException(e);
		}

		return career;
	}

	/**
	 * 業務経歴を登録するメソッド
	 * @param businessNumber 業務経歴番号
	 * @param employeeNumber 従業員番号
	 * @param startYear 開始年
	 * @param startMonth 開始月
	 * @param endYear 終了年
	 * @param endMonth 終了月
	 * @param businessName 業務名
	 * @param situation 状況
	 * @throws ServletException
	 */
	public void registerCareer(String employeeNumber, String startYear, String startMonth, String endYear, String endMonth, String businessName, String situation) throws ServletException{
		CareerDAO careerDAO = new CareerDAO(connection);
		@SuppressWarnings("unused")
		Career career = null;

		String businessStart = startYear +"/"+ startMonth;
		String businessEnd = null;
		int situationNum = Integer.parseInt(situation);

		if(endYear=="" || endMonth=="") {
		}else {
			businessEnd = endYear +"/"+ endMonth;
		}

		try {
			// DB処理実行
			//career = careerDAO.findOneCareer(businessNumber, employeeNumber);
			//登録処理を実施
			careerDAO.registerOneCareer(employeeNumber, businessStart, businessEnd, businessName, situationNum);
		}catch (SQLException | IllegalArgumentException e) {
			throw new ServletException(e);
		}

	}

	/**
	 * 業務経歴を更新するメソッド
	 * @param businessNumber 業務経歴番号
	 * @param employeeNumber 従業員番号
	 * @param startYear 開始年
	 * @param startMonth 開始月
	 * @param endYear 終了年
	 * @param endMonth 終了月
	 * @param businessName 業務名
	 * @param situation 状況
	 * @throws ServletException
	 */
	public void updateCareer(String businessNumber, String startYear, String startMonth, String endYear, String endMonth, String businessName, String situation) throws ServletException{
		CareerDAO careerDAO = new CareerDAO(connection);

		String businessStart = startYear +"/"+ startMonth;
		String businessEnd = null;
		int situationNum = Integer.parseInt(situation);
		int businessNum = Integer.parseInt(businessNumber);

		if(endYear=="" || endMonth=="") {
		}else {
			businessEnd = endYear +"/"+ endMonth;
		}

		try {
			// DB処理実行
			careerDAO.updateOneCareer(businessNum, businessStart, businessEnd, businessName, situationNum);
		}catch (SQLException | IllegalArgumentException e) {
			throw new ServletException(e);
		}

	}

	/**
	 * 業務経歴を削除するメソッド
	 * @param businessNumber 業務経歴番号
	 * @throws ServletException
	 */
	public void deleteCareer(String businessNumber) throws ServletException{
		CareerDAO careerDAO = new CareerDAO(connection);
		int businessNum = Integer.parseInt(businessNumber);

		try {
			// DB処理実行
			careerDAO.deleteOneCareer(businessNum);
		} catch (SQLException e) {
			throw new ServletException(e);
		}

	}

}
