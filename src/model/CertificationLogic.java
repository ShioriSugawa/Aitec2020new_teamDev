/**
 *
 */
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


	//資格ジャンルのコードと名前の一覧を取って来るやつ
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

	//資格のコードと名前（マスタその他共用）の一覧を取って来るやつ
	public List<Certification>getCertiName()throws ServletException{
		SkillsDAO sDAO=new SkillsDAO(connection);
		List<Certification>names=null;
		try {
			//DB処理実行
			names = sDAO.getCertiGenre();
		}catch(SQLException e) {
			throw new ServletException(e);
	}
		return names;
	}

}
