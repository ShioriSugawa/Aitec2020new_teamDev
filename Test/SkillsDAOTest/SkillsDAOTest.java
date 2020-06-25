package SkillsDAOTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
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

	@Rule
	public Timeout globalTimeout = Timeout.seconds(5);

	/**
	 * 保有マスタ資格IDによる資格保有データ1件の取得テスト（正常）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test00_getOwnedMst() throws SQLException {

		Connection connection = null;

		//テスト情報
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			//テストデータどうするか要検討
			Certification ctf = sklDAO.getOwnedMst(5);


			//-------------------
			// 結果チェック
			//-------------------

			final String exceptedEmployeeNumber = "100001";
			final String actualEmployeeNumber = ctf.getEmployeeNumber();
			final String exceptedCertiCode = "SAM";
			final String actualCertiCode = ctf.getCertiCode();
			final String exceptedCertiGenre = "サンプル1";
			final String actualCertiGenre = ctf.getCertiGenre();
			final String exceptedMasterCode = "SAM001";
			final String actualMasterCode = ctf.getMasterCode();
			final String exceptedCertiName = "資格1";
			final String actualCertiName = ctf.getCertiName();
			final String exceptedCertiDate = "2001/01";
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
	public void test01_getOwnedOth() throws SQLException {

		Connection connection = null;

		//テスト情報
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			//テストデータどうするか要検討
			Certification othctf = sklDAO.getOwnedOth(1);


			//-------------------
			// 結果チェック
			//-------------------

			final String exceptedEmployeeNumber = "100001";
			final String actualEmployeeNumber = othctf.getEmployeeNumber();
			final String exceptedCertiCode = "SAM";
			final String actualCertiCode = othctf.getCertiCode();
			final String exceptedCertiGenre = "サンプル1";
			final String actualCertiGenre = othctf.getCertiGenre();
			final String exceptedOtherName = "その他1";
			final String actualOtherName = othctf.getCertiName();
			final String exceptedOtherDate = "2011/11";
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
	public void test02_getGenre() throws SQLException {

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
	public void test03_getCertiGenre() throws SQLException {
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
	public void test03_getCertiName() throws SQLException {

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
	public void test04_getOwnedSkill() throws SQLException {

		Connection connection = null;

		//テスト情報
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			Skill skl = sklDAO.getOwnedSkill(1);


			//-------------------
			// 結果チェック
			//-------------------

			final String exceptedEmployeeNumber = "100001";
			final String actualEmployeeNumber = skl.getEmployeeNumber();

			final String exceptedGenreCode = "EXS";
			final String actualGenreCode = skl.getGenreCode();

			final String exceptedGenreName = "スキルジャンル1";
			final String actualGenreName = skl.getGenreName();

			final String exceptedSkillName = "スキル1";
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
	public void test05_updateSkill() throws SQLException {

		Connection connection = null;

		//テスト情報
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			//スキルジャンル：EXS → SKL スキル名：スキル1 → スキル更新
			sklDAO.updateSkill(1, "SKL", "スキル更新");


			//-------------------
			// 結果チェック
			//-------------------

			Skill skl = new Find().findOneSkill(1, connection);

			final String exceptedSkillGenreCode = "SKL";
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
	 * スキルアップデートテスト（異常系：スキルジャンルコード3桁より大きい）
	 *
	 * @throws SQLException
	 */
	@Test
	public void test05_01_updateSkillCodeOverError() throws SQLException {

		Connection connection = null;
		String actual = "";
		String expected = "ERROR: 値は型character varying(3)としては長すぎます";

		try {
			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);

			sklDAO.updateSkill(1, "1234", "テスト");
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
	public void test05_02_updateSkillNameOverError() throws SQLException {

		Connection connection = null;
		String actual = "";
		String expected = "ERROR: 値は型character varying(100)としては長すぎます";

		try {
			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);

			sklDAO.updateSkill(1, "123", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１");
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
	public void test06_updateOthCerti() throws SQLException {

		Connection connection = null;

		//テスト情報
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			//資格ジャンル：SAM → SMP 認定日： 2011/11 → 2010/10 資格名：その他1 → その他更新
			sklDAO.updateOthCerti(1, "SMP", "2010/10", "その他更新");


			//-------------------
			// 結果チェック
			//-------------------

			Certification ctf = new Find().findOneOtherCertification(1, connection);

			final String exceptedCertiGenreCode = "SMP";
			final String actualCertiGenreCode = ctf.getCertiCode();

			final String exceptedOtherName = "その他更新";
			final String actualOtherName = ctf.getCertiName();

			assertEquals(exceptedCertiGenreCode, actualCertiGenreCode);
			assertEquals(exceptedOtherName, actualOtherName);


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
	 * その他資格アップデート（異常系：資格ジャンルコード3桁より大きい（資格ジャンルマスターに登録の無いコード））
	 *
	 * @throws SQLException
	 */
	@Test
	public void test06_01_updateOthCertiCodeOverError() throws SQLException {

		Connection connection = null;
		String actual = "";
		String expected =		"ERROR: テーブル\"owned_other_certification\"への挿入、更新は外部キー制約\"owned_other_certification_certification_genre_code_fkey\"に違反しています\n" +
				"  詳細: テーブル\"certification_genre\"にキー(certification_genre_code)=(1234)がありません";

		try {
			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);

			sklDAO.updateOthCerti(1, "1234", "2000/01", "テスト");
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
	public void test06_02_updateOthCertiDateOverError() throws SQLException {

		Connection connection = null;
		String actual = "";
		String expected = "ERROR: 値は型character varying(7)としては長すぎます";

		try {
			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);

			sklDAO.updateOthCerti(1, "SAM", "2000/010", "テスト");
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
	public void test06_03_updateOthCertiNameOverError() throws SQLException {

		Connection connection = null;
		String actual = "";
		String expected = "ERROR: 値は型character varying(100)としては長すぎます";

		try {
			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);

			sklDAO.updateOthCerti(1, "SAM", "2000/01", "１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１２３４５６７８９０１");
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
	public void test07_updateMstCerti() throws SQLException {

		Connection connection = null;

		//テスト情報
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);
			//認定日：2001/01 → 2002/02
			sklDAO.updateMstCerti(5, "2002/02");


			//-------------------
			// 結果チェック
			//-------------------

			Certification ctf = new Find().findOneMasterCertification(5, connection);

			final String exceptedDate = "2002/02";
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
	public void test07_01_updateMstCertiDateOverError() throws SQLException {

		Connection connection = null;
		String actual = "";
		String expected = "ERROR: 値は型character varying(7)としては長すぎます";

		try {
			connection = ConnectionManagerTest.getConnection();
			SkillsDAO sklDAO = new SkillsDAO(connection);

			sklDAO.updateMstCerti(5, "2000/010");
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
}

