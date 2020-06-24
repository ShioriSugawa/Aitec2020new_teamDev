package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Certification;
import model.Skill;

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
	 * 保有マスタ資格IDによる資格保有データ1件の取得
	 * @param 編集中
	 */		/*	編集中*/
	public Certification getOwnedMst(int ownedId)throws SQLException {
		Certification ownedCertification;
		final String sql=	//要編集、全部まとめて取って来る
				"SELECT employee_number,certification_code,certification_date"
						+ "FROM owned_skill INNER JOIN skill_genre"
						+ "ON owned_skill.skill_genre_code=skill_genre.skill_genre_code"
						+ "WHERE owned_skill_id=? ";

	try(PreparedStatement pStmt = conn.prepareStatement(sql)){
		pStmt.setInt(1, ownedId);
		ResultSet resultSet = pStmt.executeQuery();

		String employeeNumber=resultSet.getString("employee_number");
		String certiCode=resultSet.getString("certification_genre_code");
		String certiGenre=resultSet.getString("certification_genre_name");
		String masterCode=resultSet.getString("certification_code");
		String certiName=resultSet.getString("certification_name");
		String certiDate=resultSet.getString("certification_date");

		ownedCertification=new Certification(ownedId,employeeNumber,certiCode,certiGenre,masterCode,certiName,certiDate);
	}
		return ownedCertification;
	}//*/


	/**
	 * 保有その他資格IDによるその他資格保有データ1件の取得
	 * @param 編集中
	 */
	public Certification getOwnedOth(int ownedId)throws SQLException {
		Certification ownedOth=null;
		final String sql=	//要編集、まとめて取って来る
				"SELECT employee_number,certification_genre_code,other_certification_date,other_certification_name"
						+ "FROM owned_other_certification WHERE owned_other_certification_id=? ";
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

	public List<Skill> getGenre()throws SQLException{
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
	public List<Certification> getCertiName()throws SQLException{
		List<Certification> certiGenreList=new ArrayList<>();
		final String sql="SELECT * FROM certification";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			ResultSet resultSet = pStmt.executeQuery();

			while(resultSet.next()) {
				String code=resultSet.getString("certification_code");
				String name=resultSet.getString("certification_name");

				Certification names=new Certification(code,name);
				certiGenreList.add(names);
			}
		}
		return certiGenreList;
	}

	/**
	 * 保有スキルIDによるスキル保有データ1件の取得
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
	public void updateOthCerti(int ownedId, String genCode, String othDate, String othName)throws SQLException {
		String sql="UPDATE owned_other_certification "
				+ "SET certification_genre_code=?,"
				+ "other_certification_date=?,othre_certification_name=? WHERE owned_other_certification_id=?";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			pStmt.setString(1, genCode);
			pStmt.setString(2, othDate);
			pStmt.setString(3, othName);
			pStmt.setInt(4, ownedId);
			pStmt.executeUpdate();
		}
	}

	//マスタ資格アップデート
	public void updateMstCerti(int ownedId, String mcDate)throws SQLException {
		String sql="UPDATE owned_certification SET certification_date=? WHERE owned_certification_id=?";
		try(PreparedStatement pStmt = conn.prepareStatement(sql)){
			pStmt.setString(1, mcDate);
			pStmt.setInt(2, ownedId);
			pStmt.executeUpdate();
		}
	}

}
