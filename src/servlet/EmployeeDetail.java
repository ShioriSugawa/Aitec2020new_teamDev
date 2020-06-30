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
import model.Career;
import model.Employee;
import model.EmployeeLogic;
/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED / AitecTresure＆Toraja Co.,Ltd<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 *  従業員詳細情報のコントローラクラスです。<br>
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
			String employeeNumber = request.getParameter("employeeNumber");
			//
			if(employeeNumber == null) {
				employeeNumber = (String)request.getAttribute("employeeNumber");
			}
			Employee emp = employeeLogic.getEmployee(employeeNumber);
			List<Career> careerList = employeeLogic.getCareerList(employeeNumber);
			List<Employee> masterCertificationList = employeeLogic.getMasterCertificationList(employeeNumber);
			List<Employee> othersList = employeeLogic.getOtherCertificationList(employeeNumber);
			//2020/6/26追加
			/*一端保留
			 * List<Employee> allCertificationList = new ArrayList<>();
			allCertificationList.addAll(masterCertificationList);
			allCertificationList.addAll(othersList);
			//取得日降順にソート
			allCertificationList.sort((a,b)-> b.getCertificationDate().compareTo(a.getCertificationDate()) );
			*/

			List<Employee> skillList = employeeLogic.getSkillList(employeeNumber);

			// リクエストスコープに保存
			request.setAttribute("emp", emp);
			request.setAttribute("careerList", careerList);
			request.setAttribute("masterCertificationList", masterCertificationList);
			request.setAttribute("othersList", othersList);
			//2020/6/26追加
			//一端保留
			//request.setAttribute("allCertificationList", allCertificationList);
			request.setAttribute("skillList", skillList);

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
