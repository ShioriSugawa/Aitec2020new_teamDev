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
	/**
	 * スキル保有データを取得するメソッド
	 * @param ownedId スキル保有ID
	 * @return スキル保有データ
	 * @throws ServletException
	 */
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

	//スキル登録
	/**
	 * スキル保有情報を登録するメソッド
	 * @param eNum 従業員番号
	 * @param sklGenre スキルジャンルコード
	 * @param sklName スキル名
	 * @throws ServletException
	 */
	public void registerSkl(String eNum,String sklGenre,String sklName)throws ServletException {
		SkillsDAO sDAO=new SkillsDAO(connection);
		//入力値チェックはサーブレット
		try{
			sDAO.registerSkill(eNum,sklGenre,sklName);
		} catch (SQLException | IllegalArgumentException e) {
			throw new ServletException(e);
		}
	}

	//スキル更新
	/**
	 * スキル保有情報を編集するメソッド
	 * @param ownedId スキル保有ID
	 * @param skillGenre スキルジャンルコード
	 * @param skillName スキル名
	 * @throws ServletException
	 */
	public void updateSkill(int ownedId,String skillGenre,String skillName)throws ServletException {
		SkillsDAO sDAO=new SkillsDAO(connection);
		//入力値チェックはサーブレット
		try{
			sDAO.updateSkill(ownedId,skillGenre,skillName);
		} catch (SQLException | IllegalArgumentException e) {
			throw new ServletException(e);
		}
	}

	//ジャンルのコードと名前の一覧を取って来るやつ
	/**
	 * スキルジャンル(code/name)一覧を取得するメソッド
	 * @return スキルジャンル一覧
	 * @throws ServletException
	 */
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

	//スキルデリート
	/**
	 * スキル資格保有情報を削除するメソッド
	 * @param sklId
	 * @throws ServletException
	 */
	public void sklDelete(int sklId) throws ServletException{
		SkillsDAO sDAO=new SkillsDAO(connection);
		try {
			//DB処理実行
			sDAO.skillDelete(sklId);
		}catch(SQLException e) {
			throw new ServletException(e);
		}
	}


}
