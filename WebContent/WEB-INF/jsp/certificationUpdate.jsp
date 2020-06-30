<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%@ page import="model.Certification"%>
<%@ page import="java.util.List"%>

<% Certification mst=(Certification)request.getAttribute("mst");%>

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

		<label>資格名：${mst.certiName}</label>
		<input type="button" class="button" value="削除" onclick="confirmDelete()">
		<br>

		<label>ジャンル：${mst.certiGenre}</label><br>

		<form name="mUp" action="/SelfIntroduction/SkillsUpdate" method="post" >

			<input type="hidden" name="MownedId" value="${mst.ownedId}">
			<input type="hidden" name="employeeNumber" value="${mst.employeeNumber }">

			<label>取得日：</label>
				<select name="mstYear" required>
					<option disabled>年</option>
					<c:forEach var="MyL" items="${yearL}">
						<option value="${MyL}" <c:if test="${MyL == sYeI}">selected</c:if> >
							<c:out value="${MyL}年" />
						</option>
					</c:forEach>
					<option value="70以前" <c:if test="${0 == sYeI}">selected</c:if>>1970年～</option>
				</select>

				<select name="mstMonth" required>
					<option disabled> 月 </option>
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

			<input type="button" class="button" value="キャンセル"  onclick="location.href='/SelfIntroduction/EmployeeDetail?employeeNumber=${mst.employeeNumber}'">
			<input type="button" class="button register-button" value="更新" onclick="confirmUpdate()">

		</form>

		<script type="text/javascript">
			function confirmUpdate(){
				// 確認ダイアログの表示
				if(window.confirm('更新してよろしいでしょうか？')){
					// 「OK」時の処理
					document.mUp.submit();
					//return true; // 更新処理実行（post送信）
				}
				return false; // キャンセル時は何もしない
			}

			function confirmDelete(){
				// 確認ダイアログの表示
				if(window.confirm('この資格情報を削除してよろしいでしょうか？')){
					// 「OK」時の処理
					location.href = '/SelfIntroduction/SkillsDelete?owned_certification_id=${ mst.ownedId }&employeeNumber=${mst.employeeNumber}'; // 削除処理実行
				}
			// キャンセル時は何もしない
			}
		</script>
		<script src="./js/jquery-3.3.1.min.js"></script>
		<script src="./js/bootstrap.bundle.min.js"></script>

	</body>

</html>