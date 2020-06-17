package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConnectionManager;
import dao.DetailDAO;
import model.Career;
import model.Employee;
import model.EmployeeLogic;
/**
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 *  従業員詳細情報のコントローラクラス<br>
 */
@WebServlet("/EmployeeDetail")
public class EmployeeDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeDetail() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		try (Connection connection = ConnectionManager.getConnection()) {
			// 該当の従業員を取得
			EmployeeLogic employeeLogic = new EmployeeLogic(connection);
			Employee emp = employeeLogic.getEmployee(request.getParameter("employeeNumber"));
			List<Career> careerList = new DetailDAO(connection).getAllCareer(request.getParameter("employeeNumber"));

			// リクエストスコープに保存
			request.setAttribute("emp", emp);
			request.setAttribute("careerList", careerList);

		} catch (SQLException e) {
			throw new ServletException(e);
		}


		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detail.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
