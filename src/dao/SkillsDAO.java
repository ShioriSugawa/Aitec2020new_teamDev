package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Certification;
import model.Skill;

	/**
	 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
	 * システム名：自己紹介システム<br>
	 * クラス概要：<br>
	 * 資格スキルの登録・更新・削除のデータベース操作を行うクラス
	 */
public class SkillsDAO {

	/** データベースへの接続オブジェクト*/
	Connection conn;

	/**
	 * コンストラクタ
	 * @param conn データベースへの接続オブジェクト
	 */
	public SkillsDAO(Connection conn) {
		this.conn = conn;
	}

	//以下、保有IDによる検索
	/**
	 * マスタ資格保有IDによる資格保有データ1件の取得
	 * @param ownedId 情報を取得したいマスタ資格保有ID
	 * @return 該当のマスタ資格保有データ1件
	 * @throws SQLException
	 */
	public Certification getOwnedMst(int ownedId)throws SQLException {
		Certification ownedCertification=null;
		final String sql=
				"SELECT o.employee_number,o.certification_code,o.certification_date,"
				+ "m.certification_name,m.certification_genre_code,g.certification_genre_name"
				+ " FROM owned_certification o INNER JOIN certification m"
				+ " ON o.certification_code=m.certification_code"
				+ " INNER JOIN certification_genre g"
				+ " ON m.certification_genre_code=g.certification_genre_code"
				+ " WHERE owned_certification_id=? ";

	try(PreparedStatement pStmt = conn.prepareStatement(sql)){
		pStmt.setInt(1, ownedId);
		ResultSet resultSet = pStmt.executeQuery();

		if(resultSet.next()) {
			String employeeNumber=resultSet.getString("employee_number");
			String certiCode=resultSet.getString("certification_genre_code");
			String certiGenre=resultSet.getString("certification_genre_name");
			String masterCode=resultSet.getString("certification_code");
			String certiName=resultSet.getString("certification_name");
			String certiDate=resultSet.getString("certification_date");

			ownedCertification=new Certification(ownedId,employeeNumber,certiCode,certiGenre,masterCode,certiName,certiDate);
		}
	}
	return ownedCertification;
	}


	/**
	 * その他資格保有IDによるその他資格保有データ1件の取得
	 * @param ownedId 情報を取得したいその他資格保有ID
	 * @return 該当のその他資格保有データ1件
	 * @throws SQLException
	 */
	public Certification getOwnedOth(int ownedId)throws SQLException {
		Certification ownedOth=null;
		final String sql=
				"SELECT o.employee_number,o.certification_genre_code,g.certification_genre_name,o.other_certification_date,o.other_certification_name"
						+ " FROM owned_other_certification o INNER JOIN certification_genre g"
						+ " ON o.certification_genre_code=g.certification_genre_code"
						+ " WHERE owned_other_certification_id=? ";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			pStmt.setInt(1, ownedId);
			ResultSet resultSet = pStmt.executeQuery();

			if(resultSet.next()) {
				String employeeNumber=resultSet.getString("employee_number");
				String genreCode=resultSet.getString("certification_genre_code");
				String genreName=resultSet.getString("certification_genre_name");
				String othDate=resultSet.getString("other_certification_date");
				String othName=resultSet.getString("other_certification_name");

				ownedOth=new Certification(ownedId,employeeNumber,genreCode,genreName,othDate,othName);
			}
		}
		return ownedOth;
	}

	//スキルジャンルのリスト取得
	/**
	 * スキルジャンルのcodeとnameの一覧を取得
	 * @return スキルジャンルリスト
	 * @throws SQLException
	 */
	public List<Skill>getGenre()throws SQLException{
		List<Skill> genreList=new ArrayList<>();
		final String sql="SELECT * FROM skill_genre";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			ResultSet resultSet = pStmt.executeQuery();

			while(resultSet.next()) {
				String code=resultSet.getString("skill_genre_code");
				String name=resultSet.getString("skill_genre_name");

				Skill genre=new Skill(code,name);
				genreList.add(genre);
			}
		}
		return genreList;
	}

	//資格ジャンル（コード＆名）一覧用（マスタその他共用）
	/**
	 * 資格ジャンルのcodeとnameの一覧を取得
	 * @return 資格ジャンルリスト
	 * @throws SQLException
	 */
	public List<Certification> getCertiGenre()throws SQLException{
		List<Certification> certiGenreList=new ArrayList<>();
		final String sql="SELECT * FROM certification_genre";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			ResultSet resultSet = pStmt.executeQuery();

			while(resultSet.next()) {
				String code=resultSet.getString("certification_genre_code");
				String name=resultSet.getString("certification_genre_name");

				Certification genre=new Certification(code,name);
				certiGenreList.add(genre);
			}
		}
		return certiGenreList;
	}

	//マスタ資格（コード＆名）一覧用
	/**
	 * マスタ資格のcodeとnameの一覧を取得
	 * @return マスタ資格リスト
	 * @throws SQLException
	 */
	public List<Certification> getCertiName()throws SQLException{
		List<Certification> certiNameList=new ArrayList<>();
		final String sql="SELECT * FROM certification";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			ResultSet resultSet = pStmt.executeQuery();

			while(resultSet.next()) {
				String code=resultSet.getString("certification_code");
				String name=resultSet.getString("certification_name");

				Certification names=new Certification(code,name);
				certiNameList.add(names);
			}
		}
		return certiNameList;
	}

	/**
	 * スキル保有IDによるスキル保有データ1件の取得
	 * @param owned_skill_id 取得したいスキル保有データのID
	 * @return スキル保有データ1件
	 * @throws SQLException
	 */
	public Skill getOwnedSkill(int ownedId)throws SQLException {
		Skill ownedSkill=null;
		final String sql=
				"SELECT o.employee_number,o.skill_genre_code,g.skill_genre_name,o.skill_name"
						+ " FROM owned_skill o INNER JOIN skill_genre g"
						+ " ON o.skill_genre_code=g.skill_genre_code"
						+ " WHERE owned_skill_id=? ";

	try(PreparedStatement pStmt = conn.prepareStatement(sql)){
		pStmt.setInt(1, ownedId);
		ResultSet resultSet = pStmt.executeQuery();

		if(resultSet.next()) {
		String employeeNumber=resultSet.getString("employee_number");
		String genreCode=resultSet.getString("skill_genre_code");
		String genreName=resultSet.getString("skill_genre_name");
		String skillName=resultSet.getString("skill_name");

		ownedSkill=new Skill(ownedId,employeeNumber,genreCode,genreName,skillName);
		}
	}
		return ownedSkill;
	}

	//スキルアップデート
	/**
	 * スキル保有情報を更新するメソッド
	 * @param ownedId スキル保有ID
	 * @param skillGenre スキルジャンル
	 * @param skillName スキル名
	 * @throws SQLException
	 */
	public void updateSkill(int ownedId,String skillGenre,String skillName)throws SQLException{
		//String ownedIdS=String.valueOf( ownedId );
		String sql="UPDATE owned_skill SET skill_genre_code=?,skill_name=? WHERE owned_skill_id=?";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			pStmt.setString(1, skillGenre);
			pStmt.setString(2, skillName);
			pStmt.setInt(3, ownedId);
			pStmt.executeUpdate();
		}
	}

	//その他資格アップデート
	/**
	 * その他資格保有情報を更新するメソッド
	 * @param ownedId その他資格保有ID
	 * @param genCode 資格ジャンルコード
	 * @param othDate 取得日
	 * @param othName その他資格名
	 * @throws SQLException
	 */
	public void updateOthCerti(int ownedId, String genCode, String othDate, String othName)throws SQLException {
		String sql="UPDATE owned_other_certification "
				+ "SET certification_genre_code=?,"
				+ "other_certification_date=?,other_certification_name=? WHERE owned_other_certification_id=?";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			pStmt.setString(1, genCode);
			pStmt.setString(2, othDate);
			pStmt.setString(3, othName);
			pStmt.setInt(4, ownedId);
			pStmt.executeUpdate();
		}
	}

	//マスタ資格アップデート
	/**
	 * マスタ資格保有情報を更新するメソッド
	 * @param ownedId マスタ資格保有ID
	 * @param mcDate 取得日
	 * @throws SQLException
	 */
	public void updateMstCerti(int ownedId, String mcDate)throws SQLException {
		String sql="UPDATE owned_certification SET certification_date=? WHERE owned_certification_id=?";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			pStmt.setString(1, mcDate);
			pStmt.setInt(2, ownedId);
			pStmt.executeUpdate();
		}
	}

	//マスタ資格登録
	/**
	 * マスタ資格保有情報を登録するメソッド
	 * @param eNum 従業員番号
	 * @param mstCode マスタ資格コード
	 * @param mstDate 取得日
	 * @throws SQLException
	 */
	public void registerMaster(String eNum, String mstCode, String mstDate)throws SQLException {
		String sql="INSERT INTO owned_certification(employee_number,certification_code,certification_date)VALUES(?,?,?)";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			pStmt.setString(1, eNum);
			pStmt.setString(2, mstCode);
			pStmt.setString(3, mstDate);
			pStmt.executeUpdate();
		}
	}

	//その他資格登録
	/**
	 * その他資格保有情報を登録するメソッド
	 * @param eNum 従業員番号
	 * @param othGenre 資格ジャンルコード
	 * @param othDate 取得日
	 * @param othName その他資格名
	 * @throws SQLException
	 */
	public void registerOther(String eNum, String othGenre, String othName, String othDate)throws SQLException {
		String sql="INSERT INTO owned_other_certification(employee_number,certification_genre_code,other_certification_date,other_certification_name)VALUES(?,?,?,?)";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			pStmt.setString(1, eNum);
			pStmt.setString(2, othGenre);
			pStmt.setString(3, othDate);
			pStmt.setString(4, othName);
			pStmt.executeUpdate();
		}
	}

	//スキル登録
	/**
	 * スキル保有情報を登録するメソッド
	 * @param eNum 従業員番号
	 * @param sklGenre スキルジャンルコード
	 * @param sklName スキル名
	 * @throws SQLException
	 */
	public void registerSkill(String eNum, String sklGenre, String sklName)throws SQLException {
		String sql="INSERT INTO owned_skill(employee_number,skill_genre_code,skill_name)VALUES(?,?,?)";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			pStmt.setString(1, eNum);
			pStmt.setString(2, sklGenre);
			pStmt.setString(3, sklName);
			pStmt.executeUpdate();
		}
	}

	/**
	 * マスタ資格保有情報を削除するメソッド
	 * @param mcId マスタ資格保有ID
	 * @throws SQLException
	 */
	public void masterDelete(int mcId) throws SQLException{
		String sql="DELETE FROM owned_certification WHERE owned_certification_id=?";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			pStmt.setInt(1, mcId);
			pStmt.executeUpdate();
		}
	}

	/**
	 * その他資格保有情報を削除するメソッド
	 * @param ocId その他資格保有ID
	 * @throws SQLException
	 */
	public void otherDelete(int ocId) throws SQLException{
		String sql="DELETE FROM owned_other_certification WHERE owned_other_certification_id=?";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			pStmt.setInt(1, ocId);
			pStmt.executeUpdate();
		}
	}

	/**
	 * スキル保有情報を削除するメソッド
	 * @param sklId スキル保有ID
	 * @throws SQLException
	 */
	public void skillDelete(int sklId) throws SQLException{
		String sql="DELETE FROM owned_skill WHERE owned_skill_id=?";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			pStmt.setInt(1, sklId);
			pStmt.executeUpdate();
		}
	}

}
