package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	 * 保有資格IDによる資格保有データ1件の取得
	 * @param
	 */

	/**
	 * 保有その他資格IDによるその他資格保有データ1件の取得
	 * @param
	 */

	/**
	 * 保有スキルIDによるスキル保有データ1件の取得
	 * @param owned_skill_id 取得したいスキル保有データのID
	 * @return スキル保有データ1件
	 * @throws SQLException
	 */
	public Skill getOwnedSkill(int ownedId)throws SQLException {
		Skill ownedSkill;
		final String sql=
				"SELECT employee_number,skill_genre_code,skill_genre_name,skill_name"
						+ "FROM owned_skill INNER JOIN skill_genre"
						+ "ON owned_skill.skill_genre_code=skill_genre.skill_genre_code"
						+ "WHERE owned_skill_id=? ";

	try(PreparedStatement pStmt = conn.prepareStatement(sql)){
		pStmt.setInt(1, ownedId);
		ResultSet resultSet = pStmt.executeQuery();

		String employeeNumber=resultSet.getString("employee_number");
		String genreCode=resultSet.getString("skill_genre_code");
		String genreName=resultSet.getString("skill_genre_name");
		String skillName=resultSet.getString("skill_name");

		ownedSkill=new Skill(ownedId,employeeNumber,genreCode,genreName,skillName);
	}
		return ownedSkill;
	}


}
