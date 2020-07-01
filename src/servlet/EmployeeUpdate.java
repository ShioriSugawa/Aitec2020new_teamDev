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
 *  従業員更新のコントローラクラスです。<br>
 *
 */
/*
 * 2020/6/16 所属追加対応
 */
@WebServlet("/EmployeeUpdate")
public class EmployeeUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeUpdate() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		try (Connection connection = ConnectionManager.getConnection()) {
			// 該当の従業員を取得
			EmployeeLogic employeeLogic = new EmployeeLogic(connection);
			Employee emp = employeeLogic.getEmployee(request.getParameter("employeeNumber"));
			//2020/07/01 追加
			String deployment = emp.getEmployeeDeployment();
			if(deployment == null) {
				deployment = "所属を選択してください";		//jspでのnullエラー対策
			}

			// リクエストスコープに保存
			request.setAttribute("emp", emp);
			// 2020/07/01 追加
			request.setAttribute("deployment", deployment);
		} catch (SQLException e) {
			throw new ServletException(e);
		}

		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/update.jsp");
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
		//2020/6/16　追加
		String employeeDeployment = request.getParameter("deployment");

		//2020/07/01 追加
		Employee emp = new Employee(employeeNumber, employeeName, employeeProfile, employeeDeployment);

		// 2020/6/16 追加　 所属が未選択なら再度更新画面にフォワード
		Boolean noInputError = false;
		if(employeeDeployment.equals("所属を選択してください")) {
			noInputError = true;
			request.setAttribute("noInputError", noInputError);

			// 2020/07/01 追加
			request.setAttribute("emp", emp);
			request.setAttribute("deployment", employeeDeployment);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/update.jsp");
			dispatcher.forward(request, response);
		}

		try (Connection connection = ConnectionManager.getConnection()) {
			try {
				// 該当の従業員を更新
				EmployeeLogic employeeLogic = new EmployeeLogic(connection);
				employeeLogic.updateEmployee(request.getParameter("employeeNumber"), employeeName, employeeProfile,employeeDeployment);
				connection.commit();
			} catch (ServletException e) {
				connection.rollback();
				throw e;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
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
