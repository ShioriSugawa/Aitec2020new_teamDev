package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionManager;
import model.CertificationLogic;
import model.SkillLogic;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED/AitecTreasure＆Toraja Co.,Ltd<br>
 * システム名：自己紹介システム
 * クラス概要：スキル資格削除のコントローラクラス
 */
@WebServlet("/SkillsDelete")
public class SkillsDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SkillsDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		//受け取ったIDで削除実行して、個人ページに返す

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String mc,oc,skl;
		try (Connection connection = ConnectionManager.getConnection()) {
			// 編集IDのオーダーを取得
			SkillLogic sLogic = new SkillLogic(connection);
			CertificationLogic cLogic=new CertificationLogic(connection);
			mc=request.getParameter("owned_certification_id");
			oc=request.getParameter("owned_other_certification_id");
			skl=request.getParameter("owned_skill_id");
			String eNum=request.getParameter("employeeNumber");

			if(mc!=null) {
				int mcId = Integer.parseInt(mc);
				cLogic.mstDelete(mcId);
				connection.commit();
			}

			if(oc!=null) {
				int ocId = Integer.parseInt(oc);
				cLogic.othDelete(ocId);
				connection.commit();
			}

			if(skl!=null) {
				int sklId = Integer.parseInt(skl);
				sLogic.sklDelete(sklId);
				connection.commit();
			}

		// 詳細画面へリダイレクト
		String url1 = "EmployeeDetail?employeeNumber=";
		String url2 = eNum;
		StringBuffer buf = new StringBuffer();

		buf.append(url1);
		buf.append(url2);

		String url = buf.toString();
		response.sendRedirect(url);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
