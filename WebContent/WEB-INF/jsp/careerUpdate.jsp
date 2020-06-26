<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%@ page import="model.Career" %>
<%@ page import="servlet.CareerUpdate" %>

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
		<link href="./css/career.css" rel="stylesheet">
		<link href="./css/lib/bootstrap.min.css" rel="stylesheet">
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

			<div class="career-start">


				<label>業務開始</label>
				<select name="startYear" required>
					<option value="">-</option>
					<c:forEach begin="1980" end="${nowYear}" step="1" var="i">
						<option value="${i}" <c:if test="${i == startYear}">selected</c:if> >
							<c:out value="${i}" />
						</option>
					</c:forEach>
				</select>
				<label>年</label>

				<select name="startMonth" required>
					<option value="">-</option>
					<c:forEach begin="01" end="12" step="1" var="i">
						<option value="0${i}" <c:if test="${ i == startMonth }">selected</c:if> >
							<c:out value="${i}" />
						</option>
					</c:forEach>
				</select>
				<label>月</label>

			</div>

			<div class="career-end">

			<% if(career.getBusinessEnd() ==null){ %>

				<label>業務終了</label>
				<select name="endYear">
					<option value="">-</option>
					<c:forEach end="${nowYear}" begin="1980" step="1" var="i">
						<option value="${i}" >
							<c:out value="${i}" />
						</option>
					</c:forEach>
				</select>
				<label>年</label>

				<select name="endMonth">
					<option value="">-</option>
					<option value="01">01</option>
					<option value="02">02</option>
					<option value="03">03</option>
					<option value="04">04</option>
					<option value="05">05</option>
					<option value="06">06</option>
					<option value="07">07</option>
					<option value="08">08</option>
					<option value="09">09</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
				</select>
				<label>月</label>

			<%}else{ %>


				<label>業務終了</label>
				<select name="endYear">
					<option value="">-</option>
					<c:forEach begin="1980" end="${nowYear}" step="1" var="i">
						<option value="${i}" <c:if test="${i == endYear}">selected</c:if> >
							<c:out value="${i}" />
						</option>
					</c:forEach>
				</select>
				<label>年</label>

				<select name="endMonth">
					<option value="">-</option>
					<c:forEach begin="01" end="12" step="1" var="i">
						<option value="0${i}" <c:if test="${ i == startMonth }">selected</c:if> >
							<c:out value="${i}" />
						</option>
					</c:forEach>
				</select>
				<label>月</label>
			<%} %>

			</div>

			<c:if test = "${ endYError0 == true || endMError0 == true }"  >
				<font color="red">
					<p>以前の業務を選択した場合は終了日を選択してください。</p>
				</font>
			</c:if>

			<c:if test = "${ endYError1 == true || endMError1 == true }"  >
				<font color="red">
					<p>現在の業務を選択した場合は終了日を選択しないでください。</p>
				</font>
			</c:if>

			<c:if test = "${ seError == true || seError == true }"  >
				<font color="red">
					<p>終了日は開始日以降を選択してください。</p>
				</font>
			</c:if>

			<div class="situation">

			<% String situationStr = String.valueOf(career.getSituation()); %>
			<% if(situationStr.equals("1")){%>
				<input type="radio" name="situation" value="1" checked>現在の業務
				<input type="radio" name="situation" value="0">以前の業務
			<% }else{%>
				<input type="radio" name="situation" value="1">現在の業務
				<input type="radio" name="situation" value="0" checked>以前の業務
			<% } %>
				<br/>

			</div>

			<div class="career-name">
				<label>業務名と業務内容（100文字以内）</label>
				<br/>
				<input type="text" name="businessName" class="career-name-input" maxlength='100' placeholder='○○プロジェクトの××業務を担当' value="${career.businessName}" required>
			</div>

            <input type="button" class="button"value="キャンセル" onclick="location.href='/SelfIntroduction/EmployeeDetail?employeeNumber=${ career.employeeNumber }'">
            <input type="button" class="button"value="削除" onclick="location.href='/SelfIntroduction/CareerDelete?businessNumber=${ career.businessNumber }&employeeNumber=${career.employeeNumber }'">
            <input type="submit" class="button update-button" value="更新">

            <input type="hidden" name="employeeNumber" value="${ career.getEmployeeNumber()}">

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
				if(window.confirm('業務経歴を削除してよろしいでしょうか？')){
					// 「OK」時の処理
					location.href = '/SelfIntroduction/CareerDelete?businessNumber=${ career.businessNumber },employeeNumber=${career.employeeNumber }'; // 削除処理実行
				}
				// キャンセル時は何もしない
			}
		</script>
		<script src="./js/jquery-3.3.1.min.js"></script>
		<script src="./js/bootstrap.bundle.min.js"></script>

	</body>

</html>