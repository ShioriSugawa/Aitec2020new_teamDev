package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import dao.SkillsDAO;

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

	//IDで保有スキルデータ1件を取得
	public Skill getOwnedSkill(int ownedId)throws ServletException {
		SkillsDAO sDAO=new SkillsDAO(connection);
		Skill ownedSkill=null;
		try {
			ownedSkill=sDAO.getOwnedSkill(ownedId);
		}catch(SQLException e) {
			throw new ServletException(e);
		}
		return ownedSkill;
	}

	//ジャンルのコードと名前の一覧を取って来るやつ
	public List<Skill>getGenre()throws ServletException{
		SkillsDAO sDAO=new SkillsDAO(connection);
		List<Skill>genre=null;
		try {
			//DB処理実行
			genre = sDAO.getGenre();
		}catch(SQLException e) {
			throw new ServletException(e);
	}
		return genre;
	}

}
