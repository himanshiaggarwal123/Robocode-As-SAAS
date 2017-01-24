<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="includes/dropdown.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ranking</title>

<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="css/freelancer.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<!--  <link
	href='https://fonts.googleapis.com/css?family=Righteous|Fredoka+One'
	rel='stylesheet' type='text/css'>-->

<link
	href="http://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic"
	rel="stylesheet" type="text/css">
    <style type="text/css">
    	table, th, td {
    border: 2px solid white;
    border-collapse: collapse;
}
th, td {
    padding: 15px;
}
    </style>
</head>
<body>
<%@include file="includes/header.jsp" %>
<body>
<header>
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<div class="intro-text">
					<span class="skills">Robots Ranking</span>
	<br/>
		<%
    try {
	    String driverClassName = "com.mysql.jdbc.Driver";
    	String connectionUrl = "jdbc:mysql://104.154.142.10/robocode";
    	String dbUser = "himanshi";
    	String dbPwd = "aggarwal";
    	Class.forName(driverClassName).newInstance();;
    	Connection connection = DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
    	String sql="Select userId, StorageDirPath, robotId, Games_played, Games_win, totalScores from robot order by totalScores DESC;";
    	Statement st=connection.createStatement();
    	ResultSet rs=st.executeQuery(sql);
    	%>
    	<table width=100% align="center">
    		<tr align="left">
    		<th>Rank</th>
    		<th>UserName</th>
    		<th>PackageName</th>
    		<th>RobotName</th>
    		<th>Games Played</th>
    		<th>Games won</th>
    		<th>Total Scores</th>
    		</tr>
    	
    	<%int i=0;
    		while(rs.next()){
    			i++;
    			%>
    			<tr align="left">
    			 <td><%=i %></td>
    			<td><%=rs.getString("userId") %></td>
    			 <td><%=rs.getString("StorageDirPath") %></td>
    			 <td><%=rs.getString("robotId") %></td>
    			 <td><%=rs.getInt("Games_played") %></td>
    			 <td><%=rs.getInt("Games_win") %></td>
    			 <td><%=rs.getDouble("totalScores") %></td>    			 
    			</tr>
    			
    			<%
    		}
    }
    catch(Exception e){
    	e.printStackTrace();
    }
    %>
    	</table>
				</div>
			</div>
		</div>
	</div>
	</header>
    

</body>
</html>