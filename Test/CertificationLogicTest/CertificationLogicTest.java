package CertificationLogicTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import org.junit.Test;

import ConnectionManagerForTest.ConnectionManagerTest;
import dao.SkillsDAO;
import mockit.Expectations;
import mockit.Mocked;
import model.Certification;
import model.CertificationLogic;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 * CertificationLogicの一覧/登録/更新/削除/各種項目一覧取得/資格所持数取得/検索/ソートの単体テストクラス<br>
 */

/*
 * 資格ジャンルとコードの名前の一覧の取得（正常）✔仮
 * 資格のコードと名前の一覧の取得（正常）✔仮
 * 登録（正常：その他）✔
 * 登録（正常：マスタ）✔
 * 取得（正常：その他）✔
 * 取得（正常：マスタ）✔
 * 更新（正常：その他）✔
 * 更新（正常：マスタ）✔
 * 削除（正常：その他）
 * 削除（正常：マスタ）
 */

public class CertificationLogicTest {
	@Mocked
	SkillsDAO skillsDAO;

	//テスト実行時毎度更新する
	int x = 1;
	int y = 2;

	/**
	 * 資格ジャンルとコードの名前の一覧の取得
	 *
	 * @throws SQLException
	 */
	@Test
	public void getCertiGenre() throws SQLException {
		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{
			List<Certification>genreList = null;
			Certification certi1 = new Certification("AAA", "ジャンル1");
			Certification certi2 = new Certification("BBB", "ジャンル2");
			genreList.add(certi1);
			genreList.add(certi2);
			skillsDAO.getCertiGenre();
			result = genreList;
		}};

		//実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CertificationLogic certificationLogic = new CertificationLogic(connection);
			List<Certification>genreList = certificationLogic.getCertiGenre();

			// ------------
			// 結果チェック
			// ------------
			assertNotNull(genreList);

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
	}

	/**
	 * 資格のコードと名前の一覧の取得
	 *
	 * @throws SQLException
	 */
	@Test
	public void getCertiName() throws SQLException {
		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{
			List<Certification>nameList = null;
			Certification certi1 = new Certification("AAA001", "資格1");
			Certification certi2 = new Certification("AAA002", "資格2");
			nameList.add(certi1);
			nameList.add(certi2);
			skillsDAO.getCertiName();
			result = nameList;
		}};

		//実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CertificationLogic certificationLogic = new CertificationLogic(connection);
			List<Certification>nameList = certificationLogic.getCertiName();
			// ------------
			// 結果チェック
			// ------------
			assertNotNull(nameList);

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
	}

	/**
	 * その他資格の登録テスト
	 *
	 * @throws SQLException
     * @throws ServletException
	 */
	@Test
	public void registerOth() throws SQLException, ServletException {
		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{
			skillsDAO.registerOther("222222", "AAA", "1111/11", "その他資格1");
		}};

		//実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CertificationLogic certificationLogic = new CertificationLogic(connection);
			certificationLogic.registerOth("222222", "AAA", "1111/11", "その他資格1");
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
	 * マスタ資格の登録テスト
	 *
	 * @throws SQLException
     * @throws ServletException
	 */
	@Test
	public void registerMst() throws SQLException, ServletException {
		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{
			skillsDAO.registerMaster("222222", "AAA001", "2222/22");
		}};

		//実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CertificationLogic certificationLogic = new CertificationLogic(connection);
			certificationLogic.registerMst("222222", "AAA001", "2222/22");
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
	 * その他資格の取得テスト
	 *
	 * @throws SQLException
	 */
	@Test
	public void getOth() throws SQLException {
		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{
			Certification certi = new Certification(x, "222222", "ジャンルコード", "ジャンル名", "取得日", "その他資格名");
			skillsDAO.getOwnedOth(x);
			result = certi;
		}};
		Certification certi = null;

		//実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CertificationLogic certificationLogic = new CertificationLogic(connection);
			certi = certificationLogic.getOwnedOth(x);
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
	}

	/**
	 * マスタ資格の取得テスト
	 *
	 * @throws SQLException
	 */
	@Test
	public void getMst() throws SQLException {
		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{
			Certification certi = new Certification(y, "222222", "ジャンルコード", "ジャンル名", "資格コード", "マスタ資格名", "取得日");
			skillsDAO.getOwnedMst(y);
			result = certi;
		}};
		Certification certi = null;

		//実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CertificationLogic certificationLogic = new CertificationLogic(connection);
			certi = certificationLogic.getOwnedMst(x);
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
	}

	/**
	 * その他資格の更新テスト
	 *
	 * @throws SQLException
     * @throws ServletException
	 */
	@Test
	public void updateOth() throws SQLException, ServletException {
		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{
			skillsDAO.updateOthCerti(x, "BBB", "3333/33", "その他資格2");
		}};

		//実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CertificationLogic certificationLogic = new CertificationLogic(connection);
			certificationLogic.updateOth(x, "BBB", "3333/33", "その他資格2");
		}catch (ServletException e) {
			 throw e;
		}finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
	}

	/**
	 * マスタ資格の更新テスト
	 *
	 * @throws SQLException
     * @throws ServletException
	 */
	@Test
	public void updateMst() throws SQLException, ServletException {
		Connection connection = null;

		//DAOのJMockit
		new Expectations() {{
			skillsDAO.updateMstCerti(y, "4444/44");
		}};

		//実行
		try {
			connection = ConnectionManagerTest.getConnection();
			CertificationLogic certificationLogic = new CertificationLogic(connection);
			certificationLogic.updateMst(y, "4444/44");
		}catch (ServletException e) {
			 throw e;
		}finally {
           try {
               if (connection != null) {
                   connection.close();
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }

       }
	}

	/**
	 * その他資格の削除テスト
	 *
	 * @throws SQLException
	 */
	@Test
	public void deleteOth() throws Exception {

	}

	/**
	 * マスタ資格の削除テスト
	 *
	 * @throws SQLException
	 */
	@Test
	public void deleteMst() throws Exception {

	}
}
