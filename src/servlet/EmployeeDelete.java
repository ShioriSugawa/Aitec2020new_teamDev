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
import model.EmployeeLogic;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED / AitecTresure＆Toraja Co.,Ltd<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 *  従業員削除のコントローラクラスです。<br>
 */
@WebServlet("/EmployeeDelete")
public class EmployeeDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeDelete() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// 該当の従業員を削除する
		try (Connection connection = ConnectionManager.getConnection()) {
			try {
				EmployeeLogic employeeLogic = new EmployeeLogic(connection);
				employeeLogic.deleteEmployee(request.getParameter("employeeNumber"));
			} catch (ServletException e) {
				connection.rollback();
				throw e;
			}
			connection.commit();
		} catch (SQLException e) {
			throw new ServletException(e);
		}

		// 自己紹介一覧画面にリダイレクト
		response.sendRedirect("/SelfIntroduction/EmployeeList?result=delete");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
