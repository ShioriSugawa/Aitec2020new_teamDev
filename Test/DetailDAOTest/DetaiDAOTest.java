package DetailDAOTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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


	@Test
	public void test01_getAllCareer() throws SQLException {

		Connection connection = null;
		List<Career> careerList = null;

		try {

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


	@Test
	public void test02_getAllMasterCertification() throws SQLException {

		Connection connection = null;
		List<Employee> ownedCertificationList = null;

		try {

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


	@Test
	public void test03_getAllOthers() throws SQLException {

		Connection connection = null;
		List<Employee> ownedOthersList = null;

		//テスト実行
		try {
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


	@Test
	public void test04_getAllSkill() throws SQLException {

		Connection connection = null;
		List<Employee> ownedSkillList = null;

		//テスト実行
		try {

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


	@Test
	public void test05_countCertification() throws SQLException {

		Connection connection = null;
		DetailDAO dtlDAO = new DetailDAO(connection);
		int result = 0;

		//テスト実行
		try {
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

		final int excepted = 1;
		final int actual = result;
		assertEquals(excepted, actual);
	}
}


class RegisterInfo{



	public void registerInfo(String employeeNumber, Connection connection) throws SQLException {

		//----------------------------------------------
		//テスト用従業員の登録
		//----------------------------------------------

		final String employeeSql = "";


		//----------------------------------------------
		//業務経歴の登録
		//----------------------------------------------

		final String careerSql =	"INSERT INTO career (employee_number, business_start, business_end, business_name, situation) \r\n" +
									"VALUES (?, '2000/01','2001/01', 'テスト経歴', 0);";
		try(PreparedStatement pStmt = connection.prepareStatement(careerSql)){

			pStmt.setString(1, employeeNumber);
			pStmt.executeUpdate();
		}

		//-----------------------------------------------
		//マスター登録有資格登録
		//-----------------------------------------------
		final String masterSql = "";
		try(PreparedStatement pStmt = connection.prepareStatement(masterSql)){

			pStmt.setString(1, employeeNumber);
			pStmt.executeUpdate();
		}

		//------------------------------------------------
		//その他資格登録
		//------------------------------------------------
		final String otherSql = "";
		try(PreparedStatement pStmt = connection.prepareStatement(otherSql)){

			pStmt.setString(1, employeeNumber);
			pStmt.executeUpdate();
		}

		//-------------------------------------------------
		//スキル登録
		//-------------------------------------------------
		final String skillSql = "";
		try(PreparedStatement pStmt = connection.prepareStatement(skillSql)){

			pStmt.setString(1, employeeNumber);
			pStmt.executeUpdate();
		}


	}
}
