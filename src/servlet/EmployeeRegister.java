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
import model.Employee;
import model.EmployeeLogic;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED / AitecTresure＆Toraja Co.,Ltd<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 *  従業員登録のコントローラクラスです。<br>
 */
/*
 * 修正内容まとめ
 * 2020/6/15　所属追加対応
 *
 */
@WebServlet("/EmployeeRegister")
public class EmployeeRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeRegister() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		String employeeNumber = request.getParameter("employeeNumber");
		String employeeName = request.getParameter("employeeName");
		String employeeProfile = request.getParameter("employeeProfile");
		// 2020/6/15 追加
		String employeeDeployment = request.getParameter("deployment");

		// 2020/6/15 追加　 所属が未選択なら再度登録画面にフォワード
		Boolean noInputError = false;
		if(employeeDeployment.equals("所属を選択してください")) {
			noInputError = true;
			request.setAttribute("noInputError", noInputError);
			Employee emp = new Employee(employeeNumber, employeeName, employeeProfile,employeeDeployment);
			request.setAttribute("emp", emp);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
			dispatcher.forward(request, response);
		}

		Boolean hasError = false;

		try (Connection connection = ConnectionManager.getConnection()) {
			try {
				// 該当の従業員を登録
				// 6/15 所属追加
				EmployeeLogic employeeLogic = new EmployeeLogic(connection);
				hasError = employeeLogic.registerEmployee(employeeNumber, employeeName, employeeProfile,employeeDeployment);

				if(hasError == true) {
					// 既に登録されている従業員番号の場合、登録画面へフォワードし、エラーメッセージを表示
					request.setAttribute("hasError", hasError);
					Employee emp = new Employee(employeeNumber, employeeName, employeeProfile,employeeDeployment);
					request.setAttribute("emp", emp);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
					dispatcher.forward(request, response);
				} else {
					// 登録に成功した場合、自己紹介一覧画面へリダイレクト
					connection.commit();
					response.sendRedirect("/SelfIntroduction/EmployeeList?result=register");
				}
			} catch (ServletException e) {
				connection.rollback();
				throw e;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

}
