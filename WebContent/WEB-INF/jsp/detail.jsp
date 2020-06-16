<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>従業員詳細情報</title>
	<link href="./css/common.css" rel="stylesheet">
	<link href="./css/list.css" rel="stylesheet">
	<link href="./css/lib/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container-fluid">

      <header>
        <div class="navbar navbar-light bg-light shadow-sm">
            <a href="#" class="navbar-brand">
              <strong>${emp.employeeName}さん</strong>
            </a>
        </div>
      </header>
	<input type="button" class="button" value="一覧に戻る"  onclick="location.href='/SelfIntroduction/EmployeeList'"><br><br><br>
	<label>基本情報</label>
	<input type="button" class="button" value="編集"  onclick="location.href='/SelfIntroduction/EmployeeUpdate?employeeNumber=${ emp.employeeNumber }'"><br><br>
	<label class="detail-number">従業員番号 : ${emp.employeeNumber }</label><br>
	<label class="detail-name">氏名: ${emp.employeeName }</label><br>
	<label>所属 : ${emp.employeeDeployment }</label><br>
	<label>現在の業務 : 業務1</label><br>
	<label class="detail-prof">プロフィール : ${emp.employeeProfile }</label>


	<div class="skillList">
		<br><br><label>資格/スキル一覧</label>
		<input type="button" class="button" value="新規"><br>
		<br><table class="table table-bordered table-striped" >
			<thead  class="thead-light">
			<tr>
				<th>ジャンル</th>
				<th>資格/スキル名</th>
				<th>取得日</th>
				<th></th>
			<thead>
		<tbody>
		<c:forEach var="mc" items="${masterCertificationList}">
		<tr>
			<td>${ mc.certificationGenreName }</td>
			<td>${ mc.certificationName }</td>
			<td>${ mc.certificationDate }</td>
			<td><input type="button" class="button" value="編集"  onclick="location.href='/SelfIntroduction/CertificationUpdate?owned_certification_id=${ mc.ownedCertificationId }'"></td>
		</tr>



		</c:forEach>
		<c:forEach var="otherCertification" items="${otherCertificationList}">



		</c:forEach>
		<c:forEach var="skill" items="${skillList}">



		</c:forEach>

		</tbody>
		</table>

	</div>

	<div class="career">
		<br><br><label>業務経歴</label>
		<input type="button" class="button" value="新規"  onclick="location.href='/SelfIntroduction/'CareerRegister"><br>
		<br><table class="table table-bordered table-striped" >
		<thead class="thead-light">
			<tr>
				<th>業務名</th>
				<th>期間</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="career" items="${careerList}">
			<tr>
				<td>${career.businessName }</td>
				<td>${career.businessStartDate }　～　${career.businessEndDate }</td>
				<td><input type="button" class="button" value="編集"  onclick="location.href='/SelfIntroduction/'CareerUpdate"></td>
		</c:forEach>
		</tbody>
		</table>
	</div>
	<br><br>
	<input type="button" class="button" value="従業員の削除">


</body>
</html>