package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
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
		String mc,oc,skl;
		try (Connection connection = ConnectionManager.getConnection()) {
			// 編集IDのオーダーを取得
			SkillLogic skillLogic = new SkillLogic(connection);
			CertificationLogic cLogic=new CertificationLogic(connection);
			Calendar cal = Calendar.getInstance();
			int nowYear= cal.get(Calendar.YEAR);
			mc=request.getParameter("owned_certification_id");
			oc=request.getParameter("owned_other_certification_id");
			skl=request.getParameter("owned_skill_id");

			//skl="5";	//実験用固定IDショートカット

			if(mc!=null) {
				int mcI = Integer.parseInt(mc);
				Certification mst=cLogic.getOwnedMst(mcI);
				String sldYear=mst.getCertiDate().substring(0,4);
				String sldMonth=mst.getCertiDate().substring(5);
				int sYeI = Integer.parseInt(sldYear);
				int sMonI = Integer.parseInt(sldMonth);
				// リクエストスコープに保存
				request.setAttribute("mst", mst);
				request.setAttribute("sYeI", sYeI);
				request.setAttribute("sMonI", sMonI);
				request.setAttribute("nowYear", nowYear);
				// マスタ資格編集にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/certificationUpdate.jsp");
				dispatcher.forward(request, response);
				}

			if(oc!=null) {
				int ocI = Integer.parseInt(oc);
				Certification oth=cLogic.getOwnedOth(ocI);
				List<Certification>cGenL=cLogic.getCertiGenre();
				String sldYear=oth.getCertiDate().substring(0,4);
				String sldMonth=oth.getCertiDate().substring(5);
				int sYeI = Integer.parseInt(sldYear);
				int sMonI = Integer.parseInt(sldMonth);
				// リクエストスコープに保存
				request.setAttribute("cGenL", cGenL);
				request.setAttribute("oth", oth);
				request.setAttribute("sYeI", sYeI);
				request.setAttribute("sMonI", sMonI);
				request.setAttribute("nowYear", nowYear);
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
			String mstYear=request.getParameter("mstYear");
			String mstMonth=request.getParameter("mstMonth");

			int mcId = Integer.parseInt(mcIdS);
			String mstDate=mstYear+"/"+mstMonth;

			try(Connection connection = ConnectionManager.getConnection()){
				try {
					// 該当のスキル記述を更新
					CertificationLogic cLogic = new CertificationLogic(connection);
					cLogic.updateMst(mcId,mstDate);
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
			String othYear=request.getParameter("othYear");
			String othMonth=request.getParameter("othMonth");
			String othName=request.getParameter("certiName");

			int othId = Integer.parseInt(othIdS);
			String othDate=othYear+"/"+othMonth;

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
