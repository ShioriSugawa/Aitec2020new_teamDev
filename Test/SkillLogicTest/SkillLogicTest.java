package SkillLogicTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.junit.Test;

import ConnectionManagerForTest.ConnectionManagerTest;
import dao.SkillsDAO;
import mockit.Expectations;
import mockit.Mocked;
import model.Skill;
import model.SkillLogic;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED / AitecTresure＆Toraja Co.,Ltd<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 * SkillLogicのデータ取得/登録/更新/削除の単体テストクラス<br>
 *
 */

public class SkillLogicTest {


	@Mocked
	SkillsDAO skillsDAO;


	/**
	 * IDで保有スキルデータ1件を取得（正常）
	 *
	 * @throws SQLException
	 * @throws ServletException
	 */
	@Test
	public void test01_getOwnedSkill() throws SQLException,ServletException {

		Connection connection = null;
		Skill skl = null;

		//DAOのJmockit
		new Expectations() {{

			Skill skl = new Skill(9999, "666666", "LNG", "語学", "スキルテスト");
			skillsDAO.getOwnedSkill(9999);
			result = skl;

		}};

		//実行
		try {
			connection = ConnectionManagerTest.getConnection();
			SkillLogic skillLogic = new SkillLogic(connection);
			skl = skillLogic.getOwnedSkill(9999);

		}catch(SQLException | ServletException e) {
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

		//---------------
		// 結果チェック
		//---------------

		final String expectedEmployeeNumber = "666666";
		final String actualEmployeeNumber = skl.getEmployeeNumber();

		final String expectedGenreCode = "LNG";
		final String actualGenreCode = skl.getGenreCode();

		final String expectedGenreName = "語学";
		final String actualGenreName = skl.getGenreName();

		final String expectedSkillName = "スキルテスト";
		final String actualSkillName = skl.getSkillName();

		assertEquals(expectedEmployeeNumber, actualEmployeeNumber);
		assertEquals(expectedGenreCode, actualGenreCode);
		assertEquals(expectedGenreName, actualGenreName);
		assertEquals(expectedSkillName, actualSkillName);
	}

	/**
	 * スキル登録テスト（正常）
	 *
	 * @throws SQLException
	 * @throws ServletException
	 */
	@Test
	public void test02_registerSkl() throws SQLException, ServletException {

		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{

			skillsDAO.registerSkill("666666", "LNG", "スキルテスト");

		}};

		//テスト実行
		try {
			connection = ConnectionManagerTest.getConnection();
			SkillLogic skillLogic = new SkillLogic(connection);
			skillLogic.registerSkl("666666", "LNG", "スキルテスト");

		}catch(ServletException e) {
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
	 * スキル更新テスト（正常）
	 *
	 * @throws SQLException
	 * @throws ServletException
	 */
	@Test
	public void test03_updateSkill() throws SQLException, ServletException {

		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{

			skillsDAO.updateSkill(9999, "BSN", "スキル更新");

		}};

		//テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();
			SkillLogic skillLogic = new SkillLogic(connection);
			skillLogic.updateSkill(9999, "BSN", "スキル更新");

		}catch(SQLException | ServletException e) {
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
	 * ジャンルリスト取得テスト（正常）
	 *
	 * @throws SQLException
	 * @throws ServletException
	 */
	@Test
	public void test04_getGenre() throws SQLException, ServletException {

		Connection connection = null;

		//DAOのJmockit
		new Expectations(){{

			List<Skill> genreList = new ArrayList<>();
			Skill skl1 = new Skill("LNG", "語学");
			Skill skl2 = new Skill("BSN", "ビジネス");
			genreList.add(skl1);
			genreList.add(skl2);

			skillsDAO.getGenre();
			result = genreList;

		}};

		//実行
		try {
			connection = ConnectionManagerTest.getConnection();
			SkillLogic skillLogic = new SkillLogic(connection);
			List<Skill> genreList = skillLogic.getGenre();

			//-----------
			// 結果チェック
			//-----------
			assertNotNull(genreList);

		}catch(SQLException | ServletException e) {
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
	 * スキル削除テスト（正常）
	 *
	 * @throws SQLException
	 * @throws ServletException
	 */
	@Test
	public void test05_sklDelete() throws SQLException, ServletException {

		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{

			skillsDAO.skillDelete(9999);

		}};

		//テスト実行
		try {
			connection = ConnectionManagerTest.getConnection();
			SkillLogic skillLogic = new SkillLogic(connection);
			skillLogic.sklDelete(9999);

		}catch(ServletException e) {
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

}
