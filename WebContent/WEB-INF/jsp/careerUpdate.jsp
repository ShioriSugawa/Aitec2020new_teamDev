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
				<% String start = career.getBusinessStart(); %>
				<% String startYear = start.substring(0,4); %>
				<% String startMonth = start.substring(5); %>

				<label>業務開始</label>
				<select name="startYear" required>
					<option value="">-</option>
					<option <%if(startYear =="1950"){ %>selected<%}%>>1950</option>
					<option <%if(startYear =="1951"){ %>selected<%}%>>1951</option>
					<option <%if(startYear =="1952"){ %>selected<%}%>>1952</option>
					<option <%if(startYear =="1953"){ %>selected<%}%>>1953</option>
					<option <%if(startYear =="1954"){ %>selected<%}%>>1954</option>
					<option <%if(startYear =="1955"){ %>selected<%}%>>1955</option>
					<option <%if(startYear =="1956"){ %>selected<%}%>>1956</option>
					<option <%if(startYear =="1957"){ %>selected<%}%>>1957</option>
					<option <%if(startYear =="1958"){ %>selected<%}%>>1958</option>
					<option <%if(startYear =="1959"){ %>selected<%}%>>1959</option>
					<option <%if(startYear =="1960"){ %>selected<%}%>>1950</option>
					<option <%if(startYear =="1961"){ %>selected<%}%>>1961</option>
					<option <%if(startYear =="1962"){ %>selected<%}%>>1962</option>
					<option <%if(startYear =="1963"){ %>selected<%}%>>1963</option>
					<option <%if(startYear =="1964"){ %>selected<%}%>>1964</option>
					<option <%if(startYear =="1965"){ %>selected<%}%>>1965</option>
					<option <%if(startYear =="1966"){ %>selected<%}%>>1966</option>
					<option <%if(startYear =="1967"){ %>selected<%}%>>1967</option>
					<option <%if(startYear =="1968"){ %>selected<%}%>>1968</option>
					<option <%if(startYear =="1969"){ %>selected<%}%>>1969</option>
					<option <%if(startYear =="1970"){ %>selected<%}%>>1970</option>
					<option <%if(startYear =="1971"){ %>selected<%}%>>1971</option>
					<option <%if(startYear =="1972"){ %>selected<%}%>>1972</option>
					<option <%if(startYear =="1973"){ %>selected<%}%>>1973</option>
					<option <%if(startYear =="1974"){ %>selected<%}%>>1974</option>
					<option <%if(startYear =="1975"){ %>selected<%}%>>1975</option>
					<option <%if(startYear =="1976"){ %>selected<%}%>>1976</option>
					<option <%if(startYear =="1977"){ %>selected<%}%>>1977</option>
					<option <%if(startYear =="1978"){ %>selected<%}%>>1978</option>
					<option <%if(startYear =="1979"){ %>selected<%}%>>1979</option>
					<option <%if(startYear =="1980"){ %>selected<%}%>>1980</option>
					<option <%if(startYear =="1981"){ %>selected<%}%>>1981</option>
					<option <%if(startYear =="1982"){ %>selected<%}%>>1982</option>
					<option <%if(startYear =="1983"){ %>selected<%}%>>1983</option>
					<option <%if(startYear =="1984"){ %>selected<%}%>>1984</option>
					<option <%if(startYear =="1985"){ %>selected<%}%>>1985</option>
					<option <%if(startYear =="1986"){ %>selected<%}%>>1986</option>
					<option <%if(startYear =="1987"){ %>selected<%}%>>1987</option>
					<option <%if(startYear =="1988"){ %>selected<%}%>>1988</option>
					<option <%if(startYear =="1989"){ %>selected<%}%>>1989</option>
					<option <%if(startYear =="1990"){ %>selected<%}%>>1990</option>
					<option <%if(startYear =="1991"){ %>selected<%}%>>1991</option>
					<option <%if(startYear =="1992"){ %>selected<%}%>>1992</option>
					<option <%if(startYear =="1993"){ %>selected<%}%>>1993</option>
					<option <%if(startYear =="1994"){ %>selected<%}%>>1994</option>
					<option <%if(startYear =="1995"){ %>selected<%}%>>1995</option>
					<option <%if(startYear =="1996"){ %>selected<%}%>>1996</option>
					<option <%if(startYear =="1997"){ %>selected<%}%>>1997</option>
					<option <%if(startYear =="1998"){ %>selected<%}%>>1998</option>
					<option <%if(startYear =="1999"){ %>selected<%}%>>1999</option>
					<option <%if(startYear =="2000"){ %>selected<%}%>>2000</option>
					<option <%if(startYear =="2001"){ %>selected<%}%>>2001</option>
					<option <%if(startYear =="2002"){ %>selected<%}%>>2002</option>
					<option <%if(startYear =="2003"){ %>selected<%}%>>2003</option>
					<option <%if(startYear =="2004"){ %>selected<%}%>>2004</option>
					<option <%if(startYear =="2005"){ %>selected<%}%>>2005</option>
					<option <%if(startYear =="2006"){ %>selected<%}%>>2006</option>
					<option <%if(startYear =="2007"){ %>selected<%}%>>2007</option>
					<option <%if(startYear =="2008"){ %>selected<%}%>>2008</option>
					<option <%if(startYear =="2009"){ %>selected<%}%>>2009</option>
					<option <%if(startYear =="2000"){ %>selected<%}%>>2000</option>
					<option <%if(startYear =="2011"){ %>selected<%}%>>2011</option>
					<option <%if(startYear =="2012"){ %>selected<%}%>>2012</option>
					<option <%if(startYear =="2013"){ %>selected<%}%>>2013</option>
					<option <%if(startYear =="2014"){ %>selected<%}%>>2014</option>
					<option <%if(startYear =="2015"){ %>selected<%}%>>2015</option>
					<option <%if(startYear =="2016"){ %>selected<%}%>>2016</option>
					<option <%if(startYear =="2017"){ %>selected<%}%>>2017</option>
					<option <%if(startYear =="2018"){ %>selected<%}%>>2018</option>
					<option <%if(startYear =="2019"){ %>selected<%}%>>2019</option>
					<option <%if(startYear =="2020"){ %>selected<%}%>>2020</option>
					<option <%if(startYear =="2021"){ %>selected<%}%>>2021</option>
					<option <%if(startYear =="2022"){ %>selected<%}%>>2022</option>
					<option <%if(startYear =="2023"){ %>selected<%}%>>2023</option>
					<option <%if(startYear =="2024"){ %>selected<%}%>>2024</option>
					<option <%if(startYear =="2025"){ %>selected<%}%>>2025</option>
					<option <%if(startYear =="2026"){ %>selected<%}%>>2026</option>
					<option <%if(startYear =="2027"){ %>selected<%}%>>2027</option>
					<option <%if(startYear =="2028"){ %>selected<%}%>>2028</option>
					<option <%if(startYear =="2029"){ %>selected<%}%>>2029</option>
					<option <%if(startYear =="2030"){ %>selected<%}%>>2030</option>
				</select>

				<select name="startMonth" required>
					<option value="">-</option>
					<option <%if(startMonth =="01"){ %>selected<%}%>>01</option>
					<option <%if(startMonth =="02"){ %>selected<%}%>>02</option>
					<option <%if(startMonth =="03"){ %>selected<%}%>>03</option>
					<option <%if(startMonth =="04"){ %>selected<%}%>>04</option>
					<option <%if(startMonth =="05"){ %>selected<%}%>>05</option>
					<option <%if(startMonth =="06"){ %>selected<%}%>>06</option>
					<option <%if(startMonth =="07"){ %>selected<%}%>>07</option>
					<option <%if(startMonth =="08"){ %>selected<%}%>>08</option>
					<option <%if(startMonth =="09"){ %>selected<%}%>>09</option>
					<option <%if(startMonth =="10"){ %>selected<%}%>>10</option>
					<option <%if(startMonth =="11"){ %>selected<%}%>>11</option>
					<option <%if(startMonth =="12"){ %>selected<%}%>>12</option>
				</select>

			</div>

			<div class="career-end">

			<% if(career.getBusinessEnd() ==null){ %>
				<label>業務終了</label>
				<select name="endYear" required>
					<option value="">-</option>
					<option value="1950">1950</option>
					<option value="1951">1951</option>
					<option value="1952">1952</option>
					<option value="1953">1953</option>
					<option value="1954">1954</option>
					<option value="1955">1955</option>
					<option value="1956">1956</option>
					<option value="1957">1957</option>
					<option value="1958">1958</option>
					<option value="1959">1959</option>
					<option value="1960">1960</option>
					<option value="1961">1961</option>
					<option value="1962">1962</option>
					<option value="1963">1963</option>
					<option value="1964">1964</option>
					<option value="1965">1965</option>
					<option value="1966">1966</option>
					<option value="1967">1967</option>
					<option value="1968">1968</option>
					<option value="1969">1969</option>
					<option value="1970">1970</option>
					<option value="1971">1971</option>
					<option value="1972">1972</option>
					<option value="1973">1973</option>
					<option value="1974">1974</option>
					<option value="1975">1975</option>
					<option value="1976">1976</option>
					<option value="1977">1977</option>
					<option value="1978">1978</option>
					<option value="1979">1979</option>
					<option value="1980">1980</option>
					<option value="1981">1981</option>
					<option value="1982">1982</option>
					<option value="1983">1983</option>
					<option value="1984">1984</option>
					<option value="1985">1985</option>
					<option value="1986">1986</option>
					<option value="1987">1987</option>
					<option value="1988">1988</option>
					<option value="1989">1989</option>
					<option value="1990">1990</option>
					<option value="1991">1991</option>
					<option value="1992">1992</option>
					<option value="1993">1993</option>
					<option value="1994">1994</option>
					<option value="1995">1995</option>
					<option value="1996">1996</option>
					<option value="1997">1997</option>
					<option value="1998">1998</option>
					<option value="1999">1999</option>
					<option value="2000">2000</option>
					<option value="2001">2001</option>
					<option value="2002">2002</option>
					<option value="2003">2003</option>
					<option value="2004">2004</option>
					<option value="2005">2005</option>
					<option value="2006">2006</option>
					<option value="2007">2007</option>
					<option value="2008">2008</option>
					<option value="2009">2009</option>
					<option value="2010">2010</option>
					<option value="2011">2011</option>
					<option value="2012">2012</option>
					<option value="2013">2013</option>
					<option value="2014">2014</option>
					<option value="2015">2015</option>
					<option value="2016">2016</option>
					<option value="2017">2017</option>
					<option value="2018">2018</option>
					<option value="2019">2019</option>
					<option value="2020">2020</option>
					<option value="2021">2021</option>
					<option value="2022">2022</option>
					<option value="2023">2023</option>
					<option value="2024">2024</option>
					<option value="2025">2025</option>
					<option value="2026">2026</option>
					<option value="2027">2027</option>
					<option value="2028">2028</option>
					<option value="2029">2029</option>
					<option value="2030">2030</option>
				</select>
				<label>年</label>

				<select name="endMonth" required>
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
				<% String end = career.getBusinessEnd(); %>
				<% String endYear = end.substring(0,3); %>
				<% String endMonth = end.substring(5); %>

				<label>業務終了</label>
				<select name="endYear" required>
					<option value="">-</option>
					<option <%if(endYear =="1950"){ %>selected<%}%>>1950</option>
					<option <%if(endYear =="1951"){ %>selected<%}%>>1951</option>
					<option <%if(endYear =="1952"){ %>selected<%}%>>1952</option>
					<option <%if(endYear =="1953"){ %>selected<%}%>>1953</option>
					<option <%if(endYear =="1954"){ %>selected<%}%>>1954</option>
					<option <%if(endYear =="1955"){ %>selected<%}%>>1955</option>
					<option <%if(endYear =="1956"){ %>selected<%}%>>1956</option>
					<option <%if(endYear =="1957"){ %>selected<%}%>>1957</option>
					<option <%if(endYear =="1958"){ %>selected<%}%>>1958</option>
					<option <%if(endYear =="1959"){ %>selected<%}%>>1959</option>
					<option <%if(endYear =="1960"){ %>selected<%}%>>1950</option>
					<option <%if(endYear =="1961"){ %>selected<%}%>>1961</option>
					<option <%if(endYear =="1962"){ %>selected<%}%>>1962</option>
					<option <%if(endYear =="1963"){ %>selected<%}%>>1963</option>
					<option <%if(endYear =="1964"){ %>selected<%}%>>1964</option>
					<option <%if(endYear =="1965"){ %>selected<%}%>>1965</option>
					<option <%if(endYear =="1966"){ %>selected<%}%>>1966</option>
					<option <%if(endYear =="1967"){ %>selected<%}%>>1967</option>
					<option <%if(endYear =="1968"){ %>selected<%}%>>1968</option>
					<option <%if(endYear =="1969"){ %>selected<%}%>>1969</option>
					<option <%if(endYear =="1970"){ %>selected<%}%>>1970</option>
					<option <%if(endYear =="1971"){ %>selected<%}%>>1971</option>
					<option <%if(endYear =="1972"){ %>selected<%}%>>1972</option>
					<option <%if(endYear =="1973"){ %>selected<%}%>>1973</option>
					<option <%if(endYear =="1974"){ %>selected<%}%>>1974</option>
					<option <%if(endYear =="1975"){ %>selected<%}%>>1975</option>
					<option <%if(endYear =="1976"){ %>selected<%}%>>1976</option>
					<option <%if(endYear =="1977"){ %>selected<%}%>>1977</option>
					<option <%if(endYear =="1978"){ %>selected<%}%>>1978</option>
					<option <%if(endYear =="1979"){ %>selected<%}%>>1979</option>
					<option <%if(endYear =="1980"){ %>selected<%}%>>1980</option>
					<option <%if(endYear =="1981"){ %>selected<%}%>>1981</option>
					<option <%if(endYear =="1982"){ %>selected<%}%>>1982</option>
					<option <%if(endYear =="1983"){ %>selected<%}%>>1983</option>
					<option <%if(endYear =="1984"){ %>selected<%}%>>1984</option>
					<option <%if(endYear =="1985"){ %>selected<%}%>>1985</option>
					<option <%if(endYear =="1986"){ %>selected<%}%>>1986</option>
					<option <%if(endYear =="1987"){ %>selected<%}%>>1987</option>
					<option <%if(endYear =="1988"){ %>selected<%}%>>1988</option>
					<option <%if(endYear =="1989"){ %>selected<%}%>>1989</option>
					<option <%if(endYear =="1990"){ %>selected<%}%>>1990</option>
					<option <%if(endYear =="1991"){ %>selected<%}%>>1991</option>
					<option <%if(endYear =="1992"){ %>selected<%}%>>1992</option>
					<option <%if(endYear =="1993"){ %>selected<%}%>>1993</option>
					<option <%if(endYear =="1994"){ %>selected<%}%>>1994</option>
					<option <%if(endYear =="1995"){ %>selected<%}%>>1995</option>
					<option <%if(endYear =="1996"){ %>selected<%}%>>1996</option>
					<option <%if(endYear =="1997"){ %>selected<%}%>>1997</option>
					<option <%if(endYear =="1998"){ %>selected<%}%>>1998</option>
					<option <%if(endYear =="1999"){ %>selected<%}%>>1999</option>
					<option <%if(endYear =="2000"){ %>selected<%}%>>2000</option>
					<option <%if(endYear =="2001"){ %>selected<%}%>>2001</option>
					<option <%if(endYear =="2002"){ %>selected<%}%>>2002</option>
					<option <%if(endYear =="2003"){ %>selected<%}%>>2003</option>
					<option <%if(endYear =="2004"){ %>selected<%}%>>2004</option>
					<option <%if(endYear =="2005"){ %>selected<%}%>>2005</option>
					<option <%if(endYear =="2006"){ %>selected<%}%>>2006</option>
					<option <%if(endYear =="2007"){ %>selected<%}%>>2007</option>
					<option <%if(endYear =="2008"){ %>selected<%}%>>2008</option>
					<option <%if(endYear =="2009"){ %>selected<%}%>>2009</option>
					<option <%if(endYear =="2000"){ %>selected<%}%>>2000</option>
					<option <%if(endYear =="2011"){ %>selected<%}%>>2011</option>
					<option <%if(endYear =="2012"){ %>selected<%}%>>2012</option>
					<option <%if(endYear =="2013"){ %>selected<%}%>>2013</option>
					<option <%if(endYear =="2014"){ %>selected<%}%>>2014</option>
					<option <%if(endYear =="2015"){ %>selected<%}%>>2015</option>
					<option <%if(endYear =="2016"){ %>selected<%}%>>2016</option>
					<option <%if(endYear =="2017"){ %>selected<%}%>>2017</option>
					<option <%if(endYear =="2018"){ %>selected<%}%>>2018</option>
					<option <%if(endYear =="2019"){ %>selected<%}%>>2019</option>
					<option <%if(endYear =="2020"){ %>selected<%}%>>2020</option>
					<option <%if(endYear =="2021"){ %>selected<%}%>>2021</option>
					<option <%if(endYear =="2022"){ %>selected<%}%>>2022</option>
					<option <%if(endYear =="2023"){ %>selected<%}%>>2023</option>
					<option <%if(endYear =="2024"){ %>selected<%}%>>2024</option>
					<option <%if(endYear =="2025"){ %>selected<%}%>>2025</option>
					<option <%if(endYear =="2026"){ %>selected<%}%>>2026</option>
					<option <%if(endYear =="2027"){ %>selected<%}%>>2027</option>
					<option <%if(endYear =="2028"){ %>selected<%}%>>2028</option>
					<option <%if(endYear =="2029"){ %>selected<%}%>>2029</option>
					<option <%if(endYear =="2030"){ %>selected<%}%>>2030</option>
				</select>

				<select name="endMonth" required>
					<option value="">-</option>
					<option <%if(endMonth =="01"){ %>selected<%}%>>01</option>
					<option <%if(endMonth =="02"){ %>selected<%}%>>02</option>
					<option <%if(endMonth =="03"){ %>selected<%}%>>03</option>
					<option <%if(endMonth =="04"){ %>selected<%}%>>04</option>
					<option <%if(endMonth =="05"){ %>selected<%}%>>05</option>
					<option <%if(endMonth =="06"){ %>selected<%}%>>06</option>
					<option <%if(endMonth =="07"){ %>selected<%}%>>07</option>
					<option <%if(endMonth =="08"){ %>selected<%}%>>08</option>
					<option <%if(endMonth =="09"){ %>selected<%}%>>09</option>
					<option <%if(endMonth =="10"){ %>selected<%}%>>10</option>
					<option <%if(endMonth =="11"){ %>selected<%}%>>11</option>
					<option <%if(endMonth =="12"){ %>selected<%}%>>12</option>
				</select>
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

			<% if(career.getSituation() =="1"){%>
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
            <input type="button" class="button"value="削除" onclick="location.href='/SelfIntroduction/CareerDelete?businessNumber=${ career.businessNumber }'">
            <input type="submit" class="button update-button" value="更新">

			<input type="hidden" name="employeeNumber" value="employee.employeeNumber">

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
					location.href = '/SelfIntroduction/CareerDelete?businessNumber=${ career.businessNumber }'; // 削除処理実行
				}
				// キャンセル時は何もしない
			}
		</script>
		<script src="./js/jquery-3.3.1.min.js"></script>
		<script src="./js/bootstrap.bundle.min.js"></script>

	</body>

</html>