<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ page import="java.util.List" %>
<%@ page import="model.Certification"%>
<%@ page import="model.Skill"%>
<%
//リクエストスコープからインスタンスを取得
String searchedDeployment = (String) session.getAttribute("searchedDeployment");
String searchedMaster = (String) session.getAttribute("searchedMaster");
String searchedOther = (String) session.getAttribute("searchedOther");
String searchedSkillGenre = (String) session.getAttribute("searchedSkillGenre");
String searchedSkill = (String) session.getAttribute("searchedSkill");
List<Certification> genreList = (List<Certification>)session.getAttribute("genreList");
List<Certification> masterCertificationList = (List<Certification>)session.getAttribute("masterCertificationList");
List<Skill> skillGenreList = (List<Skill>)session.getAttribute("skillGenre");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" >
        <title>従業員一覧</title>
        <link href="./css/common.css" rel="stylesheet">
        <link href="./css/list.css" rel="stylesheet">
        <link href="./css/lib/bootstrap.min.css" rel="stylesheet">
    </head>

    <body class="container-fluid">

      <header>
        <div class="navbar navbar-light bg-light shadow-sm">
            <a href="#" class="navbar-brand">
              <strong>従業員一覧</strong>
            </a>
        </div>
      </header>

        <%-- 登録画面または更新画面から実行後遷移した場合のメッセージ表示 --%>
        <div>
          <c:if test = "${ result == 'register' }"  >
              <label>登録完了しました。</label>
          </c:if>
          <c:if test = "${ result == 'delete' }"  >
              <label>削除完了しました。</label>
          </c:if>
        </div>

        <div class="emp-table">
        <input type="button" class="button" value="新規登録"  onclick="location.href='/SelfIntroduction/EmployeeRegister'">
        <%-- 2020/6/16　追加 --%>
        <%-- 2020/6/18　プルダウン項目データベース参照に変更 --%>
        <%-- 2020/6/19　検索実行後検索条件を検索欄に表示 --%>
        <form action="/SelfIntroduction/EmployeeList" method="post">
	        <br><br><label>所属</label>
	        <select name="deployment">
					<option>所属を選択してください</option>
					<option <%if(searchedDeployment != null && searchedDeployment.equals("本部")){%>
					selected
				<%}%>>本部</option>
					<option  <%if(searchedDeployment != null && searchedDeployment.equals("第1システム部")){%>
					selected
				<%}%>>第1システム部</option>
					<option <%if(searchedDeployment != null && searchedDeployment.equals("第2システム部")){%>
					selected
				<%}%>>第2システム部</option>
					<option <%if(searchedDeployment != null && searchedDeployment.equals("第3システム部")){%>
					selected
				<%}%>>第3システム部</option>
					<option <%if(searchedDeployment != null && searchedDeployment.equals("第4システム部")){%>
					selected
				<%}%>>第4システム部</option>
				<option <%if(searchedDeployment != null && searchedDeployment.equals("その他")){%>
					selected
				<%}%>>その他</option>
				</select>
	        <br><br><label>資格</label>
	        <select name="masterCertification">
	        	<option>ジャンルもしくは資格を選択してください</option>
				<c:forEach var="genre" items="${genreList}">
					<option value="${genre.getCertiCode() }"
					<c:if test="${searchedMaster != null && searchedMaster.equals(genre.getCertiCode()) }">
						selected
					</c:if>
					>${genre.getCertiName()}</option>
				</c:forEach>
				<c:forEach var="certification" items="${masterCertificationList}">
					<option value="${certification.getCertiCode() }"
					<c:if test="${searchedMaster != null && searchedMaster.equals(certification.getCertiCode()) }">
						selected
					</c:if>
					>${certification.getCertiName()}</option>
				</c:forEach>
			</select>
			<input type="text" name="otherCertification" placeholder= "その他資格名" value="${ searchedOther }">

			<br><label>スキル</label>
			<select name="skillGenre">
	        	<option>ジャンルを選択してください</option>
				<c:forEach var="skillGenre" items="${skillGenreList}">
					<option value="${skillGenre.getGenreCode()}"
					<c:if test="${searchedSkillGenre != null && searchedSkillGenre.equals(skillGenre.getGenreCode()) }">
						selected
					</c:if>
					>${skillGenre.getGenreName()}</option>
				</c:forEach>
			</select>
			<input type="text" name="skill"  placeholder="スキル名" value="${ searchedSkill }" >
			<button type="submit" class="searchbutton" name="search" value="検索">検索</button>
			<button type="submit" class="resetbutton" name="reset" value="リセット">検索条件のクリア</button>
		</form>
          <br><br><br>
          <form action="/SelfIntroduction/EmployeeList" method="post">

          	<%--検索後にソートした際検索結果を保持するため入力されているの検索条件を渡す --%>
          	<input type="hidden" name="searchedDeployment" value="${searchedDeployment}">
          	<input type="hidden" name="searchedMaster" value="${searchedMaster}">
          	<input type="hidden" name="searchedOther" value="${searchedOther}">
          	<input type="hidden" name="searchedSkillGenre" value="${searchedSkillGenre}">
          	<input type="hidden" name="searchedSkill" value="${searchedSkill}">
         	<button type="submit" class="sortButton" name="sort" value="資格所持数" >資格所持ランキング</button>
          	<button type="submit" class="sortButton" name="sort" value="従業員番号">従業員番号でソート</button>
        	<label class="number-of-list">従業員数：${empList.size()}名</label>
          </form>

          <table class="table table-bordered table-striped" >
            <thead class="thead-light">
                <tr>
                <%-- 2020/6/17 所属、資格数、現在の業務にclass指定 --%>
                    <th class="empList-number">従業員番号</th>
                    <th class="empList-name">氏名</th>
                    <th class="empList-deployment">所属</th>
                    <th class="empList-profile">プロフィール</th>
                    <th class="empList-count">資格数</th>
                    <th class="empList-business">現在の業務</th>
                    <th class="empList-edit"></th>
                </tr>
            </thead>
            <tbody>
              <%-- 従業員一覧をデータの数だけ繰り返して表示 --%>
              <c:forEach var="employee" items="${empList}">
                  <tr>
                      <td class="number">${ employee.employeeNumber }</td>
                      <td class="name">${ employee.employeeName }</td>
                      <td class="deployment">${ employee.employeeDeployment }</td>
                      <td class="profile">${ employee.employeeProfile }</td>
                      <td class="count">${ employee.count }</td>
                      <td class="business">
               <c:forEach var="career" items="${employee.careerList}">
                     	<c:out value="${career}" /><br>
                      </c:forEach>
                      </td>
                      <%--2020/6/16 編集ボタンから詳細ボタンに変更 --%>
                      <td class="edit"><input type="button" class="button" value="詳細"  onclick="location.href='/SelfIntroduction/EmployeeDetail?employeeNumber=${ employee.employeeNumber }'"></td>
                  </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>


        <script src="./js/jquery-3.3.1.min.js"></script>
        <script src="./js/bootstrap.bundle.min.js"></script>

    </body>
</html>