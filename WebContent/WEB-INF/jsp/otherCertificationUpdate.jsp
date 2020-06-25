<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%@ page import="model.Certification"%>
<%@ page import="java.util.List"%>

<% List<Certification> cGenL=(List<Certification>)request.getAttribute("cGenL");%>
<% Certification oth=(Certification)request.getAttribute("oth");%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>資格情報編集</title>
		<link href="./css/common.css" rel="stylesheet">
		<link href="./css/lib/bootstrap.min.css" rel="stylesheet">
		<link href="./css/update.css" rel="stylesheet">
	</head>

	<body>
		<header>
			<div class="navbar navbar-light bg-light shadow-sm">
				<a href="#" class="navbar-brand">
				<strong>資格情報編集</strong>
				</a>
			</div>
		</header>

		<form class="register-form" action="/SelfIntroduction/SkillsUpdate" method="post" onSubmit="return confirmUpdate()">

			<input type="hidden" name="OownedId" value="${oth.ownedId}">
			<input type="hidden" name="employeeNumber" value="${oth.employeeNumber }">

			<label>資格名：</label><%-- cssは時間があれば後で書き足す --%>
				<input type="text" name="certiName" maxlength='100' placeholder='資格名を入力してください' value="${ oth.certiName }" required>
				<input type="button" class="button" value="削除" onclick="confirmDelete()"><br>


			<%-- なぜかジャンル一覧とselectedがでねえ！！後は頼んだ！ --%>
			<label>ジャンル：</label>
			<select name="genreCode" required>
				<option disabled>ジャンルを選択してください</option>

				<c:forEach var="cGL" items="${cGenL}">
					<option value="${cGL.getCertiCode()}"
						 <c:if test="${ oth.certiGenre.equals(cGL.getCertiGenre()) }">selected</c:if>>
						<c:out value="${cGL.getCertiGenre()}" />
					</option>
				</c:forEach>
			</select><br>

			<label>認定日：</label>
				<select name="othYear" required>
					<option disabled>-</option>
					<c:forEach begin="1950" end="${nowYear}" step="1" var="i">
						<option value="${i}" <c:if test="${i == sYeI}">selected</c:if> >
							<c:out value="${i}年" />
						</option>
					</c:forEach>
				</select>

				<select name="othMonth" required>
					<option disabled>-</option>
					<c:forEach begin="1" end="9" step="1" var="i">
						<option value="0${i}" <c:if test="${ i == sMonI }">selected</c:if> >
							<c:out value="${i}月" />
						</option>
					</c:forEach>
					<c:forEach begin="10" end="12" step="1" var="i">
						<option value="${i}" <c:if test="${ i == sMonI }">selected</c:if> >
							<c:out value="${i}月" />
						</option>
					</c:forEach>
				</select>
			<br>

			<input type="button" class="button" value="キャンセル"  onclick="location.href='/SelfIntroduction/EmployeeDetail?employeeNumber=${oth.employeeNumber}'">
			<input type="submit" class="button register-button" value="更新">

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
				if(window.confirm('この資格情報を削除してよろしいでしょうか？')){
					// 「OK」時の処理
					location.href = '/SelfIntroduction/SkillsDelete?owned_skill_id=${ skl.ownedId }'; // 削除処理実行
				}
			// キャンセル時は何もしない
			}
		</script>
		<script src="./js/jquery-3.3.1.min.js"></script>
		<script src="./js/bootstrap.bundle.min.js"></script>


	</body>

</html>