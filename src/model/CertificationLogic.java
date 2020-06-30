package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import dao.SkillsDAO;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * Copyright 2020 AitecTreasure＆Toraja Co.,Ltd<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 * 資格情報に関するビジネスロジックを記述するクラス<br>
 *
 */
public class CertificationLogic {

	/** データベースへの接続オブジェクト */
	Connection connection;

	/**
	 * コンストラクタ
	 * @param connection データベースへの接続オブジェクト
	 */
	public CertificationLogic(Connection connection) {
		this.connection = connection;
	}


	//資格ジャンルのコードと名前の一覧を取って来るやつ(マスタその他共用)
	/**
	 * 資格ジャンル(code/name)一覧を取得するメソッド
	 * @return 資格ジャンル一覧
	 * @throws ServletException
	 */
	public List<Certification>getCertiGenre()throws ServletException{
		SkillsDAO sDAO=new SkillsDAO(connection);
		List<Certification>genre=null;
		try {
			//DB処理実行
			genre = sDAO.getCertiGenre();
		}catch(SQLException e) {
			throw new ServletException(e);
		}
		return genre;
	}

	//資格のコードと名前の一覧を取って来るやつ
	/**
	 * マスタ資格(code/name)一覧を取得するメソッド
	 * @return マスタ資格一覧
	 * @throws ServletException
	 */
	public List<Certification>getCertiName()throws ServletException{
		SkillsDAO sDAO=new SkillsDAO(connection);
		List<Certification>names=null;
		try {
			//DB処理実行
			names = sDAO.getCertiName();
		}catch(SQLException e) {
			throw new ServletException(e);
	}
		return names;
	}

	//マスタ資格登録
	/**
	 * マスタ資格保有情報を登録するメソッド
	 * @param eNum 従業員番号
	 * @param mstCode マスタ資格コード
	 * @param mstDate 取得日
	 * @throws ServletException
	 */
	public void registerMst(String eNum,String mstCode,String mstDate)throws ServletException {
		SkillsDAO sDAO=new SkillsDAO(connection);
		try {
			sDAO.registerMaster(eNum,mstCode,mstDate);
		}catch(SQLException | IllegalArgumentException e){
			throw new ServletException(e);
		}
	}

	//その他資格登録
	/**
	 * その他資格保有情報を登録するメソッド
	 * @param eNum 従業員番号
	 * @param othGenre その他ジャンルコード
	 * @param othName その他資格名
	 * @param othDate 取得日
	 * @throws ServletException
	 */
	public void registerOth(String eNum,String othGenre,String othName,String othDate)throws ServletException {
		SkillsDAO sDAO=new SkillsDAO(connection);
		try {
			sDAO.registerOther(eNum,othGenre,othName,othDate);
		}catch(SQLException | IllegalArgumentException e){
			throw new ServletException(e);
		}
	}

	//その他資格の更新
	/**
	 * その他資格保有情報を編集するメソッド
	 * @param ownedId その他資格保有ID
	 * @param genCode その他ジャンルコード
	 * @param othDate 取得日
	 * @param othName その他資格名
	 * @throws ServletException
	 */
	public void updateOth(int ownedId, String genCode, String othDate, String othName)throws ServletException {
		SkillsDAO sDAO=new SkillsDAO(connection);
		try{
			sDAO.updateOthCerti(ownedId,genCode,othDate,othName);
		} catch (SQLException | IllegalArgumentException e) {
			throw new ServletException(e);
		}
	}

	//マスタ資格の更新
	/**
	 * マスタ資格保有情報を編集するメソッド
	 * @param ownedId マスタ資格保有ID
	 * @param mcDate 取得日
	 * @throws ServletException
	 */
	public void updateMst(int ownedId, String mcDate)throws ServletException {
		SkillsDAO sDAO=new SkillsDAO(connection);
		try{
			sDAO.updateMstCerti(ownedId,mcDate);
		} catch (SQLException | IllegalArgumentException e) {
			throw new ServletException(e);
		}
	}

	//その他資格内容取得
	/**
	 * その他資格保有データを取得するメソッド
	 * @param ownedId その他資格保有ID
	 * @return その他資格保有データ
	 * @throws ServletException
	 */
	public Certification getOwnedOth(int ownedId)throws ServletException {
		SkillsDAO sDAO=new SkillsDAO(connection);
		Certification ownedOth=null;
		try {
			ownedOth=sDAO.getOwnedOth(ownedId);
		}catch(SQLException e){
			throw new ServletException(e);
		}
		return ownedOth;
	}

	//マスタ資格内容取得
	/**
	 * マスタ資格保有データを取得するメソッド
	 * @param ownedId その他資格保有ID
	 * @return マスタ資格保有データ
	 * @throws ServletException
	 */
	public Certification getOwnedMst(int ownedId)throws ServletException {
		SkillsDAO sDAO=new SkillsDAO(connection);
		Certification ownedMst=null;
		try {
			ownedMst=sDAO.getOwnedMst(ownedId);
		}catch(SQLException e){
			throw new ServletException(e);
		}
		return ownedMst;
	}


	/**
	 * マスタ資格保有情報を削除するメソッド
	 * @param mcId
	 * @throws ServletException
	 */
	public void mstDelete(int mcId) throws ServletException{
		SkillsDAO sDAO=new SkillsDAO(connection);
		try {
			//DB処理実行
			sDAO.masterDelete(mcId);
		}catch(SQLException e) {
			throw new ServletException(e);
		}
	}


	/**
	 * その他資格保有情報を削除するメソッド
	 * @param ocId
	 * @throws ServletException
	 */
	public void othDelete(int ocId) throws ServletException{
		SkillsDAO sDAO=new SkillsDAO(connection);
		try {
			//DB処理実行
			sDAO.otherDelete(ocId);
		}catch(SQLException e) {
			throw new ServletException(e);
		}
	}

}
