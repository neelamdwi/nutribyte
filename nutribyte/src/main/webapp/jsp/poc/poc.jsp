<%@page language="java" contentType="text/html"%>
<%@page import="java.sql.*, 
			java.util.List,
			com.fitness.nutribyte.datalayer.USDADao"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>NuriByte POC</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="/jsp/header.jsp"></jsp:include>
	<div style="text-align: center; vertical-align: middle;">
	<h2> There are <%= USDADao.productMap.size() %> products in the data base</h2>
	<h2> There are <%= USDADao.nutrientMap.size() %> nutrients in the data base</h2>
	</div>
</body>
</html>