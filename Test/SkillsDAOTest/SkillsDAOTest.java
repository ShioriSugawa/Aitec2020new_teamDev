package SkillsDAOTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ConnectionManagerForTest.ConnectionManagerTest;
import dao.SkillsDAO;
import model.Certification;
import model.Skill;

/**
 * Copyright 2020 ????????????????????????????
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 * SkillsDAOの一覧取得/登録/更新/削除の単体テスト
 * @author aitec
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)


public class SkillsDAOTest {

	//@Rule
	//public Timeout globalTimeout = Timeout.seconds(5);


	/**
	 * テスト用データ追加
	 *
	 */
	@Test
	public void test00_REGISTERTESTDATA() {
		Connection conn = null;
		try {
			conn = ConnectionManagerTest.getConnection();
			new RegisterTestEmployee().registerTestEmployee(conn);
			conn.commit();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (conn != null) {
					conn.close();
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * マスター登録有資格登録テスト（正常）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test01_00_registerMaster() throws SQLException {

		Connection connection = null;
		Certification ctf = null;
		String actualEmployeeNumber = null;
		String actualMstCode = null;
		String actualDate = null;

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerMaster("666666", "IPA001", "2020/06");
			connection.commit();



			//登録テストで登録したマスター登録有資格情報の取得
			Find find  = new Find();
			int ownedId = find.findOwnedCertificationId(connection,"666666", "IPA001", "2020/06");
			ctf = find.findOneMasterCertification(ownedId, connection);
			actualEmployeeNumber = ctf.getEmployeeNumber();
			actualMstCode = ctf.getCertiCode();
			actualDate = ctf.getCertiDate();


			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				connection.rollback();
				try {
					if (connection != null) {
						connection.close();
						}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		//------------
		// 結果チェック
		// ------------
		final String expectedEmployeeNumber = "666666" ;
		final String expectedMstCode = "IPA001" ;
		final String expectedDate = "2020/06";

		assertEquals(expectedEmployeeNumber, actualEmployeeNumber);
		assertEquals(expectedMstCode, actualMstCode);
		assertEquals(expectedDate, actualDate);
	}


	/**
	 * マスター登録有資格登録テスト（異常系：従業員番号外部キー制約違反）
	 * ）
	 * @throws SQLException
	 */
	@Test
	public void test01_01_registerMasterEmployeeNumeberForeignKeyError() throws SQLException {

		Connection connection = null;
		String actual = null;
		final String expected =	"ERROR: テーブル\"owned_certification\"への挿入、更新は外部キー制約\"owned_certification_employee_number_fkey\"に違反しています\n" +
								"  詳細: テーブル\"employee\"にキー(employee_number)=(666665)がありません";

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerMaster("666665", "IPA001", "2020/06");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}

	/**
	 * マスター登録有資格登録テスト（異常系：資格コード外部キー制約違反）
	 * ）
	 * @throws SQLException
	 */
	@Test
	public void test01_02_registerMasterMstCodeForeignKeyError() throws SQLException {

		Connection connection = null;
		String actual = null;
		final String expected =	"ERROR: テーブル\"owned_certification\"への挿入、更新は外部キー制約\"owned_certification_certification_code_fkey\"に違反しています\n" +
								"  詳細: テーブル\"certification\"にキー(certification_code)=(AAAAAA)がありません";

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerMaster("666666", "AAAAAA", "2020/06");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}


	/**
	 * マスター登録有資格登録テスト（異常系：従業員番号6桁より大きい）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test01_03_registerMasterEmployeeNumberOverError() throws SQLException {

		Connection connection_register = null;
		String actual = null;
		final String expected =	"ERROR: 値は型character varying(6)としては長すぎます";

		//登録テスト実行
		try {

			connection_register = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection_register);
			sklDAO.registerMaster("6666661", "IPA001", "2020/06");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection_register.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection_register != null) {
					connection_register.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}


	/**
	 * マスター登録有資格登録テスト（異常系：資格コード6桁より大きい）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test01_04_registerMasterMstCodeOverError() throws SQLException {

		Connection connection_register = null;
		String actual = null;
		final String expected =	"ERROR: 値は型character varying(6)としては長すぎます";

		//登録テスト実行
		try {

			connection_register = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection_register);
			sklDAO.registerMaster("666666", "IPA0010", "2020/06");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection_register.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection_register != null) {
					connection_register.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}


	/**
	 * マスター登録有資格登録テスト（異常系：認定日7桁より大きい）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test01_05_registerMasterMstDateOverError() throws SQLException {

		Connection connection_register = null;
		String actual = null;
		final String expected =	"ERROR: 値は型character varying(7)としては長すぎます";

		//登録テスト実行
		try {

			connection_register = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection_register);
			sklDAO.registerMaster("666666", "IPA001", "2020/060");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection_register.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection_register != null) {
					connection_register.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}

	/**
	 * その他資格登録テスト（正常）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test02_00_registerOther() throws SQLException {

		Connection connection = null;
		Certification octf = null;
		String actualEmployeeNumber = null;
		String actualGenre = null;
		String actualOtherName = null;
		String actualDate = null;

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerOther("666666", "IPA", "その他資格テスト", "2020/06");
			connection.commit();



			//登録テストで登録したその他資格情報の取得
			Find find  = new Find();
			int ownedId = find.findOwnedOtherCertificationId(connection,"666666", "IPA", "その他資格テスト", "2020/06");
			octf = find.findOneOtherCertification(ownedId, connection);
			actualEmployeeNumber = octf.getEmployeeNumber();
			actualGenre = octf.getCertiCode();
			actualOtherName = octf.getCertiName();
			actualDate = octf.getCertiDate();


			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				connection.rollback();
				try {
					if (connection != null) {
						connection.close();
						}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		//------------
		// 結果チェック
		// ------------
		final String expectedEmployeeNumber = "666666" ;
		final String expectedGenre = "IPA" ;
		final String expectedOtherName = "その他資格テスト";
		final String expectedDate = "2020/06";

		assertEquals(expectedEmployeeNumber, actualEmployeeNumber);
		assertEquals(expectedGenre, actualGenre);
		assertEquals(expectedOtherName, actualOtherName);
		assertEquals(expectedDate, actualDate);
	}


	/**
	 * その他資格登録テスト（異常系：従業員番号外部キー制約違反）
	 *
	 * @throws SQLException
	 *
	 */
	@Test
	public void test02_01_registerOtherEmployeeNumberForeignKeyError() throws SQLException {

		Connection connection = null;
		String actual = null;
		final String expected =	"ERROR: テーブル\"owned_other_certification\"への挿入、更新は外部キー制約\"owned_other_certification_employee_number_fkey\"に違反しています\n" +
								"  詳細: テーブル\"employee\"にキー(employee_number)=(666665)がありません";

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerOther("666665", "IPA", "その他資格テスト","2020/06");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}


	/**
	 * その他資格登録テスト（異常系：資格ジャンルコード外部キー制約違反）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test02_02_registerOtherOthGenreForeignKeyError() throws SQLException {

		Connection connection = null;
		String actual = null;
		final String expected =	"ERROR: テーブル\"owned_other_certification\"への挿入、更新は外部キー制約\"owned_other_certification_certification_genre_code_fkey\"に違反しています\n" +
								"  詳細: テーブル\"certification_genre\"にキー(certification_genre_code)=(IPZ)がありません";

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerOther("666666", "IPZ", "その他資格テスト","2020/06");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}

	/**
	 * その他資格登録テスト（異常系：従業員番号6桁より大きい）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test02_03_registerOtherEmployeeNumberOverError() throws SQLException {
		Connection connection = null;
		String actual = null;
		final String expected =	"ERROR: 値は型character varying(6)としては長すぎます";

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerOther("6666661", "IPA", "その他資格テスト","2020/06");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}

	/**
	 * その他資格登録テスト（異常系：資格ジャンルコード3桁より大きい）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test02_04_registerOtherOthGenreOverError() throws SQLException {
		Connection connection = null;
		String actual = null;
		final String expected =	"ERROR: 値は型character varying(3)としては長すぎます";

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerOther("666666", "IPAA", "その他資格テスト","2020/06");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}


	/**
	 * その他資格登録テスト（異常系：その他資格名100桁より大きい）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test02_05_registerOtherOthNameOverError() throws SQLException {
		Connection connection = null;
		String actual = null;
		final String expected =	"ERROR: 値は型character varying(100)としては長すぎます";

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerOther("666666", "IPA", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１","2020/06");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}

	/**
	 * その他資格登録テスト（異常系：認定日100桁より大きい）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test02_06_registerOtherOthDateOverError() throws SQLException {
		Connection connection = null;
		String actual = null;
		final String expected =	"ERROR: 値は型character varying(7)としては長すぎます";

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerOther("666666", "IPA", "その他資格テスト","2020/060");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}


	/**
	 * スキル登録テスト（正常）
	 *
	 * @throws SQLException
	 *
	 */
	@Test
	public void test03_00_registerSkill() throws SQLException {

		Connection connection = null;
		Skill skl = null;
		String actualEmployeeNumber = null;
		String actualGenre = null;
		String actualName = null;

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerSkill("666666", "LNG", "スキルテスト");
			connection.commit();



			//登録テストで登録したスキル情報の取得
			Find find  = new Find();
			int ownedId = find.findOwnedSkillId(connection,"666666", "LNG", "スキルテスト");
			skl = find.findOneSkill(ownedId, connection);
			actualEmployeeNumber = skl.getEmployeeNumber();
			actualGenre = skl.getGenreCode();
			actualName = skl.getSkillName();


			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				connection.rollback();
				try {
					if (connection != null) {
						connection.close();
						}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		//------------
		// 結果チェック
		// ------------
		final String expectedEmployeeNumber = "666666" ;
		final String expectedGenre = "LNG" ;
		final String expectedOtherName = "スキルテスト";

		assertEquals(expectedEmployeeNumber, actualEmployeeNumber);
		assertEquals(expectedGenre, actualGenre);
		assertEquals(expectedOtherName, actualName);
	}


	/**
	 * スキル登録テスト（異常系：従業員番号外部キー制約違反）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test03_01_registerSkillEmployeeNumberForeignKeyError() throws SQLException {

		Connection connection = null;
		String actual = null;
		final String expected =	"ERROR: テーブル\"owned_skill\"への挿入、更新は外部キー制約\"owned_skill_employee_number_fkey\"に違反しています\n" +
								"  詳細: テーブル\"employee\"にキー(employee_number)=(666665)がありません";

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerSkill("666665", "LNG", "スキルテスト");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}


	/**
	 * スキル登録テスト（異常系：スキルジャンルコード外部キー制約違反）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test03_02_registerSkillSklGenreForeignKeyError() throws SQLException {

		Connection connection = null;
		String actual = null;
		final String expected =	"ERROR: テーブル\"owned_skill\"への挿入、更新は外部キー制約\"owned_skill_skill_genre_code_fkey\"に違反しています\n" +
								"  詳細: テーブル\"skill_genre\"にキー(skill_genre_code)=(AAA)がありません";

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerSkill("666666", "AAA", "スキルテスト");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}


	/**
	 * スキル登録テスト（異常系：従業員番号6桁より大きい）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test03_03_registerSkillEmployeeNumberOverError() throws SQLException {

		Connection connection = null;
		String actual = null;
		final String expected =	"ERROR: 値は型character varying(6)としては長すぎます";

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerSkill("6666661", "LNG", "スキルテスト");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}

	/**
	 * スキル登録テスト（異常系：スキルジャンルコード3桁より大きい）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test03_04_registerSkillSklGenreOverError() throws SQLException {

		Connection connection = null;
		String actual = null;
		final String expected =	"ERROR: 値は型character varying(3)としては長すぎます";

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerSkill("666666", "LNGG", "スキルテスト");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}

	/**
	 * スキル登録テスト（異常系：スキル名100桁より大きい）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test03_05_registerSkillSklNameOverError() throws SQLException {

		Connection connection = null;
		String actual = null;
		final String expected =	"ERROR: 値は型character varying(100)としては長すぎます";

		//登録テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();

			SkillsDAO sklDAO = new SkillsDAO(connection);
			sklDAO.registerSkill("666666", "LNG", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１");
			//Exceptionが発生しなければ失敗
			fail();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}

	/**
	 * 保有マスタ資格IDによる資格保有データ1件の取得テスト（正常）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test04_getOwnedMst() throws SQLException {

		Connection connection = null;
		Find find = new Find();

		//テスト情報
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			int ownedId = find.findOwnedCertificationId(connection,"666666", "IPA001", "2020/06");
			Certification ctf = sklDAO.getOwnedMst(ownedId);


			//-------------------
			// 結果チェック
			//-------------------

			final String exceptedEmployeeNumber = "666666";
			final String actualEmployeeNumber = ctf.getEmployeeNumber();
			final String exceptedCertiCode = "IPA";
			final String actualCertiCode = ctf.getCertiCode();
			final String exceptedCertiGenre = "IPA";
			final String actualCertiGenre = ctf.getCertiGenre();
			final String exceptedMasterCode = "IPA001";
			final String actualMasterCode = ctf.getMasterCode();
			final String exceptedCertiName = "ITパスポート";
			final String actualCertiName = ctf.getCertiName();
			final String exceptedCertiDate = "2020/06";
			final String actualCertiDate = ctf.getCertiDate();

			assertEquals(exceptedEmployeeNumber, actualEmployeeNumber);
			assertEquals(exceptedCertiCode, actualCertiCode);
			assertEquals(exceptedCertiGenre, actualCertiGenre);
			assertEquals(exceptedMasterCode, actualMasterCode);
			assertEquals(exceptedCertiName, actualCertiName);
			assertEquals(exceptedCertiDate, actualCertiDate);

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 保有その他資格IDによるその他資格保有データ1件の取得
	 *
	 * @throws SQLException
	 */
	@Test
	public void test05_getOwnedOth() throws SQLException {

		Connection connection = null;

		//テスト情報
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			//テストデータ取得メソッド必要
			int ownedId = new Find().findOwnedOtherCertificationId(connection, "666666", "IPA","その他資格テスト", "2020/06");
			Certification othctf = sklDAO.getOwnedOth(ownedId);


			//-------------------
			// 結果チェック
			//-------------------

			final String exceptedEmployeeNumber = "666666";
			final String actualEmployeeNumber = othctf.getEmployeeNumber();
			final String exceptedCertiCode = "IPA";
			final String actualCertiCode = othctf.getCertiCode();
			final String exceptedCertiGenre = "IPA";
			final String actualCertiGenre = othctf.getCertiGenre();
			final String exceptedOtherName = "その他資格テスト";
			final String actualOtherName = othctf.getCertiName();
			final String exceptedOtherDate = "2020/06";
			final String actualOtherDate = othctf.getCertiDate();

			assertEquals(exceptedEmployeeNumber, actualEmployeeNumber);
			assertEquals(exceptedCertiCode, actualCertiCode);
			assertEquals(exceptedCertiGenre, actualCertiGenre);
			assertEquals(exceptedOtherName, actualOtherName);
			assertEquals(exceptedOtherDate, actualOtherDate);

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * スキルジャンル一覧取得テスト（正常）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test06_getGenre() throws SQLException {

		Connection connection = null;

		//テスト情報
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			//テストデータどうするか要検討
			List<Skill> genreList = sklDAO.getGenre();


			//-------------------
			// 結果チェック
			//-------------------

			assertNotNull(genreList);

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 資格ジャンル（コード＆名）一覧（マスタその他共用）取得テスト（正常）
	 * @throws SQLException
	 */
	@Test
	public void test07_getCertiGenre() throws SQLException {
		Connection connection = null;

		//テスト情報
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			List<Certification> certiGenreList = sklDAO.getCertiGenre();


			//-------------------
			// 結果チェック
			//-------------------

			assertNotNull(certiGenreList);

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * マスタ資格（コード＆名）一覧取得テスト（正常）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test08_getCertiName() throws SQLException {

		Connection connection = null;

		//テスト情報
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			List<Certification> certiNameList = sklDAO.getCertiName();


			//-------------------
			// 結果チェック
			//-------------------

			assertNotNull(certiNameList);

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 保有スキルIDによるスキル保有データ1件の取得テスト（正常）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test09_getOwnedSkill() throws SQLException {

		Connection connection = null;

		//テスト情報
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			int ownedId = new Find().findOwnedSkillId(connection, "666666", "LNG", "スキルテスト");
			Skill skl = sklDAO.getOwnedSkill(ownedId);


			//-------------------
			// 結果チェック
			//-------------------

			final String exceptedEmployeeNumber = "666666";
			final String actualEmployeeNumber = skl.getEmployeeNumber();

			final String exceptedGenreCode = "LNG";
			final String actualGenreCode = skl.getGenreCode();

			final String exceptedGenreName = "語学";
			final String actualGenreName = skl.getGenreName();

			final String exceptedSkillName = "スキルテスト";
			final String actualSkillName = skl.getSkillName();

			assertEquals(exceptedEmployeeNumber, actualEmployeeNumber);
			assertEquals(exceptedGenreCode, actualGenreCode);
			assertEquals(exceptedGenreName, actualGenreName);
			assertEquals(exceptedSkillName, actualSkillName);


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * スキルアップデートテスト（正常）
	 * @throws SQLException
	 */
	@Test
	public void test10_00_updateSkill() throws SQLException {

		Connection connection = null;

		//テスト情報
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			int ownedId = new Find().findOwnedSkillId(connection, "666666", "LNG", "スキルテスト");

			//スキルジャンル：LNG → BSN スキル名：スキルテスト → スキル更新
			sklDAO.updateSkill(ownedId, "BSN", "スキル更新");


			//-------------------
			// 結果チェック
			//-------------------

			Skill skl = new Find().findOneSkill(ownedId, connection);

			final String exceptedSkillGenreCode = "BSN";
			final String actualSkillGenreCode = skl.getGenreCode();

			final String exceptedSkillName = "スキル更新";
			final String actualSkillName = skl.getSkillName();

			assertEquals(exceptedSkillGenreCode, actualSkillGenreCode);
			assertEquals(exceptedSkillName, actualSkillName);


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * スキルアップデートテスト（異常系：スキルジャンルコード外部キー制約違反）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test10_01_updateSkillGenreCodeForeignKeyError() throws SQLException {

		Connection connection = null;
		String actual = "";
		String expected =	"ERROR: テーブル\"owned_skill\"への挿入、更新は外部キー制約\"owned_skill_skill_genre_code_fkey\"に違反しています\n" +
							"  詳細: テーブル\"skill_genre\"にキー(skill_genre_code)=(123)がありません";

		try {
			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			int ownedId = new Find().findOwnedSkillId(connection, "666666", "LNG", "スキルテスト");

			sklDAO.updateSkill(ownedId, "123", "テスト");
			//Exceptionが発生しなければ失敗
			fail();
			connection.commit();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);

	}

	/**
	 * スキルアップデートテスト（異常系：スキルジャンルコード3桁より大きい）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test10_02_updateSkillCodeOverError() throws SQLException {

		Connection connection = null;
		String actual = "";
		String expected = "ERROR: 値は型character varying(3)としては長すぎます";

		try {
			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			int ownedId = new Find().findOwnedSkillId(connection, "666666", "LNG", "スキルテスト");

			sklDAO.updateSkill(ownedId, "1234", "テスト");
			//Exceptionが発生しなければ失敗
			fail();
			connection.commit();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);

	}


	/**
	 * スキルアップデートテスト（異常系：スキル名100桁より大きい）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test10_03_updateSkillNameOverError() throws SQLException {

		Connection connection = null;
		String actual = "";
		String expected = "ERROR: 値は型character varying(100)としては長すぎます";

		try {
			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			int ownedId = new Find().findOwnedSkillId(connection, "666666", "LNG", "スキルテスト");

			sklDAO.updateSkill(ownedId, "123", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１");
			//Exceptionが発生しなければ失敗
			fail();
			connection.commit();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);

	}

	/**
	 * その他資格アップデート（正常）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test11_00_updateOthCerti() throws SQLException {

		Connection connection = null;

		//テスト情報
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			int ownedId = new Find().findOwnedOtherCertificationId(connection, "666666", "IPA", "その他資格テスト", "2020/06");

			//資格ジャンル：IPA → MCP 認定日： 2020/06 → 2019/06  資格名：その他資格テスト → その他更新
			sklDAO.updateOthCerti(ownedId, "MCP", "2019/06", "その他更新");


			//-------------------
			// 結果チェック
			//-------------------

			Certification ctf = new Find().findOneOtherCertification(ownedId, connection);

			final String exceptedCertiGenreCode = "MCP";
			final String actualCertiGenreCode = ctf.getCertiCode();

			final String exceptedOtherName = "その他更新";
			final String actualOtherName = ctf.getCertiName();

			final String expectedOtherDate = "2019/06";
			final String actualOtherDate = ctf.getCertiDate();

			assertEquals(exceptedCertiGenreCode, actualCertiGenreCode);
			assertEquals(exceptedOtherName, actualOtherName);
			assertEquals(expectedOtherDate, actualOtherDate);


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * その他資格アップデート（異常系：資格ジャンルコード外部キー制約違反）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test11_01_updateOthCertiCodeOverError() throws SQLException {

		Connection connection = null;
		String actual = "";
		String expected =	"ERROR: テーブル\"owned_other_certification\"への挿入、更新は外部キー制約\"owned_other_certification_certification_genre_code_fkey\"に違反しています\n" +
							"  詳細: テーブル\"certification_genre\"にキー(certification_genre_code)=(123)がありません";

		try {
			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			int ownedId = new Find().findOwnedOtherCertificationId(connection, "666666", "IPA", "その他資格テスト", "2020/06");

			sklDAO.updateOthCerti(ownedId, "123", "2000/01", "テスト");
			//Exceptionが発生しなければ失敗
			fail();
			connection.commit();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}


	/**
	 * その他資格アップデート（異常系：資格ジャンルコード3桁より大きい）
	 * @throws SQLException
	 */
	@Test
	public void test11_02_updateOthCertiCodeForeignKeyError() throws SQLException {

		Connection connection = null;
		String actual = "";
		final String expected = "ERROR: 値は型character varying(3)としては長すぎます";

		try {
			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			int ownedId = new Find().findOwnedOtherCertificationId(connection, "666666", "IPA", "その他資格テスト", "2020/06");

			sklDAO.updateOthCerti(ownedId, "SAM1", "2000/01", "テスト");
			//Exceptionが発生しなければ失敗
			fail();
			connection.commit();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);

	}

	/**
	 * その他資格アップデート（異常系：認定日7桁より大きい）
	 * @throws SQLException
	 */
	@Test
	public void test11_03_updateOthCertiDateOverError() throws SQLException {

		Connection connection = null;
		String actual = "";
		String expected = "ERROR: 値は型character varying(7)としては長すぎます";

		try {
			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			int ownedId = new Find().findOwnedOtherCertificationId(connection, "666666", "IPA", "その他資格テスト", "2020/06");

			sklDAO.updateOthCerti(ownedId, "SAM", "2000/010", "テスト");
			//Exceptionが発生しなければ失敗
			fail();
			connection.commit();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);

	}

	/**
	 * その他資格アップデート（異常系：資格名100桁より大きい）
	 * @throws SQLException
	 */
	@Test
	public void test11_04_updateOthCertiNameOverError() throws SQLException {

		Connection connection = null;
		String actual = "";
		String expected = "ERROR: 値は型character varying(100)としては長すぎます";

		try {
			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			int ownedId = new Find().findOwnedOtherCertificationId(connection, "666666", "IPA", "その他資格テスト", "2020/06");

			sklDAO.updateOthCerti(ownedId, "SAM", "2000/01", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１");
			//Exceptionが発生しなければ失敗
			fail();
			connection.commit();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);

	}

	/**
	 * マスター資格アップデート（正常）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test12_00_updateMstCerti() throws SQLException {

		Connection connection = null;

		//テスト情報
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			int ownedId = new Find().findOwnedCertificationId(connection, "666666", "IPA001", "2020/06");

			//認定日：2020/06 → 2019/06
			sklDAO.updateMstCerti(ownedId, "2019/06");


			//-------------------
			// 結果チェック
			//-------------------

			Certification ctf = new Find().findOneMasterCertification(ownedId, connection);

			final String exceptedDate = "2019/06";
			final String actualDate = ctf.getCertiDate();


			assertEquals(exceptedDate, actualDate);


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * その他資格アップデート（異常系：認定日7桁より大きい）
	 * @throws SQLException
	 */
	@Test
	public void test12_01_updateMstCertiDateOverError() throws SQLException {

		Connection connection = null;
		String actual = "";
		String expected = "ERROR: 値は型character varying(7)としては長すぎます";

		try {
			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			int ownedId = new Find().findOwnedCertificationId(connection, "666666", "IPA001", "2020/06");

			sklDAO.updateMstCerti(ownedId, "2000/010");
			//Exceptionが発生しなければ失敗
			fail();
			connection.commit();
		}catch(SQLException e) {
			connection.rollback();
			actual = e.getMessage();
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//------------------
		// 結果チェック
		//------------------
		assertEquals(expected, actual);
	}


	/**
	 * マスタ登録資格削除テスト（正常）
	 *
	 */
	@Test
	public void test13_deleteMaster() {

		Connection conn = null;
		try {
			conn = ConnectionManagerTest.getConnection();
			int ownedId = new Find().findOwnedCertificationId(conn, "666666", "IPA001", "2020/06");
			final String sql = "DELETE FROM owned_certification WHERE owned_certification_id =?";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setInt(1, ownedId);
    		ps.executeUpdate();
    		conn.commit();


    	}catch(SQLException e) {
    		e.printStackTrace();
    	}finally {
    		if(conn != null) {
    			try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
    		}
    	}
	}


	/**
	 * その他資格削除テスト（正常）
	 *
	 */
	@Test
	public void test14_deleteOther() {

		Connection conn = null;
		try {
			conn = ConnectionManagerTest.getConnection();
			int ownedId = new Find().findOwnedOtherCertificationId(conn, "666666", "IPA", "その他資格テスト", "2020/06");
			final String sql = "DELETE FROM owned_other_certification WHERE owned_other_certification_id =?";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setInt(1, ownedId);
    		ps.executeUpdate();
    		conn.commit();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}finally {
    		if(conn != null) {
    			try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
    		}
    	}
	}


	/**
	 * スキル削除テスト（正常）
	 *
	 */
	@Test
	public void test15_deleteSkill() {
		Connection conn = null;
		try {
			conn = ConnectionManagerTest.getConnection();
			int ownedId = new Find().findOwnedSkillId(conn, "666666", "LNG", "スキルテスト");
			final String sql = "DELETE FROM owned_skill WHERE owned_skill_id =?";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.setInt(1, ownedId);
    		ps.executeUpdate();
    		conn.commit();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}finally {
    		if(conn != null) {
    			try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
    		}
    	}
	}


	/**
	 * テストデータ削除
	 *
	 */
	@Test
	public void test99_DELETETESTDATA() {
		Connection conn = null;
		try {
			//テスト用従業員削除
			conn = ConnectionManagerTest.getConnection();
			final String sql = "DELETE FROM employee WHERE employee_number = '666666'";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		ps.executeUpdate();
    		conn.commit();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}finally {
    		if(conn != null) {
    			try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
    		}
    	}
    }
}

/**
 * テスト結果確認用データ取得クラス
 *
 * @author aitec
 *
 */
class Find{

	/**
	 * スキルデータ取得(1件)
	 *
	 * @param ownedId　保有資格ID
	 * @param conn　DB接続オブジェクト
	 * @return スキルデータ
	 * @throws SQLException
	 */
	public Skill findOneSkill(int ownedId, Connection conn) throws SQLException {

		Skill skl = null;
		final String sql = "SELECT * FROM owned_skill WHERE owned_skill_id = ?";

		//---------------------------
		//SQL発行
		//---------------------------
		try(PreparedStatement ps = conn.prepareStatement(sql)){

			ps.setInt(1, ownedId);

			try(ResultSet rs = ps.executeQuery()){
				//----------------------
				// 値の取得
				//----------------------
				if(rs.next()) {

					String employeeNumber = rs.getString("employee_number");
					String skillGenreCode = rs.getString("skill_genre_code");
					String skillName = rs.getString("skill_name");

					//結果を格納
					skl = new Skill(employeeNumber, skillGenreCode, null, skillName);
				}
			}
			return skl;
		}
	}

	/**
	 * その他資格データ取得(1件)
	 *
	 * @param ownedId　保有資格ID
	 * @param conn DB接続オブジェクト
	 * @return　その他資格データ
	 * @throws SQLException
	 */
	public Certification findOneOtherCertification(int ownedId, Connection conn) throws SQLException {

		Certification ctf = null;
		final String sql = "SELECT * FROM owned_other_certification WHERE owned_other_certification_id = ?";

		//---------------------------
		//SQL発行
		//---------------------------
		try(PreparedStatement ps = conn.prepareStatement(sql)){

			ps.setInt(1, ownedId);

			try(ResultSet rs = ps.executeQuery()){
				//----------------------
				// 値の取得
				//----------------------
				if(rs.next()) {

					String employeeNumber = rs.getString("employee_number");
					String certiGenreCode = rs.getString("certification_genre_code");
					String date = rs.getString("other_certification_date");
					String name = rs.getString("other_certification_name");

					//結果を格納
					ctf = new Certification(ownedId, employeeNumber, certiGenreCode, "", date, name);
				}
			}
			return ctf;
		}
	}

	/**
	 * マスター登録有資格データ取得(1件)
	 *
	 * @param ownedId　保有資格ID
	 * @param conn DB接続オブジェクト
	 * @return　マスター登録有資格データ
	 * @throws SQLException
	 */
	public Certification findOneMasterCertification(int ownedId, Connection conn) throws SQLException {

		Certification ctf = null;
		final String sql = "SELECT * FROM owned_certification WHERE owned_certification_id = ?";

		//---------------------------
		//SQL発行
		//---------------------------
		try(PreparedStatement ps = conn.prepareStatement(sql)){

			ps.setInt(1, ownedId);

			try(ResultSet rs = ps.executeQuery()){
				//----------------------
				// 値の取得
				//----------------------
				if(rs.next()) {

					String employeeNumber = rs.getString("employee_number");
					String certificationCode = rs.getString("certification_code");
					String date = rs.getString("certification_date");

					//結果を格納
					ctf = new Certification(ownedId, employeeNumber, certificationCode, "", date, "");
				}
			}
			return ctf;
		}
	}

	/**
	 * 保有資格ID取得
	 *
	 * @param conn　DB接続オブジェクト
	 * @param employeeNumber　従業員番号
	 * @param mstCode　資格コード
	 * @param mstDate　認定日
	 * @return　保有資格ID
	 * @throws SQLException
	 */
	public int findOwnedCertificationId(Connection conn, String employeeNumber, String mstCode, String mstDate) throws SQLException {

		int ownedId = 0;
		final String sql =	"SELECT owned_certification_id FROM owned_certification \r\n" +
							"WHERE employee_number=? AND certification_code=? AND certification_date=?";

		try (PreparedStatement ps = conn.prepareStatement(sql)){

			ps.setString(1, employeeNumber);
			ps.setString(2, mstCode);
			ps.setString(3, mstDate);

			try(ResultSet rs = ps.executeQuery()){

				if(rs.next()) {

					ownedId = rs.getInt("owned_certification_id");

				}
			}
		}
		return ownedId;
	}

	/**
	 * その他保有資格ID取得
	 *
	 * @param conn　DB接続オブジェクト
	 * @param employeeNumber　従業員番号
	 * @param otherGenre　資格ジャンルコード
	 * @param otherName　その他資格名
	 * @param otherDate　認定日
	 * @return　その他保有資格ID
	 * @throws SQLException
	 */
	public int findOwnedOtherCertificationId(Connection conn, String employeeNumber, String otherGenre, String otherName, String otherDate) throws SQLException {

		int ownedId = 0;
		final String sql =	"SELECT owned_other_certification_id FROM owned_other_certification \r\n" +
							"WHERE employee_number=? AND certification_genre_code=? AND other_certification_name=? AND other_certification_date=?";

		try (PreparedStatement ps = conn.prepareStatement(sql)){

			ps.setString(1, employeeNumber);
			ps.setString(2, otherGenre);
			ps.setString(3, otherName);
			ps.setString(4, otherDate);

			try(ResultSet rs = ps.executeQuery()){

				if(rs.next()) {

					ownedId = rs.getInt("owned_other_certification_id");

				}
			}
		}
		return ownedId;
	}

	/**
	 * 保有スキルID取得
	 *
	 * @param conn　DB接続オブジェクト
	 * @param employeeNumber　従業員番号
	 * @param skillGenre　スキルジャンルコード
	 * @param skillName　スキル名
	 * @return　保有スキルID
	 * @throws SQLException
	 */
	public int findOwnedSkillId(Connection conn, String employeeNumber, String skillGenre, String skillName) throws SQLException {

		int ownedId = 0;
		final String sql =	"SELECT owned_skill_id FROM owned_skill \r\n" +
							"WHERE employee_number=? AND skill_genre_code=? AND skill_name=?";

		try (PreparedStatement ps = conn.prepareStatement(sql)){

			ps.setString(1, employeeNumber);
			ps.setString(2, skillGenre);
			ps.setString(3, skillName);

			try(ResultSet rs = ps.executeQuery()){

				if(rs.next()) {

					ownedId = rs.getInt("owned_skill_id");

				}
			}
		}
		return ownedId;
	}
}

class RegisterTestEmployee{

	public void registerTestEmployee(Connection conn) throws SQLException {

		final String sql = "INSERT INTO employee VALUES('666666', 'SkillsDAOTest用', 'テスト用従業員', 'テスト', 2020, 1)";

		try(PreparedStatement ps = conn.prepareStatement(sql)){
			ps.executeUpdate();
		}
	}
}

