<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>従業員詳細情報</title>
	<link href="./css/common.css" rel="stylesheet">
	<link href="./css/detail.css" rel="stylesheet">
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
		<div class="basicInfo">
			<label>従業員番号 : ${emp.employeeNumber }</label><br>
			<label>氏名: ${emp.employeeName }</label><br>
			<label>所属 : ${emp.employeeDeployment }</label><br>
			<label>プロフィール : ${emp.employeeProfile }</label>
		</div>

	<div class="certificationList">
	<%--資格とスキルを別テーブルに表示 --%>
		<br><br><label>資格一覧</label>
		<input type="button" class="button" value="新規" onclick="location.href='/SelfIntroduction/SkillsRegister?employeeNumber=${emp.employeeNumber}&regi=c'"><br>
		<br><table class="table table-bordered table-striped" >
			<thead  class="thead-light">
			<tr>
				<th class="certiGenre">ジャンル</th>
				<th class="certiName">資格名</th>
				<th class="certiDate">取得日</th>
				<th></th>
			<thead>
		<tbody>
		<%-- <c:forEach var="all" items="${allCertificationList}">
		<tr>
			<td>${ all.genreName }</td>
			<td>${ all.certificationOrSkillName }</td>
			<td>${ all.certificationDate }</td>
			<td><input type="button" class="button" value="編集"  onclick="location.href='/SelfIntroduction/SkillsUpdate?owned_certification_id=${ all.ownedId }'"></td>
		</tr>
		</c:forEach>--%>
		<c:forEach var="mc" items="${masterCertificationList}">
		<tr>
			<td>${ mc.genreName }</td>
			<td>${ mc.certificationOrSkillName }</td>
			<td>${ mc.certificationDate }</td>
			<td><input type="button" class="button" value="編集"  onclick="location.href='/SelfIntroduction/SkillsUpdate?owned_certification_id=${ mc.ownedId }'"></td>
		</tr>



		</c:forEach>
		<c:forEach var="others" items="${othersList}">
		<tr>
			<td>${ others.genreName }</td>
			<td>${ others.certificationOrSkillName }</td>
			<td>${ others.certificationDate }</td>
			<td><input type="button" class="button" value="編集"  onclick="location.href='/SelfIntroduction/SkillsUpdate?owned_other_certification_id=${ others.ownedId }'"></td>
		</tr>
		</c:forEach>
		</tbody>
		</table>
	</div>


	<div class="skillList">
		<br><br><label>スキル一覧</label>
		<input type="button" class="button" value="新規" onclick="location.href='/SelfIntroduction/SkillsRegister?employeeNumber=${emp.employeeNumber}&regi=s'"><br>
		<br><table class="table table-bordered table-striped" >
			<thead  class="thead-light">
			<tr>
				<th class="skillGenre">ジャンル</th>
				<th class="skillName">スキル名</th>
				<th></th>
			<thead>
		<tbody>
		<c:forEach var="skill" items="${skillList}">
		<tr>
			<td>${ skill.genreName }</td>
			<td>${ skill.certificationOrSkillName }</td>
			<td><input type="button" class="button" value="編集"  onclick="location.href='/SelfIntroduction/SkillsUpdate?owned_skill_id=${ skill.ownedId }'"></td>
		</tr>
		</c:forEach>
		</tbody>
		</table>
	</div>

	<div class="careerList">
		<br><br><label>業務経歴</label>
		<input type="button" class="button" value="新規"  onclick="location.href='/SelfIntroduction/CareerRegister?employeeNumber=${ emp.employeeNumber }'"><br>
		<br><table class="table table-bordered table-striped" >
		<thead class="thead-light">
			<tr>
				<th class="careerName">業務名</th>
				<th class="careerTerm">期間</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="career" items="${careerList}">
			<tr>
				<td>${career.businessName }</td>
				<td>${career.businessStart }　～　${career.businessEnd }</td>
				<td><input type="button" class="button" value="編集"  onclick="location.href='/SelfIntroduction/CareerUpdate?businessNumber=${ career.businessNumber }'"></td>
		</c:forEach>
		</tbody>
		</table>
	</div>
	<br>
	<input type="button" class="deletebutton" value="従業員の削除" onclick="confirmDelete()">
	  <script type="text/javascript">
          function confirmDelete(){
              // 確認ダイアログの表示
              if(window.confirm('削除してよろしいでしょうか？')){
					// 「OK」時の処理
					location.href='/SelfIntroduction/EmployeeDelete?employeeNumber=${ emp.employeeNumber }'
					return true; // 更新処理実行（post送信）
              }
              	return false; // キャンセル時は何もしない
          	}
      </script>
      <script src="./js/jquery-3.3.1.min.js"></script>
      <script src="./js/bootstrap.bundle.min.js"></script>


</body>
</html>