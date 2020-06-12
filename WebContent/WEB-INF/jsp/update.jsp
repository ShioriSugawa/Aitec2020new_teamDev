<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" >
        <title>編集</title>
        <link href="./css/common.css" rel="stylesheet">
        <link href="./css/lib/bootstrap.min.css" rel="stylesheet">
        <link href="./css/update.css" rel="stylesheet">
    </head>
    <body class="container-fluid">
      <header>
        <div class="navbar navbar-light bg-light shadow-sm">
            <a href="#" class="navbar-brand">
              <strong>編集</strong>
            </a>
        </div>
      </header>

        <label class="update-number">従業員番号：${ emp.employeeNumber }</label>
        <input type="button" class="button delete-button" value="削除" onclick="confirmDelete()">

        <%-- プロフィールを取得して表示。編集された内容をPOSTで更新する。 --%>
        <form class="update-form" action="/SelfIntroduction/EmployeeUpdate?employeeNumber=${ emp.employeeNumber }" method="post" onSubmit="return confirmUpdate()">
            <div class="update-name">
                <label>氏名（30文字以内）</label>		<%-- 2020.05.27 10→30文字に変更 --%>
                <br/>
                <input type="text" name="employeeName" class="update-name-input" maxlength='30' placeholder='氏名を入力してください。' value="${ emp.employeeName }" required >		<%-- 2020.05.28 maxlength='30'→maxlength='30'に変更 --%>
            </div>
            <div class="update-profile">
                <label>プロフィール（100文字以内）</label>
                <br/>
                <textarea name="employeeProfile" class="update-profile-input" rows="6" cols="40" maxlength="100" placeholder="プロフィールを入力してください。" required >${ emp.employeeProfile }</textarea>
            </div>
            <input type="button" class="button"value="キャンセル" onclick="location.href='/SelfIntroduction/EmployeeList'">
            <input type="submit" class="button update-button" value="更新">
        </form>

      <script type="text/javascript">
          function confirmUpdate(){
              // 確認ダイアログの表示
              if(window.confirm('更新してよろしいでしょうか？')){
                  // 「OK」時の処理
                  return true; // 更新処理実行（post送信）
              }
              return false; // キャンセル時は何もしない
          }

          function confirmDelete(){
              // 確認ダイアログの表示
              if(window.confirm('従業員を削除してよろしいでしょうか？')){
                  // 「OK」時の処理
                  location.href = '/SelfIntroduction/EmployeeDelete?employeeNumber=${ emp.employeeNumber }'; // 削除処理実行
              }
              // キャンセル時は何もしない
          }
      </script>
      <script src="./js/jquery-3.3.1.min.js"></script>
      <script src="./js/bootstrap.bundle.min.js"></script>

    </body>
</html>