package DetailDAOTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ConnectionManagerForTest.ConnectionManagerTest;
import dao.DetailDAO;
import model.Career;
import model.Employee;

/**
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 * DetailDAOの各項目一覧取得/資格所持数取得単体のテストクラス<br>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class DetaiDAOTest {


	/**
	 * 業務経歴一覧取得テスト（正常）
	 * @throws SQLException
	 */
	@Test
	public void test01_getAllCareer() throws SQLException {

		Connection connection = null;
		List<Career> careerList = null;

		try {

			connection = ConnectionManagerTest.getConnection();
			new RegisterInfo().registerInfo(connection);
			DetailDAO dtlDAO = new DetailDAO(connection);
			careerList = dtlDAO.getAllCareer("666666");

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try{
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
		assertNotNull(careerList);

	}


	/**
	 * 所持マスター登録有資格一覧取得テスト（正常）
	 * @throws SQLException
	 */
	@Test
	public void test02_getAllMasterCertification() throws SQLException {

		Connection connection = null;
		List<Employee> ownedCertificationList = null;

		try {

			connection = ConnectionManagerTest.getConnection();
			new RegisterInfo().registerInfo(connection);
			DetailDAO dtlDAO = new DetailDAO(connection);
			ownedCertificationList = dtlDAO.getAllMasterCertification("666666");

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try{
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
		assertNotNull(ownedCertificationList);
	}


	/**
	 * 所持その他資格一覧取得テスト（正常）
	 * @throws SQLException
	 */
	@Test
	public void test03_getAllOthers() throws SQLException {

		Connection connection = null;
		List<Employee> ownedOthersList = null;

		//テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();
			new RegisterInfo().registerInfo(connection);
			DetailDAO dtlDAO = new DetailDAO(connection);
			ownedOthersList = dtlDAO.getAllOthers("666666");

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try{
				if(connection != null) {
					connection.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

		//-------------------
		// 結果チェック
		//-------------------

		assertNotNull(ownedOthersList);
	}


	/**
	 * 所持スキル一覧取得テスト（正常）
	 * @throws SQLException
	 */
	@Test
	public void test04_getAllSkill() throws SQLException {

		Connection connection = null;
		List<Employee> ownedSkillList = null;

		//テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();
			new RegisterInfo().registerInfo(connection);
			DetailDAO dtlDAO = new DetailDAO(connection);
			ownedSkillList = dtlDAO.getAllSkill("666666");


		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			try{
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
		assertNotNull(ownedSkillList);


	}


	/**
	 * 資格所持数取得テスト（正常）
	 * @throws SQLException
	 */
	@Test
	public void test05_countCertification() throws SQLException {

		Connection connection = null;
		int result = 0;

		//テスト実行
		try {

			connection = ConnectionManagerTest.getConnection();
			DetailDAO dtlDAO = new DetailDAO(connection);
			new RegisterInfo().registerInfo(connection);
			result = dtlDAO.countCertification("666666");

		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(connection != null) {
				connection.close();
			}
		}
		//---------------------
		// 結果チェック
		//---------------------

		final int excepted = 2;		//マスター登録有資格1つ、その他資格1つをregisterInfoで追加しているため
		final int actual = result;
		assertEquals(excepted, actual);
	}
}

/**
 * テストデータ登録用クラス
 * @author aitec
 *
 */
class RegisterInfo{


	/**
	 * テストデータ登録メソッド従業員一人
	 * @param connection　DB接続オブジェクト
	 * @throws SQLException
	 */
	public void registerInfo(Connection connection) throws SQLException {

		//----------------------------------------------
		//テスト用従業員の登録
		//----------------------------------------------

		final String employeeSql = "INSERT INTO employee VALUES('666666', 'テスト', 'テストプロフィール', '部署1', null, 1)";
		try(PreparedStatement pStmt = connection.prepareStatement(employeeSql)){

			pStmt.executeUpdate();
		}


		//----------------------------------------------
		//業務経歴の登録
		//----------------------------------------------

		final String careerSql =	"INSERT INTO career (employee_number, business_start, business_end, business_name, situation) \r\n" +
									"VALUES ('666666', '2000/01','2001/01', 'テスト経歴', 0);";
		try(PreparedStatement pStmt = connection.prepareStatement(careerSql)){

			pStmt.executeUpdate();
		}

		//-----------------------------------------------
		//マスター登録有資格登録
		//-----------------------------------------------
		final String masterSql = 	"INSERT INTO owned_certification (employee_number, certification_code, certification_date)\r\n" +
									"VALUES('666666', 'SAM001', '2000/01')";
		try(PreparedStatement pStmt = connection.prepareStatement(masterSql)){

			pStmt.executeUpdate();
		}

		//------------------------------------------------
		//その他資格登録
		//------------------------------------------------
		final String otherSql = 	"INSERT INTO owned_other_certification (employee_number, certification_genre_code, other_certification_date, other_certification_name)\r\n" +
									"VALUES('666666', 'SAM', '2000/01','その他資格')";
		try(PreparedStatement pStmt = connection.prepareStatement(otherSql)){

			pStmt.executeUpdate();
		}

		//-------------------------------------------------
		//スキル登録
		//-------------------------------------------------
		final String skillSql = 	"INSERT INTO owned_skill (employee_number, skill_genre_code, skill_name)\r\n" +
									"VALUES('666666', 'EXS','スキルテスト')";
		try(PreparedStatement pStmt = connection.prepareStatement(skillSql)){

			pStmt.executeUpdate();
		}
	}
}
