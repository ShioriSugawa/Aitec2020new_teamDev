<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
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
          <table class="table table-bordered table-striped" >
            <thead class="thead-light">
                <tr>
                    <th class="empList-number">従業員番号</th>
                    <th class="empList-name">氏名</th>
                    <th class="empList-profile">プロフィール</th>
                    <th class="empList-edit"></th>
                </tr>
            </thead>
            <tbody>
              <%-- 従業員一覧をデータの数だけ繰り返して表示 --%>
              <c:forEach var="employee" items="${empList}">
                  <tr>
                      <td>${ employee.employeeNumber }</td>
                      <td>${ employee.employeeName }</td>
                      <td>${ employee.employeeProfile }</td>
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