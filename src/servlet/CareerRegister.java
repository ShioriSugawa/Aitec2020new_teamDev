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
import model.CareerLogic;
import model.Employee;
import model.EmployeeLogic;

/**
 * Servlet implementation class CareerRegister
 */
@WebServlet("/CareerRegister")
public class CareerRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CareerRegister() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		try (Connection connection = ConnectionManager.getConnection()) {
			// 該当の従業員を取得
			EmployeeLogic employeeLogic = new EmployeeLogic(connection);
			Employee employee = employeeLogic.getEmployee(request.getParameter("employeeNumber"));

			// リクエストスコープに保存
			request.setAttribute("employee", employee);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/careerRegister.jsp");
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


		//開始日が未選択なら再度登録画面にフォワード
		Boolean startYError = false;
		Boolean startMError = false;
		if(startYear.equals("")) {
			startYError = true;
			request.setAttribute("startYError", startYError);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/careerRegister.jsp");
			dispatcher.forward(request, response);
		}
		if(startMonth.equals("")) {
			startMError = true;
			request.setAttribute("startMError", startMError);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/careerRegister.jsp");
			dispatcher.forward(request, response);
		}

		//以前の業務を選択した場合
		if(situation =="0") {
			//終了日が未選択なら再度登録画面にフォワード
			Boolean endYError = false;
			Boolean endMError = false;
			if(endYear.equals("")) {
				endYError = true;
				request.setAttribute("endYError", endYError);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/careerRegister.jsp");
				dispatcher.forward(request, response);
			}
			if(endMonth.equals("")) {
				endMError = true;
				request.setAttribute("endMError", endMError);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/careerRegister.jsp");
				dispatcher.forward(request, response);
			}
		}

		try (Connection connection = ConnectionManager.getConnection()){
			CareerLogic careerLogic = new CareerLogic(connection);
			careerLogic.registerCareer(employeeNumber, startYear, startMonth, endYear, endMonth, businessName, situation);
			// 従業員詳細画面へフォワード
			//RequestDispatcher dispatcher = request.getRequestDispatcher("/SelfIntroduction/EmployeeDetail?employeeNumber");
			//dispatcher.forward(request, response);
			//従業員一覧画面へリダイレクト
			connection.commit();
			response.sendRedirect("/SelfIntroduction/EmployeeList?result=careerRegister");
		}catch (SQLException e) {
			throw new ServletException(e);
		}

	}

}
