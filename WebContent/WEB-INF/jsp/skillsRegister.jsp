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
<%! String emptyMessage=null;%>
<% emptyMessage=(String)request.getAttribute("emptyMessage");%>
<% List<Integer> yearL=(List<Integer>)request.getAttribute("yearL");%>
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

		<form name="mosDate" class="register-form" action="/SelfIntroduction/SkillsRegister" method="post" >
			<input type="hidden" name="employeeNumber" value="${eNum}">

			<input type="radio" id="raM" name="regiSelect" value="mst"
			<c:if test="${regi == 'c'}"> checked="checked"</c:if>>
			<label for="raM">資格（一覧から選択）</label><br>
		<c:if test="${emptyMessage!=null && regi.equals('c')}">
		<font color="red"><c:out value="一覧の資格の登録には${emptyMessage}の入力が必須です" /></font><br></c:if>

				<select name="mstCode" id="raMc" disabled >
				<option disabled <c:if test="${ emptyMessage == null }" >selected</c:if> value="empty">登録する資格を選択してください</option>
					<c:forEach var="slCerti" items="${cNameL}">
						<option value="${slCerti.getCertiCode() }"
						<c:if test="${ sldMC == slCerti.getCertiCode()}">selected</c:if>><c:out value="${slCerti.getCertiName()}" /></option>
					</c:forEach>
				</select><br>

				<label>取得日：</label>
					<select name="mstYear" id="raMy" disabled >
					<option disabled <c:if test="${ emptyMessage == null }">selected</c:if> value="empty">年</option>

					<c:forEach var="MyL" items="${yearL}">
						<option value="${MyL}"
						<c:if test="${ sldMY == MyL}">selected</c:if>><c:out value="${MyL}年" /></option>
					</c:forEach>
					<option value="70以前">1970年～</option>
					</select>

					<select name="mstMonth" id="raMm" disabled >
						<option disabled <c:if test="${ emptyMessage == null }">selected</c:if> value="empty">月</option>
						<c:forEach begin="1" end="9" step="1" var="i">
							<option value="0${i}" <c:if test="${regi==null }">selected</c:if>><c:out value="${i}月" /></option>
						</c:forEach>
						<c:forEach begin="10" end="12" step="1" var="i">
							<option value="${i}" <c:if test="${ regi==null}">selected</c:if>><c:out value="${i}月" /></option>
						</c:forEach>
					</select><br>
				<br>


			<input type="radio" id="raO" name="regiSelect" value="oth"
			<c:if test="${regi == 'o'}"> checked="checked"</c:if>>
			<label for="raO">資格（一覧にない資格の登録）</label><br>
		<c:if test="${emptyMessage!=null && regi.equals('o')}">
		<font color="red"><c:out value="手入力資格の登録には${emptyMessage}の入力が必須です" /></font><br></c:if>

				<select name="othGenre" id="raOg" disabled >
				<option disabled <c:if test="${ emptyMessage == null }">selected</c:if> value="empty">ジャンルを選択してください</option>
					<c:forEach var="slGenre" items="${cGenL}">
						<option value="${slGenre.getCertiCode() }" <c:if test="${regi==null }">selected</c:if>><c:out value="${slGenre.getCertiName()}" /></option>
					</c:forEach>
				</select><br>

				<label for="raOn">資格名（100文字以内）</label><br>
					<input disabled required type="text" name="othName" id="raOn" maxlength='100' placeholder='資格名を入力してください'>
					<br>

				<label>取得日：</label>
					<select name="mstYear" id="raMy" disabled >
					<option disabled <c:if test="${ emptyMessage == null }">selected</c:if> value="empty">年</option>

					<c:forEach var="MyL" items="${yearL}">
						<option value="${MyL}"
						<c:if test="${ sldMY == MyL}">selected</c:if>><c:out value="${MyL}年" /></option>
					</c:forEach>
					<option value="70以前">1970年～</option>
					</select>

				<select name="othMonth" id="raOm" disabled >
					<option disabled <c:if test="${ emptyMessage == null }">selected</c:if>>月</option>
					<c:forEach begin="1" end="9" step="1" var="i">
						<option value="0${i}" <c:if test="${ regi==null}">selected</c:if>><c:out value="${i}月" /></option>
					</c:forEach>
					<c:forEach begin="10" end="12" step="1" var="i">
						<option value="${i}" <c:if test="${ regi==null}">selected</c:if>><c:out value="${i}月" /></option>
					</c:forEach>
				</select><br>
			<br>


			<input type="radio" id="raS" name="regiSelect" value="skl"
			<c:if test="${regi=='s'}"> checked="checked"</c:if>>
			<label for="raS">スキル（ジャンルを選択して内容を記入）</label><br>
		<c:if test="${emptyMessage!=null && regi.equals('s')}">
		<font color="red"><c:out value="スキルの登録には${emptyMessage}の入力が必須です" /></font><br></c:if>

				<select name="sklGenre" id="raSg" disabled >
				<option disabled value="empty" <c:if test="${ emptyMessage == null }">selected</c:if>>ジャンルを選択してください</option>
					<c:forEach var="slGenre" items="${sGenL}">
						<option value="${slGenre.getGenreCode() }" <c:if test="${regi==null }">selected</c:if>><c:out value="${slGenre.getGenreName()}" /></option>
					</c:forEach>
				</select><br>

				<label for="raSn">スキルの内容（100文字以内）</label><br>
					<textarea disabled required name="sklName" id="raSn" maxlength='100' placeholder="スキルの内容を具体的に記入してください"></textarea>
					<br>

				<br>


			<input type="button" class="button" value="キャンセル"  onclick="location.href='/SelfIntroduction/EmployeeDetail?employeeNumber=${ eNum }'">
			<input type="button" id="sklsRegi" class="button career-button" value="登録">

		</form>


		<script type="text/javascript">
			function confirmRegister(){
				// 確認ダイアログの表示
				if(window.confirm('登録してよろしいでしょうか？')){
					// 「OK」時の処理
					document.mosDate.submit();
					//return true; // 更新処理実行（post送信）
				}
				return false; // キャンセル時は何もしない
			}
		</script>
		<script src="./js/jquery-3.3.1.min.js"></script>
		<script src="./js/bootstrap.bundle.min.js"></script>

		<script>
		var sklsRegi = document.getElementById("sklsRegi");
		sklsRegi.addEventListener('click',confirmRegister,false)

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

				VraOn.required = false;
				VraSn.required = false;
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

				VraOn.required = true;
				VraSn.required = false;
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

				VraSn.required = true;
				VraOn.required = false;
			}
		}

		</script>

	</body>

</html>