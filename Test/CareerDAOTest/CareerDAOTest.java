package CareerDAOTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ConnectionManagerForTest.ConnectionManagerTest;
import dao.CareerDAO;
import model.Career;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED/AitecTresur＆Toraja Co.,Ltd<br>
 * クラス概要：<br>
 * CareerDAOの一覧/登録/更新/削除の単体テストクラス<br>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

/*
 * 登録（正常系：以前）
 * 取得（正常系：以前）
 * 登録（正常系：現在）
 * 取得（正常系：現在）
 * 登録（異常系：業務名100桁以上）
 * 更新（正常系：以前）
 * 更新（正常系：現在）
 * 更新（異常系：業務名100桁以上）
 * 削除（正常系）
 *
 * 登録したものは全て削除する
 */

public class CareerDAOTest {

	//テスト実行時毎度更新する
	int x = 78;
	int y = 79;

	/**
	 * 業務経歴の登録テスト（正常系：以前の業務）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test01_registerOneCareer() throws Exception {
		Connection connection_register = null;

		//登録テスト実行
		try {
			connection_register = ConnectionManagerTest.getConnection();
			CareerDAO careerDAO = new CareerDAO(connection_register);
			careerDAO.registerOneCareer("222222", "1111/11", "2222/22", "０１２３４５６７８９０１２３４５６７８９", 0);
			connection_register.commit();
		}catch (SQLException e) {
			connection_register.rollback();
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if (connection_register != null) {
					connection_register.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//登録テストで登録した業務経歴の取得
		Career career = null;
		Connection connection_findOne = null;
		FindOneCareer findOneCareerTest = new FindOneCareer();
		try {
			connection_findOne = ConnectionManagerTest.getConnection();
			int businessNumber = x;
			career = findOneCareerTest.findOneCareer(businessNumber, connection_findOne);
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if (connection_findOne != null) {
					connection_findOne.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// ------------
		// 結果チェック
		// ------------
		final String expectedEmployeeNumber = "222222";
		final String actualEmployeeNumber = career.getEmployeeNumber().trim();
		final String expectedBusinessStart = "1111/11";
		final String actualBusinessStart = career.getBusinessStart().trim();
		final String expectedBusinessEnd = "2222/22";
		final String actualBusinessEnd = career.getBusinessEnd().trim();
		final String expectedBusinessName = "０１２３４５６７８９０１２３４５６７８９";
		final String actualBusinessName = career.getBusinessName().trim();
		final String expectedSituation = "0";
		final String actualSituation = career.getSituation().trim();

		assertEquals(expectedEmployeeNumber, actualEmployeeNumber);
		assertEquals(expectedBusinessStart, actualBusinessStart);
		assertEquals(expectedBusinessEnd, actualBusinessEnd);
		assertEquals(expectedBusinessName, actualBusinessName);
		assertEquals(expectedSituation, actualSituation);

	}

	/**
	 * 業務経歴（1つ）の取得テスト（正常系：以前の業務）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test02_findOneCareer() throws Exception {
		Connection connection = null;
		Career career = null;

		//テスト実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CareerDAO careerDAO = new CareerDAO(connection);
			career = careerDAO.findOneCareer(x);
		}catch (SQLException e) {
			e.printStackTrace();
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

		// ------------
		// 結果チェック
		// ------------
		final String expected = "０１２３４５６７８９０１２３４５６７８９";
        final String actual = career.getBusinessName().trim();

        assertEquals(expected, actual);

	}

	/**
	 * 業務経歴の登録テスト（正常系：現在の業務）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test03_registerOneCareer() throws Exception {
		Connection connection_register = null;

		//登録テスト実行
		try {
			connection_register = ConnectionManagerTest.getConnection();
			CareerDAO careerDAO = new CareerDAO(connection_register);
			careerDAO.registerOneCareer("222222", "3333/33", "", "０１２３４５６７８９０１２３４５６７８９", 1);
			connection_register.commit();
		}catch (SQLException e) {
			connection_register.rollback();
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if (connection_register != null) {
					connection_register.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//登録テストで登録した業務経歴の取得
		Career career = null;
		Connection connection_findOne = null;
		FindOneCareer findOneCareerTest = new FindOneCareer();
		try {
			connection_findOne = ConnectionManagerTest.getConnection();
			int businessNumber = y;
			career = findOneCareerTest.findOneCareer(businessNumber, connection_findOne);
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if (connection_findOne != null) {
					connection_findOne.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// ------------
		// 結果チェック
		// ------------
		final String expectedEmployeeNumber = "222222";
		final String actualEmployeeNumber = career.getEmployeeNumber().trim();
		final String expectedBusinessStart = "3333/33";
		final String actualBusinessStart = career.getBusinessStart().trim();
		final String expectedBusinessEnd = "";
		final String actualBusinessEnd = career.getBusinessEnd().trim();
		final String expectedBusinessName = "０１２３４５６７８９０１２３４５６７８９";
		final String actualBusinessName = career.getBusinessName().trim();
		final String expectedSituation = "1";
		final String actualSituation = career.getSituation().trim();

		assertEquals(expectedEmployeeNumber, actualEmployeeNumber);
		assertEquals(expectedBusinessStart, actualBusinessStart);
		assertEquals(expectedBusinessEnd, actualBusinessEnd);
		assertEquals(expectedBusinessName, actualBusinessName);
		assertEquals(expectedSituation, actualSituation);

	}

	/**
	 * 業務経歴（1つ）の取得テスト（正常系：現在の業務）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test04_findOneCareer() throws Exception {
		Connection connection = null;
		Career career = null;

		//テスト実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CareerDAO careerDAO = new CareerDAO(connection);
			career = careerDAO.findOneCareer(y);
		}catch (SQLException e) {
			e.printStackTrace();
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

		// ------------
		// 結果チェック
		// ------------
		final String expected = "０１２３４５６７８９０１２３４５６７８９";
        final String actual = career.getBusinessName().trim();

        assertEquals(expected, actual);

	}

	/**
	 * 業務経歴の登録テスト（異常系：業務名100桁より大きい）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test05_registerOneCareerNameOverError() throws Exception {
		Connection connection = null;
		String actual = "";
		String expected = "ERROR: 値は型character varying(100)としては長すぎます";

		//テスト実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CareerDAO careerDAO = new CareerDAO(connection);
			careerDAO.registerOneCareer("222222", "4444/44", "5555/55", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０", 0);
			//Exceptionが発生しなければ失敗
			fail();
			connection.commit();
		}catch (SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// ------------
		// 結果チェック
		// ------------
		assertEquals(expected, actual);

	}

	/**
	 * 業務履歴の更新テスト（正常系：以前の業務）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test06_updateOneCareer() throws Exception {
		Connection connection_update = null;

		//更新テスト実行
		try {
			connection_update = ConnectionManagerTest.getConnection();
			CareerDAO careerDAO = new CareerDAO(connection_update);
			careerDAO.updateOneCareer(x, "6666/66", "7777/77", "９８７６５４３２１０９８７６５４３２１０", 0);
			connection_update.commit();
		}catch (SQLException e) {
			connection_update.rollback();
			e.printStackTrace();
		}finally {
			try {
				if (connection_update != null) {
					connection_update.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//更新テストで更新したデータの業務経歴の取得
		Career career = null;
		Connection connection_findOne = null;
		try {
			connection_findOne = ConnectionManagerTest.getConnection();
			CareerDAO careerDAO = new CareerDAO(connection_findOne);
			career = careerDAO.findOneCareer(x);
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if (connection_findOne != null) {
					connection_findOne.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// ------------
		// 結果チェック
		// ------------
		final String expectedBusinessStart = "6666/66";
		final String actualBusinessStart = career.getBusinessStart().trim();
		final String expectedBusinessEnd = "7777/77";
		final String actualBusinessEnd = career.getBusinessEnd().trim();
		final String expectedBusinessName = "９８７６５４３２１０９８７６５４３２１０";
		final String actualBusinessName = career.getBusinessName().trim();
		final String expectedSituation = "0";
		final String actualSituation = career.getSituation().trim();

		assertEquals(expectedBusinessStart, actualBusinessStart);
		assertEquals(expectedBusinessEnd, actualBusinessEnd);
		assertEquals(expectedBusinessName, actualBusinessName);
		assertEquals(expectedSituation, actualSituation);

	}

	/**
	 * 業務履歴の更新テスト（正常系：現在の業務）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test07_updateOneCareer() throws Exception {
		Connection connection_update = null;

		//更新テスト実行
		try {
			connection_update = ConnectionManagerTest.getConnection();
			CareerDAO careerDAO = new CareerDAO(connection_update);
			careerDAO.updateOneCareer(y, "8888/88", "", "９８７６５４３２１０９８７６５４３２１０", 1);
			connection_update.commit();
		}catch (SQLException e) {
			connection_update.rollback();
			e.printStackTrace();
		}finally {
			try {
				if (connection_update != null) {
					connection_update.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//更新テストで更新したデータの業務経歴の取得
		Career career = null;
		Connection connection_findOne = null;
		try {
			connection_findOne = ConnectionManagerTest.getConnection();
			CareerDAO careerDAO = new CareerDAO(connection_findOne);
			career = careerDAO.findOneCareer(y);
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if (connection_findOne != null) {
					connection_findOne.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// ------------
		// 結果チェック
		// ------------
		final String expectedBusinessStart = "8888/88";
		final String actualBusinessStart = career.getBusinessStart().trim();
		final String expectedBusinessEnd = "";
		final String actualBusinessEnd = career.getBusinessEnd().trim();
		final String expectedBusinessName = "９８７６５４３２１０９８７６５４３２１０";
		final String actualBusinessName = career.getBusinessName().trim();
		final String expectedSituation = "1";
		final String actualSituation = career.getSituation().trim();

		assertEquals(expectedBusinessStart, actualBusinessStart);
		assertEquals(expectedBusinessEnd, actualBusinessEnd);
		assertEquals(expectedBusinessName, actualBusinessName);
		assertEquals(expectedSituation, actualSituation);
	}

	/**
	 * 業務経歴の更新テスト（異常系：業務名100桁より大きい）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test08_updateOneCareerNameOverError() throws Exception {
		Connection connection = null;
		String actual = "";
		String expected = "ERROR: 値は型character varying(100)としては長すぎます";

		//テスト実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CareerDAO careerDAO = new CareerDAO(connection);
			careerDAO.updateOneCareer(x, "9999/99", "", "０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０", 0);
			//Exceptionが発生しなければ失敗
			fail();
			connection.commit();
		}catch (SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// ------------
		// 結果チェック
		// ------------
		assertEquals(expected, actual);

	}

	/**
	 * 業務経歴の削除テスト（正常）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test09_deleteOneCareer() throws Exception {
		Connection connection_delete = null;
		Connection connection_findOne = null;

		//削除テスト実行
		try {
			connection_delete = ConnectionManagerTest.getConnection();
			CareerDAO careerDAO = new CareerDAO(connection_delete);
			careerDAO.deleteOneCareer(x);
			connection_delete.commit();
		}catch (SQLException e) {
			connection_delete.rollback();
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if (connection_delete != null) {
					connection_delete.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

				//削除テスト実行(合わせるため)
				try {
					connection_delete = ConnectionManagerTest.getConnection();
					CareerDAO careerDAO = new CareerDAO(connection_delete);
					careerDAO.deleteOneCareer(y);
					connection_delete.commit();
				}catch (SQLException e) {
					connection_delete.rollback();
					e.printStackTrace();
					throw e;
				}finally {
					try {
						if (connection_delete != null) {
							connection_delete.close();
						}
					}catch (SQLException e) {
						e.printStackTrace();
					}
				}

		//削除テストで削除した業務経歴の取得
		try {
			connection_findOne = ConnectionManagerTest.getConnection();
			CareerDAO careerDAO = new CareerDAO(connection_findOne);
			Career career = null;
			career = careerDAO.findOneCareer(x);
			assertNull(career);
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if (connection_findOne != null) {
					connection_findOne.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}



/**
 * テスト用の業務経歴取得クラス（1つ）
 */
class FindOneCareer{

	/**
	 * 業務経歴取得
	 * @param businessNumber 業務経歴番号
	 * @param connection DB接続用コネクション
	 * @return 業務経歴データ
	 * @throws SQLException
	 */

	public Career findOneCareer(int businessNumber, Connection connection) throws SQLException{

		Career career = null;
		String sql = "SELECT * FROM career WHERE owned_career_id=?";

		// -------------------
		// SQL発行
		// -------------------
		try (PreparedStatement pStmt = connection.prepareStatement(sql)){

			pStmt.setInt(1, businessNumber);

			try (ResultSet resultSet = pStmt.executeQuery()){
				// -------------------
				// 値の取得
				// -------------------
				if (resultSet.next()) {

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

}
