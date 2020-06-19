package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 *  従業員一覧のコントローラクラス<br>
 */

/*
 * 修正内容まとめ
 * 2020/6/18 ソート機能追加
 * 2020/6/18 検索機能追加
 */
@WebServlet("/EmployeeList")
public class EmployeeList extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeList() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//文字コードエンコーディング
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		try (Connection connection = ConnectionManager.getConnection()) {
			// 従業員一覧を取得
			EmployeeLogic employeeLogic = new EmployeeLogic(connection);
			List<Employee> empList = employeeLogic.getEmployeeList();

			// 2020/6/18　追加
			//検索項目をDBより取得
			List<String> genreList = employeeLogic.getGenreList();
			List<String> masterCertificationList = employeeLogic.getCertificationName();
			List<String> skillGenreList = employeeLogic.getSkillGenreList();


			// リクエストスコープに保存
			request.setAttribute("empList", empList);
			request.setAttribute("genreList", genreList);
			request.setAttribute("masterCertificationList", masterCertificationList);
			request.setAttribute("skillGenreList", skillGenreList);
			request.setAttribute("result", request.getParameter("result")); // 登録・更新・削除後に遷移してくる場合に格納
		} catch (SQLException e) {
			throw new ServletException(e);
		}

		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/list.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 2020/6/17 追加
		//文字コードエンコーディング
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		try (Connection connection = ConnectionManager.getConnection()) {
			// 従業員一覧を取得
			EmployeeLogic employeeLogic = new EmployeeLogic(connection);
			ArrayList<Employee> empList = employeeLogic.getEmployeeList();

			//検索ボタンが押下されていた場合条件に応じてリスト絞り込み
			String searchButton = request.getParameter("search");
			String deployment = request.getParameter("deployment");
			String masterCertification = request.getParameter("mastercertification");
			String otherCertification = request.getParameter("otherCertification");
			String skillGenre = request.getParameter("skillGenre");
			String skill = request.getParameter("skill");

			//
			if(searchButton != null) {
				empList = employeeLogic.searchEmployee(deployment, masterCertification, otherCertification, skillGenre,skill);
			}

			//押下されたボタンに応じてソート
			String sortButton = request.getParameter("sort");

			if(sortButton != null) {
				employeeLogic.sortArrayList(sortButton, empList);
			}

			// リクエストスコープに保存
			request.setAttribute("empList", empList);
			request.setAttribute("searchedDeployment", deployment);
			request.setAttribute("searchedMaster", masterCertification);
			request.setAttribute("searchedOther", otherCertification);
			request.setAttribute("searchedSkillGenre", skillGenre);
			request.setAttribute("searchedSkill", skill);
		} catch (SQLException e) {
			throw new ServletException(e);
		}

		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/list.jsp");
		dispatcher.forward(request, response);
	}

}
