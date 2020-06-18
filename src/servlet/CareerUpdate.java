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
