package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionManager;
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
		String mc,oth,skl;
		try (Connection connection = ConnectionManager.getConnection()) {
			// 編集IDのオーダーを取得
			SkillLogic skillLogic = new SkillLogic(connection);
			mc=request.getParameter("owned_certification_id");
			oth=request.getParameter("owned_other_certification_id");
			skl=request.getParameter("owned_skill_id");
			
			if(mc!=null) {

			// リクエストスコープに保存
			//request.setAttribute("emp", emp);
			}
			if(oth!=null) {}
			if(skl!=null) {}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		
		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/skillsupdate.jsp");
		dispatcher.forward(request, response);
		
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");


	}


}
