<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ page import="model.Skill"%>
<%@ page import="servlet.SkillsUpdate"%>
<%@ page import ="java.util.List" %>


<% List<Skill> skillGenre = (List<Skill>)request.getAttribute("skillGenre");%>
<% Skill oSkl=(Skill)request.getAttribute("oSkl");%>

<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" >
		<title>スキル情報編集</title>
		<link href="./css/common.css" rel="stylesheet">
        <link href="./css/lib/bootstrap.min.css" rel="stylesheet">
        <link href="./css/update.css" rel="stylesheet">
	</head>

	<body>
		<header>
			<div class="navbar navbar-light bg-light shadow-sm">
				<a href="#" class="navbar-brand">
				<strong>スキル情報編集</strong>
				</a>
			</div>
		</header>

        <form class="register-form" action="/SelfIntroduction/SkillsUpdate" method="post" onSubmit="return confirmUpdate()">

		<input type="hidden" name="SownedId" value="${oSkl.ownedId}">
		<input type="hidden" name="employeeNumber" value="${oSkl.employeeNumber}">

		<label>ジャンル：</label>
		<select name="skillGenre" required>
			<option disabled>ジャンルを選択してください</option>
			<c:forEach var="skillGenre" items="${skillGenre}">
				<option value="${skillGenre.getGenreCode() }"
					<c:if test="${ oSkl.genreName.equals(skillGenre.getGenreName()) }">selected</c:if> >
					<c:out value="${skillGenre.getGenreName()}" />
				</option>
			</c:forEach>
		</select>
		<input type="button" class="button" value="削除" onclick="confirmDelete()"><br>
		<br>

		<label>スキルの内容（100文字以内）</label><br>
			<textarea name="skillName" maxlength='100' placeholder="スキルの内容を具体的に記入してください">${ oSkl.skillName }</textarea>
			<br>
            <input type="button" class="button" value="キャンセル"  onclick="location.href='/SelfIntroduction/EmployeeDetail?employeeNumber=${oSkl.employeeNumber}'">
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
				if(window.confirm('このスキルを削除してよろしいでしょうか？')){
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