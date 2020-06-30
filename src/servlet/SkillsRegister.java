package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import model.Employee;
import model.EmployeeLogic;
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
			List<Integer> year=new ArrayList<>();
			for(int i=nowYear;i>1970;i--) {
				year.add(nowYear);
				nowYear--;
			}

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
			request.setAttribute("regi", regi);
			request.setAttribute("yearL", year);
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

		try(Connection connection = ConnectionManager.getConnection()){
			try {
				CertificationLogic cLogic = new CertificationLogic(connection);
				SkillLogic sLogic = new SkillLogic(connection);
				EmployeeLogic eLogic = new EmployeeLogic(connection);

				Calendar cal = Calendar.getInstance();
				int nowYear= cal.get(Calendar.YEAR);
				List<Integer> year=new ArrayList<>();
				for(int i=nowYear;i>1970;i--) {
					year.add(nowYear);
					nowYear--;
				}

				List<Certification>cGenL=cLogic.getCertiGenre();
				List<Certification>cNameL=cLogic.getCertiName();
				List<Skill>sGenL=sLogic.getGenre();

				request.setAttribute("eNum", eNum);
				request.setAttribute("cGenL", cGenL);
				request.setAttribute("cNameL", cNameL);
				request.setAttribute("sGenL", sGenL);
				request.setAttribute("yearL", year);

				String emptyMessage="";
				String doubleMessage="";

				if("mst".equals(regiSel)) {
					String mstInfo =request.getParameter("mstInfo");
					String mstYear =request.getParameter("mstYear");
					String mstMonth =request.getParameter("mstMonth");
					String mstDate=mstYear+"/"+mstMonth;

					List<Employee> mstL=eLogic.getMasterCertificationList(eNum);
					String mstCode="";
					if(mstInfo!=null) {
						String mstName=mstInfo.substring(6);
						mstCode=mstInfo.substring(0, 6);
						for ( Employee j : mstL ) {
							if(mstName.equals(j.getCertificationOrSkillName())) {
								doubleMessage+="この資格は既に登録されています";
								String regi="c";
								request.setAttribute("doubleMessage", doubleMessage);
								request.setAttribute("regi", regi);
								request.setAttribute("eNum" , eNum);
								request.setAttribute("sldMC", mstCode);
								request.setAttribute("sldMY" , mstYear);
								request.setAttribute("sldMM" , mstMonth);

								request.setAttribute("sldMN", mstName);
								request.setAttribute("sldMI", mstInfo);

								RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/skillsRegister.jsp");
								dispatcher.forward(request, response);
							}
						}
					}
						if(mstInfo==null) {
							emptyMessage += "「資格名」";
						}

						if(mstYear==null||mstMonth==null) {
							emptyMessage+="「取得日」";
						}
						if(emptyMessage.equals("")&&doubleMessage.equals("")) {
							// マスタ資格保有情報を登録
							cLogic.registerMst(eNum,mstCode,mstDate);
							connection.commit();
						}else{
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

				}

				if("oth".equals(regiSel)) {
					String othGenre =request.getParameter("othGenre");
					String othName =request.getParameter("othName");
					String othYear =request.getParameter("othYear");
					String othMonth =request.getParameter("othMonth");
					String othDate=othYear+"/"+othMonth;

					if(othGenre==null) {
						emptyMessage += "「資格ジャンル」";
					}
					if(othName==null) {
						emptyMessage += "「資格名」";
					}else {
						if(othName.isBlank()) {
							emptyMessage+="「資格名」";
						}
					}
					if(othYear==null||othMonth==null) {
						emptyMessage += "「取得日」";
					}
					if(emptyMessage.equals("")){
						// その他資格保有情報を登録
					cLogic.registerOth(eNum,othGenre,othName,othDate);
					connection.commit();
					}else {
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
				}

				if("skl".equals(regiSel)) {
					String sklGenre =request.getParameter("sklGenre");
					String sklName =request.getParameter("sklName");
					if(sklGenre==null) {
						emptyMessage += "「スキルジャンル」";
					}
					if(sklName==null) {
						emptyMessage+="「スキル内容」";
					}else {
						if(sklName.isBlank()) {
							emptyMessage+="「スキル内容」";
						}
					}
					if(emptyMessage.equals("")) {
						// スキル情報を登録
						sLogic.registerSkl(eNum,sklGenre,sklName);
						connection.commit();
					}else{
						String regi="s";
						request.setAttribute("eNum" , eNum);
						request.setAttribute("sldSG", sklGenre);
						request.setAttribute("sldSN" , sklName);
						request.setAttribute("emptyMessage", emptyMessage);
						request.setAttribute("regi", regi);
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/skillsRegister.jsp");
						dispatcher.forward(request, response);
					}
				}

			} catch (ServletException e) {
				throw e;
			}
		}catch(SQLException e){
			throw new ServletException(e);
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