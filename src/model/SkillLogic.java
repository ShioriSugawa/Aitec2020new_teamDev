package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
	public Skill getOwnedSkill(int ownedId) {
		Skill ownedSkill=null;
		return ownedSkill;
	}

	//ジャンルのコードと名前の一覧を取って来るやつ
	public ArrayList<Skill>getGenre()throws ServletException{
		SkillsDAO sDAO=new SkillsDAO(connection);
		ArrayList<Skill>genre=null;
		try {
			//DB処理実行
			genre = empDAO.getGenreList();
		}catch(SQLException e) {
			throw new ServletException(e);
	}
		return genre;
	}

}
