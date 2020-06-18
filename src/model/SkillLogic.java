package model;

import java.sql.Connection;

public class SkillLogic {

	/** データベースへの接続オブジェクト */
	Connection connection;

	/**
	 * コンストラクタ
	 * @param connection データベースへの接続オブジェクト
	 */
	public SkillLogic(Connection connection) {
		this.connection = connection;
	}

	public Skill getOwnedSkill(int ownedId) {

		Skill ownedSkill=null;
		return ownedSkill;
	}

}
