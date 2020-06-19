<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ page import="java.util.List" %>
<%
//リクエストスコープからインスタンスを取得
String searchedDeployment = (String) request.getAttribute("searchedDeployment");
String searchedMaster = (String) request.getAttribute("searchedMaster");
String searchedOther = (String) request.getAttribute("searchedOther");
String searchedSkillGenre = (String) request.getAttribute("searchedSkillGenre");
String searchedSkill = (String) request.getAttribute("searchedSkill");
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
          <c:if test = "${ result == 'update' }"  >
              <label>更新完了しました。</label>
          </c:if>
          <c:if test = "${ result == 'delete' }"  >
              <label>削除完了しました。</label>
          </c:if>
        </div>

        <div class="emp-table">
        <input type="button" class="button" value="新規登録"  onclick="location.href='/SelfIntroduction/EmployeeRegister'">
        <label class="number-of-list">従業員数：${empList.size()}名</label>
        <%-- 2020/6/16　追加 --%>
        <%-- 2020/6/18　プルダウン項目データベース参照に変更 --%>
        <%-- 2020/6/19　検索実行後検索条件を検索欄に表示 --%>
        <form action="/SelfIntroduction/EmployeeList" method="post">
	        <br><br><label>所属</label>
	        <select name="deployment">
					<option>所属を選択してください</option>
					<option <%if(searchedDeployment != null && searchedDeployment.equals("部署1")){%>
					selected
				<%}%>>部署1</option>
					<option  <%if(searchedDeployment != null && searchedDeployment.equals("部署2")){%>
					selected
				<%}%>>部署2</option>
					<option <%if(searchedDeployment != null && searchedDeployment.equals("部署3")){%>
					selected
				<%}%>>部署3</option>
					<option <%if(searchedDeployment != null && searchedDeployment.equals("部署4")){%>
					selected
				<%}%>>部署4</option>
					<option <%if(searchedDeployment != null && searchedDeployment.equals("部署5")){%>
					selected
				<%}%>>部署5</option>
				</select>
	        <br><br><label>資格</label>
	        <select name=”masterCertification” class="genre">
	        	<option>ジャンルもしくは資格を選択してください</option>
				<c:forEach var="genre" items="${genreList}">
					<option><c:out value="${genre}" /></option>
				</c:forEach>
				<c:forEach var="certification" items="${certificationList}">
					<option><c:out value="${certification}" /></option>
				</c:forEach>
			</select>
			<input type="text" name="otherCertification" placeholder= "その他資格名" value="${ searchedOther }">

				<br><label>スキル</label>
				<select name=”skillGenre”>
	        	<option>ジャンルを選択してください</option>
				<c:forEach var="skillGenre" items="${skillGenreList}">
					<option><c:out value="${skillGenre}" /></option>
				</c:forEach>
			</select>
				<input type="text" name="skill"  placeholder="スキル名" value="${ searchedSkill }" >
			<button type="submit" class="searchbutton" name="search" value="検索">検索</button>
			<button type="submit" class="resetbutton" name="search" value="リセット">検索条件のクリア</button>
		</form>
          <br><br><br>
          <form action="/SelfIntroduction/EmployeeList" method="post">
         	<button type="submit" class="sortButton" name="sort" value="資格所持数">資格所持ランキング</button>
          	<button type="submit" class="sortButton" name="sort" value="従業員番号">従業員番号でソート</button>
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
                      <td>${ employee.employeeNumber }</td>
                      <td>${ employee.employeeName }</td>
                      <td>${ employee.employeeDeployment }</td>
                      <td>${ employee.employeeProfile }</td>
                      <td>${ employee.count }</td>
                      <td>
               <c:forEach var="career" items="${employee.careerList}">
                     	<c:out value="${career}" /><br>
                      </c:forEach>
                      </td>
                      <%--2020/6/16 編集ボタンから詳細ボタンに変更 --%>
                      <td><input type="button" class="button" value="詳細"  onclick="location.href='/SelfIntroduction/EmployeeDetail?employeeNumber=${ employee.employeeNumber }'"></td>
                  </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>


        <script src="./js/jquery-3.3.1.min.js"></script>
        <script src="./js/bootstrap.bundle.min.js"></script>

    </body>
</html>