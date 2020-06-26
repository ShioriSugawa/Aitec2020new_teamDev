package CareerLogicTest;


import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.junit.Test;

import ConnectionManagerForTest.ConnectionManagerTest;
import dao.CareerDAO;
import mockit.Expectations;
import mockit.Mocked;
import model.Career;
import model.CareerLogic;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 * CareerLogicの一覧/登録/更新/削除の単体テストクラス<br>
 */

/*
 * 登録（正常系：以前）
 * 登録（正常系：現在）
 * 取得（正常系）
 * 更新（正常系：以前）
 * 更新（正常系：現在）
 * 削除（正常系）
 */

public class CareerLogicTest {
	@Mocked
	CareerDAO careerDAO;

	//テスト実行時毎度更新する
	int x = 81;
	int y = 82;
	String xx ="81";
	String yy ="82";

	/**
	 * 業務経歴の登録テスト（正常系：以前）
	 *
	 * @throws SQLException
	 * @throws ServletException
	 */
	@Test
	public void registerCareer0() throws SQLException, ServletException {
		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{
				careerDAO.registerOneCareer("222222", "1990/04", "2000/06", "０１２３４５６７８９０１２３４５６７８９", 0);
		}};

		//実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CareerLogic careerLogic = new CareerLogic(connection);
			careerLogic.registerCareer("222222", "1990", "04", "2000", "06", "０１２３４５６７８９０１２３４５６７８９", "0");
		}catch (ServletException e) {
			throw e;
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 業務経歴の登録テスト（正常系：現在）
	 *
	 * @throws SQLException
	 * @throws ServletException
	 */
	@Test
	public void registerCareer1() throws SQLException, ServletException {
		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{
				careerDAO.registerOneCareer("222222", "1990/04", null, "０１２３４５６７８９０１２３４５６７８９", 1);
		}};

		//実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CareerLogic careerLogic = new CareerLogic(connection);
			careerLogic.registerCareer("222222", "1990", "04", "", "", "０１２３４５６７８９０１２３４５６７８９", "1");
		}catch (ServletException e) {
			throw e;
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 業務経歴（1つ）の取得テスト（正常系）
	 *
	 * @throws SQLException
	 */
	@Test
	public void getCareer() throws SQLException {
		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{
			Career career = new Career(xx, "222222", "開始日", "終了日", "業務名", "状況");
			careerDAO.findOneCareer(x);
			result = career;
		}};
		Career career = null;

		//実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CareerLogic careerLogic = new CareerLogic(connection);
			career = careerLogic.getCareer(xx);
		}catch (SQLException | ServletException e) {
			e.printStackTrace();
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		final String expectedEmployeeNumber = "222222";
		final String actualEmployeeNumber = career.getEmployeeNumber();
		final String expectedBusinessStart = "開始日";
		final String actualBusinessStart = career.getBusinessStart();
		final String expectedBusinessEnd = "終了日";
		final String actualBusinessEnd = career.getBusinessEnd();
		final String expectedBusinessName = "業務名";
		final String actualBusinessName = career.getBusinessName();
		final String expectedSituation = "状況";
		final String actualSituation = career.getSituation();

		// ------------
		// 結果チェック
		// ------------
		assertEquals(expectedEmployeeNumber, actualEmployeeNumber);
		assertEquals(expectedBusinessStart, actualBusinessStart);
		assertEquals(expectedBusinessEnd, actualBusinessEnd);
		assertEquals(expectedBusinessName, actualBusinessName);
		assertEquals(expectedSituation, actualSituation);
	}

	/**
	 * 業務経歴の更新テスト（正常系：以前の業務）
	 *
	 * @throws SQLException
	 * @throws ServletException
	 */
	@Test
	public void updateCareer0() throws SQLException, ServletException {
		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{
			careerDAO.updateOneCareer(x, "2010/08", "2020/10", "０１２３４５６７８９０１２３４５６７８９", 0);
		}};

		//テスト実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CareerLogic careerLogic = new CareerLogic(connection);
			careerLogic.updateCareer(xx, "2010", "08", "2020", "10", "０１２３４５６７８９０１２３４５６７８９", "0");
		}catch (ServletException e) {
			throw e;
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 業務経歴の更新テスト（正常系：現在の業務）
	 *
	 * @throws SQLException
	 * @throws ServletException
	 */
	@Test
	public void updateCareer1() throws SQLException, ServletException {
		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{
			careerDAO.updateOneCareer(y, "2010/08", null, "０１２３４５６７８９０１２３４５６７８９", 1);
		}};

		//テスト実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CareerLogic careerLogic = new CareerLogic(connection);
			careerLogic.updateCareer(yy, "2010", "08", "", "", "０１２３４５６７８９０１２３４５６７８９", "1");
		}catch (ServletException e) {
			throw e;
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 業務経歴の削除テスト（正常系）
	 *
	 * @throws SQLException
	 */
	@Test
	public void deleteCareer() throws SQLException {
		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{
				careerDAO.deleteOneCareer(x);
		}};

		//テスト実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CareerLogic careerLogic = new CareerLogic(connection);
			careerLogic.deleteCareer(xx);
		}catch (ServletException e) {
			e.printStackTrace();
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

				//テスト実行（合わせるため）
				try {
					connection = ConnectionManagerTest.getConnection();
					CareerLogic careerLogic = new CareerLogic(connection);
					careerLogic.deleteCareer(yy);
				}catch (ServletException e) {
					e.printStackTrace();
				}finally {
					try {
						if (connection != null) {
							connection.close();
						}
					}catch (SQLException e) {
						e.printStackTrace();
					}
				}

	}


}
