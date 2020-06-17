<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model.Career" %>

<%
//リクエストスコープからインスタンスを取得
Career career = (Career) request.getAttribute("career");
%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>業務経歴編集</title>
		<link href="./css/common.css" rel="stylesheet">
	</head>

	<body class="container-fluid">
		<header>
			<div class="navbar navbar-light bg-light shadow-sm">
				<a href="#" class="navbar-brand">
					<strong>業務経歴編集</strong>
				</a>
			</div>
		</header>

		<%-- 業務経歴を取得して表示。編集された内容をPOSTで更新する。 --%>
		<form class="careerUpdate-form" action="/SelfIntroduction/CareerUpdate?businessNumber=${ career.businessNumber }" method="post" onSubmit="return confirmUpdate()">

			<div class="careerUpdate-start">

				<label>業務開始</label>
				<select name="startYear">
					<option value="">-</option>
				</select>

				<select name="startMonth">
					<option value="">-</option>
				</select>

				<label>業務開始</label>
				<select name="endYear">
					<option value="">-</option>
				</select>

				<select name="endMonth">
					<option value="">-</option>
				</select>

			</div>

			<div class="situation">
				<input type="radio" name="situation" value="1">現在の業務
				<input type="radio" name="situation" value="0">以前の業務
				<br/>
			</div>

			<input type="button" class="button" value="キャンセル"  onclick="location.href='/SelfIntroduction/EmployeeDetail?employeeNumber=${ emp.employeeNumber }'">
			<input type="submit" class="button careerRegister-button" value="更新">

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