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
import javax.servlet.http.HttpSession;

import dao.ConnectionManager;
import model.Certification;
import model.CertificationLogic;
import model.Employee;
import model.EmployeeLogic;
import model.Skill;
import model.SkillLogic;

/**
 * Copyright 2020 FUJITSU SOCIAL SCIENCE LABORATORY LIMITED / AitecTresure＆Toraja Co.,Ltd<br>
 * システム名：自己紹介システム<br>
 * クラス概要：<br>
 *  従業員一覧のコントローラクラスです。<br>
 */

/*
 * 修正内容まとめ
 * 2020/6/18 ソート機能追加
 * 2020/6/18 検索機能追加
 * 2020/6/19 検索リセット機能追加
 * 2020/6/19 検索後ソート時絞り込まれたままソート
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

			// 2020/7/2 追加　検索後詳細画面へ遷移後一覧に戻った際に検索結果保持させる
			HttpSession session = request.getSession();
			@SuppressWarnings("unchecked")
			List<Employee> empList =  (ArrayList<Employee>) session.getAttribute("empList");
			//初期表示などでセッションスコープに値が入っていない場合新規作成
			if(empList == null) {
				empList = employeeLogic.getEmployeeList();
			}

			// 2020/6/18　追加
			//検索項目をDBより取得
			SkillLogic skillLogic = new SkillLogic(connection);
			CertificationLogic certificationLogic = new CertificationLogic(connection);
			List<Certification> genreList = certificationLogic.getCertiGenre();
			List<Certification> masterCertificationList = certificationLogic.getCertiName();
			List<Skill> skillGenreList = skillLogic.getGenre();


			//セッションスコープに保存
			session.setAttribute("empList", empList);
			// リクエストスコープに保存
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
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 2020/6/17 追加
		//文字コードエンコーディング
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();

		try (Connection connection = ConnectionManager.getConnection()) {
			// 従業員一覧を取得
			EmployeeLogic employeeLogic = new EmployeeLogic(connection);
			ArrayList<Employee> empList = employeeLogic.getEmployeeList();

			//検索項目をDBより取得
			SkillLogic skillLogic = new SkillLogic(connection);
			CertificationLogic certificationLogic = new CertificationLogic(connection);
			List<Certification> genreList = certificationLogic.getCertiGenre();
			List<Certification> masterCertificationList = certificationLogic.getCertiName();
			List<Skill> skillGenreList = skillLogic.getGenre();

			//検索ボタンが押下されていた場合条件に応じてリスト絞り込み
			String searchButton = request.getParameter("search");
			String deployment = request.getParameter("deployment");
			String masterCertification = request.getParameter("masterCertification");	//資格ジャンルorマスター登録有資格
			String otherCertification = request.getParameter("otherCertification");
			String skillGenre = request.getParameter("skillGenre");
			String skill = request.getParameter("skill");
			if(searchButton != null) {
				empList = employeeLogic.searchEmployee(deployment, masterCertification, otherCertification, skillGenre,skill);
			}

			//検索条件リセットが押下されていた場合　再度DBより一覧取得
			String resetButton = request.getParameter("reset");
			if(resetButton != null) {
				empList = employeeLogic.getEmployeeList();
				//入力されていた検索条件情報初期化
				deployment = "所属を選択してください";
				masterCertification = null;
				otherCertification = null;
				skillGenre = null;
				skill = null;
			}

			//押下されたボタンに応じてソート
			String sortButton = request.getParameter("sort");
			if(sortButton != null) {
				deployment = request.getParameter("searchedDeployment");
				masterCertification = request.getParameter("searchedMaster");
				otherCertification = request.getParameter("searchedOther");
				skillGenre = request.getParameter("searchedSkillGenre");
				skill = request.getParameter("searchedSkill");
				empList = (ArrayList<Employee>) session.getAttribute("empList");
				employeeLogic.sortArrayList(sortButton, empList);
			}

			// 検索結果保持したままソート等リクエストを超えて保持する必要があるためセッションスコープに保存
			session.setAttribute("empList", empList);
			session.setAttribute("genreList", genreList);
			session.setAttribute("masterCertificationList", masterCertificationList);
			session.setAttribute("skillGenreList", skillGenreList);
			session.setAttribute("searchedDeployment", deployment);
			session.setAttribute("searchedMaster", masterCertification);
			session.setAttribute("searchedOther", otherCertification);
			session.setAttribute("searchedSkillGenre", skillGenre);
			session.setAttribute("searchedSkill", skill);

		} catch (SQLException e) {
			throw new ServletException(e);
		}

		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/list.jsp");
		dispatcher.forward(request, response);
	}

}
