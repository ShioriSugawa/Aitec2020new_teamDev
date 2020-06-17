<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ page import="java.util.List" %>
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
        <br><br><label>所属</label>
        <select name="certification">
				<option>所属を選択してください</option>
				<option>部署1</option>
				<option>部署2</option>
				<option>部署3</option>
				<option>部署4</option>
				<option>部署5</option>
			</select>
        <br><br><label>資格</label>
        <select name=”Genre” class="genre">
        	<option>ジャンルもしくは資格を選択してください</option>
			<option>ジャンル1</option>
			<option>ジャンル2</option>
			<option>ジャンル3</option>
			<option>資格1</option>
			<option>資格2</option>
		</select>
		<input type="text" name="skill"  placeholder="資格を入力してください" >

			<br><label>スキル</label>
			<select name=”Genre” class="genre">
        	<option>ジャンルを選択してください</option>
			<option>ジャンル1</option>
			<option>ジャンル2</option>
			<option>ジャンル3</option>
			<option>ジャンル4</option>
			<option>ジャンル5</option>
		</select>
			<input type="text" name="skill"  placeholder="スキルを入力してください" >
		<input type="button" class="searchbutton" name="searchbutton" value="検索">
          <br><br><br>
          <input type="button" class="searchbutton" name="searchbutton" value="資格所持数ランキング">
          <input type="button" class="searchbutton" name="searchbutton" value="従業員番号でソート">

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
                      <td>未実装</td>
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