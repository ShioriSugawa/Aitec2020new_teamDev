<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" >
        <title>新規登録</title>
        <link href="./css/common.css" rel="stylesheet">
        <link href="./css/register.css" rel="stylesheet">
        <link href="./css/lib/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container-fluid">
      <header>
        <div class="navbar navbar-light bg-light shadow-sm">
            <a href="#" class="navbar-brand">
              <strong>新規登録</strong>
            </a>
        </div>
      </header>

        <%-- 入力した内容をPOSTで更新する。 --%>
        <form class="register-form" action="/SelfIntroduction/EmployeeRegister" method="post" onSubmit="return confirmRegister()">
            <div class="register-number" >
                <label>従業員番号（半角数字6桁）</label>
                <br/>
                <input type="text" name="employeeNumber" class="register-number-input" maxlength='6' style="ime-mode:disabled" placeholder='従業員番号を入力してください。' value="${ emp.employeeNumber }" required pattern="[0-9]{6}">
            </div>
            <div class="register-name">
                <label>氏名（30文字以内）</label>		<%-- 2020.05.27 10→30文字に変更 --%>
                <br/>
                <input type="text" name="employeeName" class="register-name-input" maxlength='30' placeholder='氏名を入力してください。' value="${ emp.employeeName }" required >		<%-- 2020.05.28 maxlength='30'→maxlength='30'に変更 --%>
            </div>
            <div class="register-profile">
                <label>プロフィール（100文字以内）</label>
                <br/>
                <textarea name="employeeProfile" class="register-profile-input" rows="6" cols="40" maxlength="100" placeholder="プロフィールを入力してください。" required >${ emp.employeeProfile }</textarea>
            </div>
            <input type="button" class="button" value="キャンセル"  onclick="location.href='/SelfIntroduction/EmployeeList'">
            <input type="submit" class="button register-button" value="登録">
        </form>

        <%-- 従業員番号が既存データと重複している場合のエラー表示。 --%>
        <c:if test = "${ hasError == true }"  >
            <font color="red">
				<p>既に存在している従業員番号です。</p>
            </font>
        </c:if>

      <script type="text/javascript">
          function confirmRegister(){
              // 確認ダイアログの表示
              if(window.confirm('登録してよろしいでしょうか？')){
                  // 「OK」時の処理
                  return true; // 更新処理実行（post送信）
              }
              return false; // キャンセル時は何もしない
          }
      </script>
      <script src="./js/jquery-3.3.1.min.js"></script>
      <script src="./js/bootstrap.bundle.min.js"></script>

    </body>
</html>