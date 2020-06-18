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
 * Servlet implementation class CareerDelete
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
		request.setCharacterEncoding("UTF-8");

		//該当の業務経歴を削除する
		try (Connection connection = ConnectionManager.getConnection()){
			CareerLogic careerLogic = new CareerLogic(connection);
			careerLogic.deleteCareer(request.getParameter("businessNumber"));
			//従業員一覧画面へリダイレクト
			connection.commit();
			response.sendRedirect("/SelfIntroduction/EmployeeList?result=careerdelete");
		}catch (SQLException e) {
			throw new ServletException(e);
		}

		// 従業員詳細画面へフォワード
		//RequestDispatcher dispatcher = request.getRequestDispatcher("/SelfIntroduction/EmployeeDetail");
		//dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
