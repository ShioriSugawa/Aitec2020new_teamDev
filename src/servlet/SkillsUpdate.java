package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionManager;
import model.Certification;
import model.CertificationLogic;
import model.Skill;
import model.SkillLogic;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム
 * クラス概要：スキル資格編集のコントローラクラス
 */

@WebServlet("/SkillsUpdate")
public class SkillsUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SkillsUpdate() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String mc,oth,skl;
		try (Connection connection = ConnectionManager.getConnection()) {
			// 編集IDのオーダーを取得
			SkillLogic skillLogic = new SkillLogic(connection);
			CertificationLogic cLogic=new CertificationLogic(connection);
			mc=request.getParameter("owned_certification_id");
			oth=request.getParameter("owned_other_certification_id");
			skl=request.getParameter("owned_skill_id");

			//skl="5";	//実験用固定IDショートカット

			if(mc!=null) {
				int mcI = Integer.parseInt(mc);
				Certification ownedMst=cLogic.getOwnedMst(mcI);
				// リクエストスコープに保存
				request.setAttribute("ownedMst", ownedMst);
				// マスタ資格編集にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/certificationUpdate.jsp");
				dispatcher.forward(request, response);
				}

			if(oth!=null) {
				int othI = Integer.parseInt(oth);
				Certification ownedOth=cLogic.getOwnedOth(othI);
				List<Certification>certiGenre=cLogic.getCertiGenre();
				// リクエストスコープに保存
				request.setAttribute("certiGenre", certiGenre);
				request.setAttribute("ownedOth", ownedOth);
				// その他資格編集にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/otherCertificationUpdate.jsp");
				dispatcher.forward(request, response);
			}

			if(skl!=null) {
				int sklI = Integer.parseInt(skl);
				Skill oSkl=skillLogic.getOwnedSkill(sklI);
				List<Skill> skillGenre=skillLogic.getGenre();
				// リクエストスコープに保存
				request.setAttribute("skillGenre", skillGenre);
				request.setAttribute("oSkl", oSkl);

				// スキル編集にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/skillUpdate.jsp");
				dispatcher.forward(request, response);
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		//保有ID 受け取り、それによって条件分岐？（getと同じく）、予定
		//下の受け取り値、わからなければメンバーに聞け！！

		String mcIdS=request.getParameter("MownedId");
		String othIdS=request.getParameter("OownedId");
		String sklIdS=request.getParameter("SownedId");
		String employeeNumber=request.getParameter("employeeNumber");

		//マスタ資格IDを受け取ったら
		if(mcIdS!=null) {
			String mcDate=request.getParameter("date");

			int mcId = Integer.parseInt(mcIdS);

			try(Connection connection = ConnectionManager.getConnection()){
				try {
					// 該当のスキル記述を更新
					CertificationLogic cLogic = new CertificationLogic(connection);
					cLogic.updateMst(mcId,mcDate);
					connection.commit();
				} catch (ServletException e) {
					connection.rollback();
					throw e;
				}
			}catch(SQLException e){
				throw new ServletException(e);
			}
		}

		//その他資格IDを受け取ったら
		if(othIdS!=null) {
			String genCode=request.getParameter("genreCode");
			String othDate=request.getParameter("date");
			String othName=request.getParameter("certiName");

			int othId = Integer.parseInt(othIdS);

			try(Connection connection = ConnectionManager.getConnection()){
				try {
					// 該当のスキル記述を更新
					CertificationLogic cLogic = new CertificationLogic(connection);
					cLogic.updateOth(othId,genCode,othDate,othName);
					connection.commit();
				} catch (ServletException e) {
					connection.rollback();
					throw e;
				}
			}catch(SQLException e){
				throw new ServletException(e);
			}
		}

		//スキルIDを受け取ったら
		if(sklIdS!=null) {
			String sklGenCode=request.getParameter("skillGenre");
			String sklName=request.getParameter("skillName");

			int sklId = Integer.parseInt(sklIdS);

			//あとで未入力チェックもつけないといけない

			try(Connection connection = ConnectionManager.getConnection()){
				try {
					// 該当のスキル記述を更新
					SkillLogic sLogic = new SkillLogic(connection);
					sLogic.updateSkill(sklId,sklGenCode,sklName);
					connection.commit();
				} catch (ServletException e) {
					connection.rollback();
					throw e;
				}
			}catch(SQLException e){
				throw new ServletException(e);
			}
		}

		// 詳細画面へリダイレクト
		String url1 = "EmployeeDetail?employeeNumber=";
		String url2 = employeeNumber;
		StringBuffer buf = new StringBuffer();

		buf.append(url1);
		buf.append(url2);

		String url = buf.toString();
		response.sendRedirect(url);
	}


}
