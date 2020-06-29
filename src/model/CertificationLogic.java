package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import dao.SkillsDAO;

/**
 * @author Aitec
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
	public void registerMst(String eNum,String mstCode,String mstDate)throws ServletException {
		SkillsDAO sDAO=new SkillsDAO(connection);
		try {
			sDAO.registerMaster(eNum,mstCode,mstDate);
		}catch(SQLException | IllegalArgumentException e){
			throw new ServletException(e);
		}
	}

	//その他資格登録
	public void registerOth(String eNum,String othGenre,String othName,String othDate)throws ServletException {
		SkillsDAO sDAO=new SkillsDAO(connection);
		try {
			sDAO.registerOther(eNum,othGenre,othName,othDate);
		}catch(SQLException | IllegalArgumentException e){
			throw new ServletException(e);
		}
	}

	//その他資格の更新
	public void updateOth(int ownedId, String genCode, String othDate, String othName)throws ServletException {
		SkillsDAO sDAO=new SkillsDAO(connection);
		try{
			sDAO.updateOthCerti(ownedId,genCode,othDate,othName);
		} catch (SQLException | IllegalArgumentException e) {
			throw new ServletException(e);
		}
	}

	//マスタ資格の更新
	public void updateMst(int ownedId, String mcDate)throws ServletException {
		SkillsDAO sDAO=new SkillsDAO(connection);
		try{
			sDAO.updateMstCerti(ownedId,mcDate);
		} catch (SQLException | IllegalArgumentException e) {
			throw new ServletException(e);
		}
	}

	//その他資格内容取得
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


	public void mstDelete(int mcId) throws ServletException{
		SkillsDAO sDAO=new SkillsDAO(connection);
		try {
			//DB処理実行
			sDAO.masterDelete(mcId);
		}catch(SQLException e) {
			throw new ServletException(e);
		}
	}


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
