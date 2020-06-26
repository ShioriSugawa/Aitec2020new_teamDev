<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ page import ="java.util.List" %>
<%@ page import="model.Certification"%>
<%@ page import="model.Skill"%>

<% List<Certification> cGenL = (List<Certification>)request.getAttribute("cGenL");%>
<% List<Certification> cNameL = (List<Certification>)request.getAttribute("cNameL");%>
<% List<Skill> sGenL = (List<Skill>)request.getAttribute("sGenL");%>
<% String regi=(String)request.getAttribute("regi");%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>資格・スキル登録</title>
		<link href="./css/common.css" rel="stylesheet">
        <link href="./css/register.css" rel="stylesheet">
        <link href="./css/lib/bootstrap.min.css" rel="stylesheet">
	</head>

	<body>
		<header>
			<div class="navbar navbar-light bg-light shadow-sm">
				<a href="#" class="navbar-brand">
				<strong>資格・スキル登録</strong>
				</a>
			</div>
		</header>

		<form class="register-form" action="/SelfIntroduction/SkillsRegister" method="post" onSubmit="return confirmRegister()">
			<input type="hidden" name="employeeNumber" value="${eNum}">

			<input type="radio" id="raM" name="regiSelect" value="mst"
			<c:if test="${regi == 'c'}"> checked="checked"</c:if>>
			<label for="raM">資格（一覧から選択）</label><br>

				<select name="mstCode" id="raMc" disabled >
				<option disabled selected>登録する資格を選択してください</option>
					<c:forEach var="slCerti" items="${cNameL}">
						<option value="${slCerti.getCertiCode() }"><c:out value="${slCerti.getCertiName()}" /></option>
					</c:forEach>
				</select><br>

				<label>認定日：</label>
					<select name="mstYear" id="raMy" disabled >
					<option disabled selected>年</option>
					<c:forEach begin="1950" end="${nowYear}" step="1" var="i">
						<option value="${i}"><c:out value="${i}年" /></option>
					</c:forEach>
				</select>

				<select name="mstMonth" id="raMm" disabled >
					<option disabled selected>月</option>
					<c:forEach begin="1" end="9" step="1" var="i">
						<option value="0${i}"><c:out value="${i}月" /></option>
					</c:forEach>
					<c:forEach begin="10" end="12" step="1" var="i">
						<option value="${i}"><c:out value="${i}月" /></option>
					</c:forEach>
				</select><br>
			<br>


			<input type="radio" id="raO" name="regiSelect" value="oth">
			<label for="raO">資格（一覧にない資格の登録）</label><br>

				<select name="othGenre" id="raOg" disabled >
				<option disabled selected>ジャンルを選択してください</option>
					<c:forEach var="slGenre" items="${cGenL}">
						<option value="${slGenre.getCertiCode() }"><c:out value="${slGenre.getCertiName()}" /></option>
					</c:forEach>
				</select><br>

				<label for="raOn">資格名（100文字以内）</label><br>
					<input disabled type="text" name="othName" id="raOn" maxlength='100' placeholder='資格名を入力してください'>
					<br>

				<label>認定日：</label>
					<select name="othYear" id="raOy" disabled >
					<option disabled selected>年</option>
					<c:forEach begin="1950" end="${nowYear}" step="1" var="i">
						<option value="${i}"><c:out value="${i}年" /></option>
					</c:forEach>
				</select>

				<select name="othMonth" id="raOm" disabled >
					<option disabled selected>月</option>
					<c:forEach begin="1" end="9" step="1" var="i">
						<option value="0${i}"><c:out value="${i}月" /></option>
					</c:forEach>
					<c:forEach begin="10" end="12" step="1" var="i">
						<option value="${i}"><c:out value="${i}月" /></option>
					</c:forEach>
				</select><br>
			<br>


			<input type="radio" id="raS" name="regiSelect" value="skl"
			<c:if test="${regi=='s'}"> checked="checked"</c:if>>
			<label for="raS">スキル（ジャンルを選択して内容を記入）</label><br>

				<select name="sklGenre" id="raSg" disabled >
				<option disabled selected>ジャンルを選択してください</option>
					<c:forEach var="slGenre" items="${sGenL}">
						<option value="${slGenre.getGenreCode() }"><c:out value="${slGenre.getGenreName()}" /></option>
					</c:forEach>
				</select><br>

				<label for="raSn">スキルの内容（100文字以内）</label><br>
					<textarea disabled name="sklName" id="raSn" maxlength='100' placeholder="スキルの内容を具体的に記入してください"></textarea>
					<br>

				<br>


			<input type="button" class="button" value="キャンセル"  onclick="location.href='/SelfIntroduction/EmployeeDetail?employeeNumber=${ eNum }'">
			<input type="submit" class="button career-button" value="登録">

		</form>


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

		<script>
		var VraM = document.getElementById("raM");
		var VraO = document.getElementById("raO");
		var VraS = document.getElementById("raS");

		window.addEventListener('load',activM,false);
		window.addEventListener('load',activS,false);

		VraM.addEventListener('change', activM);
		VraO.addEventListener('change', activO);
		VraS.addEventListener('change', activS);

		var VraMc = document.getElementById("raMc");
		var VraMy = document.getElementById("raMy");
		var VraMm = document.getElementById("raMm");

		var VraOg = document.getElementById("raOg");
		var VraOy = document.getElementById("raOy");
		var VraOm = document.getElementById("raOm");
		var VraOn = document.getElementById("raOn");

		var VraSg = document.getElementById("raSg");
		var VraSn = document.getElementById("raSn");

		function activM(){
			if(VraM.checked == true){
				VraMc.disabled = false;
				VraMy.disabled = false;
				VraMm.disabled = false;
				VraOg.disabled = true;
				VraOy.disabled = true;
				VraOm.disabled = true;
				VraOn.disabled = true;
				VraSg.disabled = true;
				VraSn.disabled = true;


			}
		}

		function activO(){
			if(VraO.checked == true){
				VraOg.disabled = false;
				VraOy.disabled = false;
				VraOm.disabled = false;
				VraOn.disabled = false;
				VraMc.disabled = true;
				VraMy.disabled = true;
				VraMm.disabled = true;
				VraSg.disabled = true;
				VraSn.disabled = true;


			}
		}

		function activS(){
			if(VraS.checked == true){
				VraSg.disabled = false;
				VraSn.disabled = false;
				VraMc.disabled = true;
				VraMy.disabled = true;
				VraMm.disabled = true;
				VraOg.disabled = true;
				VraOy.disabled = true;
				VraOm.disabled = true;
				VraOn.disabled = true;


			}
		}

		</script>

	</body>

</html>