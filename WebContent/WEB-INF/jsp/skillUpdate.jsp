<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="model.Skill"%>
<%@ page import="servlet.SkillsUpdate"%>
    
<% Skill skl=(Skill)request.getAttribute("ownedSkill");%>
    
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
		
		<p>スキル編集のページです</p>
		
		<label>ジャンル：</label>
		
	</body>
	
</html>