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
 * クラス概要：スキル資格登録のコントローラクラス
 */

@WebServlet("/SkillsRegister")
public class SkillsRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SkillsRegister() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String eNum,regi;
		try (Connection connection = ConnectionManager.getConnection()) {
			//必要：eNum,2ジャンル＆マスタ用2Logic,認定日ロール用cal.
			eNum=request.getParameter("employeeNumber");
			regi=request.getParameter("regi");
			CertificationLogic cLogic=new CertificationLogic(connection);
			SkillLogic sLogic=new SkillLogic(connection);
			Calendar cal = Calendar.getInstance();
			int nowYear= cal.get(Calendar.YEAR);

			List<Certification>cGenL=cLogic.getCertiGenre();
			List<Certification>cNameL=cLogic.getCertiName();
			List<Skill>sGenL=sLogic.getGenre();

		/*	//実験用ショートカット
			eNum="100001";
			regi="c";//*/

			request.setAttribute("eNum", eNum);
			request.setAttribute("cGenL", cGenL);
			request.setAttribute("cNameL", cNameL);
			request.setAttribute("sGenL", sGenL);
			request.setAttribute("nowYear", nowYear);
			request.setAttribute("regi", regi);
		} catch (SQLException e) {
			throw new ServletException(e);
		}

		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/skillsRegister.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		String eNum=request.getParameter("employeeNumber");
		String regiSel=request.getParameter("regiSelect");

		if("mst".contentEquals(regiSel)) {
			String mstCode =request.getParameter("mstCode");
			String mstYear =request.getParameter("mstYear");
			String mstMonth =request.getParameter("mstMonth");
			String mstDate=mstYear+"/"+mstMonth;
			String emptyMessage=null;

			if(mstCode.equals("empty")) {
				emptyMessage += "「資格名」";
			}
			if(mstYear.equals("empty")||mstMonth.equals("empty")) {
				emptyMessage+="「認定日」";
			}
			if(emptyMessage!=null) {
				String regi="c";
				request.setAttribute("eNum" , eNum);
				request.setAttribute("sldMC", mstCode);
				request.setAttribute("sldMY" , mstYear);
				request.setAttribute("sldMM" , mstMonth);
				request.setAttribute("emptyMessage", emptyMessage);
				request.setAttribute("regi", regi);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/skillsRegister.jsp");
				dispatcher.forward(request, response);
			}

			try(Connection connection = ConnectionManager.getConnection()){
				try {
					// マスタ資格保有情報を登録
					CertificationLogic cLogic = new CertificationLogic(connection);
					cLogic.registerMst(eNum,mstCode,mstDate);
					connection.commit();
				} catch (ServletException e) {
					connection.rollback();
					throw e;
				}
			}catch(SQLException e){
				throw new ServletException(e);
			}
		}

		if("oth".contentEquals(regiSel)) {
			String othGenre =request.getParameter("othGenre");
			String othName =request.getParameter("othName");
			String othYear =request.getParameter("othYear");
			String othMonth =request.getParameter("othMonth");
			String othDate=othYear+"/"+othMonth;
			String emptyMessage=null;

			if(othGenre.equals("empty")) {
				emptyMessage += "「資格ジャンル」";
			}
			if(othName.equals("empty")) {
				emptyMessage += "「資格名」";
			}
			if(othYear.equals("empty")||othMonth.equals("empty")) {
				emptyMessage += "「認定日」";
			}
			if(emptyMessage!=null) {
				String regi="o";
				request.setAttribute("eNum" , eNum);
				request.setAttribute("sldOG", othGenre);
				request.setAttribute("sldON" , othName);
				request.setAttribute("sldOY" , othYear);
				request.setAttribute("sldOM" , othMonth);
				request.setAttribute("emptyMessage", emptyMessage);
				request.setAttribute("regi", regi);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/skillsRegister.jsp");
				dispatcher.forward(request, response);
			}

			try(Connection connection = ConnectionManager.getConnection()){
				try {
					// その他資格保有情報を登録
					CertificationLogic cLogic = new CertificationLogic(connection);
					cLogic.registerOth(eNum,othGenre,othName,othDate);
					connection.commit();
				} catch (ServletException e) {
					connection.rollback();
					throw e;
				}
			}catch(SQLException e){
				throw new ServletException(e);
			}
		}

		if("skl".contentEquals(regiSel)) {
			String sklGenre =request.getParameter("sklGenre");
			String sklName =request.getParameter("sklName");
			String emptyMessage=null;
			if(sklGenre.equals("empty")) {
				emptyMessage += "「スキルジャンル」";
			}
			if(sklName.equals("empty")) {
				emptyMessage+="「スキル内容」";
			}
			if(emptyMessage!=null) {
				String regi="s";
				request.setAttribute("eNum" , eNum);
				request.setAttribute("sldSG", sklGenre);
				request.setAttribute("sldSN" , sklName);
				request.setAttribute("emptyMessage", emptyMessage);
				request.setAttribute("regi", regi);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/skillsRegister.jsp");
				dispatcher.forward(request, response);
			}

			try(Connection connection = ConnectionManager.getConnection()){
				try {
					// スキル情報を登録
					SkillLogic sLogic = new SkillLogic(connection);
					sLogic.registerSkl(eNum,sklGenre,sklName);
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
				String url2 = eNum;
				StringBuffer buf = new StringBuffer();

				buf.append(url1);
				buf.append(url2);

				String url = buf.toString();
				response.sendRedirect(url);
	}

}