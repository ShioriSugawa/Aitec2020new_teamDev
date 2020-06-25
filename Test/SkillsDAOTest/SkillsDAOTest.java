package SkillsDAOTest;

import static org.junit.Assert.*;

import java.sql.Connection;
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

	@Test
	public void test02_getCertiGenre() throws SQLException {


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

	@Test
	public void test03_getCertiName() {

	}

	@Test
	public void test04_getOwnedSkill() {

	}

	@Test
	public void test05_updateSkill() {

	}

	@Test
	public void test06_updateOthCerti() {

	}

	@Test
	public void test07_updateMstCerti() {

	}

}
