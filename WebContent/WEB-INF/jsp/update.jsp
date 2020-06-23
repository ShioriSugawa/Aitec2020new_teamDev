<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%@ page import="model.Employee" %>
<%@ page import="servlet.EmployeeUpdate" %>
<%
//リクエストスコープからインスタンスを取得
Employee emp = (Employee) request.getAttribute("emp");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" >
        <title>従業員情報編集</title>
        <link href="./css/common.css" rel="stylesheet">
        <link href="./css/lib/bootstrap.min.css" rel="stylesheet">
        <link href="./css/update.css" rel="stylesheet">
    </head>
    <body class="container-fluid">
      <header>
        <div class="navbar navbar-light bg-light shadow-sm">
            <a href="#" class="navbar-brand">
              <strong>従業員情報編集</strong>
            </a>
        </div>
      </header>

        <label class="update-number">従業員番号：${ emp.employeeNumber }</label>

        <%-- プロフィールを取得して表示。編集された内容をPOSTで更新する。 --%>
        <form class="update-form" action="/SelfIntroduction/EmployeeUpdate?employeeNumber=${ emp.employeeNumber }" method="post" onSubmit="return confirmUpdate()">
            <div class="update-name">
                <label>氏名（30文字以内）</label>		<%-- 2020.05.27 10→30文字に変更 --%>
                <br/>
                <input type="text" name="employeeName" class="update-name-input" maxlength='30' placeholder='氏名を入力してください。' value="${ emp.employeeName }" required >		<%-- 2020.05.28 maxlength='30'→maxlength='30'に変更 --%>
            </div>
            <%--2020/6/16　追加 --%>
             <label>所属</label><br>
             <select name="deployment">
				<option>所属を選択してください</option>
				<%--部署nullの従業員を編集しようとするとJasperException --%>
				<option <%if(emp.getEmployeeDeployment().equals("部署1")){%>
					selected
				<%}%>>部署1</option>
				<option <%if(emp.getEmployeeDeployment().equals("部署2")){%>
					selected
				<%}%>>部署2</option>
				<option <%if(emp.getEmployeeDeployment().equals("部署3")){%>
					selected
				<%}%>>部署3</option>
				<option <%if(emp.getEmployeeDeployment().equals("部署4")){%>
					selected
				<%}%>>部署4</option>
				<option <%if(emp.getEmployeeDeployment().equals("部署5")){%>
					selected
				<%}%>>部署5</option>
			</select>

			<%--2020/6/15 所属未選択で遷移されて来たらエラーメッセージ表示 --%>
			<c:if test = "${ noInputError == true }"  >
	            <font color="red">
					<p>所属を未選択です。</p>
	            </font>
        	</c:if>
            <div class="update-profile">
                <br><label>プロフィール（100文字以内）</label>
                <br/>
                <textarea name="employeeProfile" class="update-profile-input" rows="6" cols="40" maxlength="100" placeholder="プロフィールを入力してください。" required >${ emp.employeeProfile }</textarea>
            </div>
            <input type="hidden" value="${emp.employeeNumber }">
            <%--キャンセル時遷移先一覧画面から詳細画面に変更 --%>
            <input type="button" class="button"value="キャンセル" onclick="location.href='/SelfIntroduction/EmployeeDetail?employeeNumber=${ emp.employeeNumber }'">
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