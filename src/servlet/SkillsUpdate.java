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
			mc=request.getParameter("owned_certification_id");
			oth=request.getParameter("owned_other_certification_id");
			skl=request.getParameter("owned_skill_id");

			//skl="6";	//実験用固定IDショートカット

			if(mc!=null) {
				int mcI = Integer.parseInt(mc);
				Skill ownedSkill=skillLogic.getOwnedSkill(mcI);
				// リクエストスコープに保存
				request.setAttribute("ownedSkill", ownedSkill);
				// マスタ資格編集にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/certificationUpdate.jsp");
				dispatcher.forward(request, response);
				}

			if(oth!=null) {
				int othI = Integer.parseInt(oth);
				Skill ownedSkill=skillLogic.getOwnedSkill(othI);
				// リクエストスコープに保存
				request.setAttribute("ownedSkill", ownedSkill);
				// その他資格編集にフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/otherCertificationUpdate.jsp");
				dispatcher.forward(request, response);
			}

			if(skl!=null) {
				int sklI = Integer.parseInt(skl);
				Skill ownedSkill=skillLogic.getOwnedSkill(sklI);
				List<Skill> skillGenre=skillLogic.getGenre();
				// リクエストスコープに保存
				request.setAttribute("skillGenre", skillGenre);
				request.setAttribute("ownedSkill", ownedSkill);

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

		request.setCharacterEncoding("UTF-8");
		String sklGenCode=request.getParameter("skillGenre");
		String sklName=request.getParameter("skillName");

		//あとで未入力チェックもつけないといけない

		try(Connection connection = ConnectionManager.getConnection()){

			try {
				// 該当のスキル記述を更新
				SkillLogic sLogic = new SkillLogic(connection);
				sLogic.updateEmployee(request.getParameter("employeeNumber"), employeeName, employeeProfile,employeeDeployment);
				connection.commit();
			} catch (ServletException e) {
				connection.rollback();
				throw e;
			}

		}catch(SQLException e){}

	}


}
