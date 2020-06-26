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
import model.CareerLogic;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：業務経歴削除のコントローラクラス<br>
 */
@WebServlet("/CareerDelete")
public class CareerDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CareerDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		String employeeNumber = request.getParameter("employeeNumber");

		try (Connection connection = ConnectionManager.getConnection()){
			try {
				//該当の業務経歴を削除する
				CareerLogic careerLogic = new CareerLogic(connection);
				careerLogic.deleteCareer(request.getParameter("businessNumber"));
				//従業員一覧画面へリダイレクト
				connection.commit();

				String url1 = "EmployeeDetail?employeeNumber=";
				String url2 = employeeNumber;
				StringBuffer buf = new StringBuffer();
				buf.append(url1);
				buf.append(url2);
				String url = buf.toString();
				response.sendRedirect(url);

				//response.sendRedirect("/SelfIntroduction/EmployeeList?result=delete");
			}catch (ServletException e) {
				connection.rollback();
				throw e;
			}
		}catch (SQLException e) {
			throw new ServletException(e);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
