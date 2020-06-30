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
import model.Career;
import model.CareerLogic;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：業務経歴更新のコントローラクラス<br>
 */

@WebServlet("/CareerUpdate")
public class CareerUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CareerUpdate() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		try (Connection connection = ConnectionManager.getConnection()){
			// 該当の従業員を取得
			CareerLogic careerLogic = new CareerLogic(connection);
			Career career = careerLogic.getCareer(request.getParameter("businessNumber"));

			Calendar cal = Calendar.getInstance();
			int nowYear= cal.get(Calendar.YEAR);
			List<Integer> year=new ArrayList<>();
			for(int i=nowYear;i>=1970;i--) {
				year.add(nowYear);
				nowYear--;
			}

			String start = career.getBusinessStart();
			String startYear = start.substring(0,4);
			//String startMonth = start.substring(5);
			int startNum = Integer.parseInt(startYear);
			request.setAttribute("startNum", startNum);
			//request.setAttribute("m", startMonth);

			if(career.getBusinessEnd()==null) {
			}else {
				String end = career.getBusinessEnd();
				String endYear = end.substring(0,4);
				//String endMonth = end.substring(5);
				int endNum = Integer.parseInt(endYear);
				//request.setAttribute("endYear", endYear);
				//request.setAttribute("e", endMonth);
				request.setAttribute("endNum", endNum);
			}

			// リクエストスコープに保存
			request.setAttribute("career", career);
			request.setAttribute("yearL", year);

		}catch (SQLException e) {
			throw new ServletException(e);
		}

		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/careerUpdate.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
		String employeeNumber = request.getParameter("employeeNumber");
		String startYear = request.getParameter("startYear");
		String startMonth = request.getParameter("startMonth");
		String endYear = request.getParameter("endYear");
		String endMonth = request.getParameter("endMonth");
		String businessName = request.getParameter("businessName");
		String situation = request.getParameter("situation");

		//再度登録画面にフォワードしたときのための準備
		try (Connection connection = ConnectionManager.getConnection()){
			// 該当の従業員を取得
			CareerLogic careerLogic = new CareerLogic(connection);
			Career career = careerLogic.getCareer(request.getParameter("businessNumber"));

			Calendar cal = Calendar.getInstance();
			int nowYear= cal.get(Calendar.YEAR);
			List<Integer> year=new ArrayList<>();
			for(int i=nowYear;i>=1970;i--) {
				year.add(nowYear);
				nowYear--;
			}
			String start = career.getBusinessStart();
			String startY = start.substring(0,4);
			int startNum = Integer.parseInt(startY);
			request.setAttribute("startNum", startNum);

			if(career.getBusinessEnd()==null) {
			}else {
				String end = career.getBusinessEnd();
				String endY = end.substring(0,4);
				int endNum = Integer.parseInt(endY);
				request.setAttribute("endNum", endNum);
			}

			// リクエストスコープに保存
			request.setAttribute("career", career);
			request.setAttribute("yearL", year);
		}catch (SQLException e) {
			throw new ServletException(e);
		}

		//以前の業務を選択した場合
		if(situation.equals("0")) {
			//終了日が未選択なら再度登録画面にフォワード
			Boolean endError0 = false;
			if(endYear.equals("") || endMonth.equals("")) {
				endError0 = true;
				request.setAttribute("endError0", endError0);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/careerUpdate.jsp");
				dispatcher.forward(request, response);
				return;
			}

			//開始日より終了日が前になっている場合、再度登録画面にフォワード
			int a = Integer.parseInt(startYear);
			int b = Integer.parseInt(startMonth);
			int x = Integer.parseInt(endYear);
			int y = Integer.parseInt(endMonth);
			a *= 12;
			x *= 12;
			int start = a + b;
			int end = x + y;
			Boolean seError = false;
			if(start > end) {
				seError = true;
				request.setAttribute("seError", seError);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/careerUpdate.jsp");
				dispatcher.forward(request, response);
				return;
			}

		//現在の業務を選択した場合
		}else {
			//終了日が選択済みなら再度登録画面にフォワード
			Boolean endError1 = false;
			if(endYear.equals("") || endMonth.equals("")) {
			}else {
				endError1 = true;
				request.setAttribute("endError1", endError1);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/careerUpdate.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}

		try (Connection connection = ConnectionManager.getConnection()){
			try {
				//該当の業務経歴を更新
				CareerLogic careerLogic = new CareerLogic(connection);
				careerLogic.updateCareer(request.getParameter("businessNumber"), startYear, startMonth, endYear, endMonth, businessName, situation);

				//従業員一覧画面へリダイレクト
				connection.commit();
				String url1 = "EmployeeDetail?employeeNumber=";
				String url2 = employeeNumber;
				StringBuffer buf = new StringBuffer();
				buf.append(url1);
				buf.append(url2);
				String url = buf.toString();
				response.sendRedirect(url);

			}catch (ServletException e) {
				connection.rollback();
				throw e;
			}
		}catch (SQLException e) {
			throw new ServletException(e);
		}

	}

}
