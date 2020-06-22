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
import model.Career;
import model.CareerLogic;

/**
 * Servlet implementation class CareerUpdate
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

			// リクエストスコープに保存
			request.setAttribute("career", career);
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

			// リクエストスコープに保存
			request.setAttribute("career", career);
		}catch (SQLException e) {
			throw new ServletException(e);
		}

		//以前の業務を選択した場合
		if(situation =="0") {
			//終了日が未選択なら再度登録画面にフォワード
			Boolean endYError0 = false;
			Boolean endMError0 = false;
			if(endYear.equals("")) {
				endYError0 = true;
				request.setAttribute("endYError", endYError0);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/careerRegister.jsp");
				dispatcher.forward(request, response);
			}
			if(endMonth.equals("")) {
				endMError0 = true;
				request.setAttribute("endMError0", endMError0);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/careerRegister.jsp");
				dispatcher.forward(request, response);
			}
		//現在の業務を選択した場合
		}else {
			//終了日が選択済みなら再度登録画面にフォワード
			Boolean endYError1 = false;
			Boolean endMError1 = false;
			if(endYear.equals("")) {
			}else {
				endYError1 = true;
				request.setAttribute("endYError1", endYError1);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/careerRegister.jsp");
				dispatcher.forward(request, response);
			}
			if(endMonth.equals("")) {
			}else {
				endMError1 = true;
				request.setAttribute("endMError1", endMError1);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/careerRegister.jsp");
				dispatcher.forward(request, response);
			}
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
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/careerRegister.jsp");
			dispatcher.forward(request, response);
		}

		try (Connection connection = ConnectionManager.getConnection()){
			//該当の業務経歴を更新
			CareerLogic careerLogic = new CareerLogic(connection);
			careerLogic.updateCareer(request.getParameter("businessNumber"), startYear, startMonth, endYear, endMonth, businessName, situation);
			connection.commit();
			//従業員一覧画面へリダイレクト
			response.sendRedirect("/SelfIntroduction/EmployeeList?result=careerUpdate");
		}catch (SQLException e) {
			throw new ServletException(e);
		}

		// 従業員詳細画面へフォワード
		//RequestDispatcher dispatcher = request.getRequestDispatcher("/SelfIntroduction/EmployeeDetail");
		//dispatcher.forward(request, response);
	}

}
