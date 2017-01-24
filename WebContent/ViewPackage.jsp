<%@page import="servlets.NewRobotServlet"%>
<%@page import="java.io.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.*, java.net.*"%>
<%@page import="java.util.*"%>
<%
	ResultSet resultset = null;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Package</title>
<!-- Bootstrap Core CSS - Uses Bootswatch Flatly Theme: http://bootswatch.com/flatly/ -->
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
<style>
        #RobotCode { 
                position: absolute;
                top: 200px;
                right: 0; 
                bottom: 0;
                left: 0;
            }
           
    </style>
    <style type="text/css">
			body{
			background-color:#18bc9c;
			}
			h1,h2{
			color: white;
			}
			.example {
				float: left;
				margin: 15px;
			}
			
			.demo {
				width: auto;
				height: auto;
				border-top: solid 1px #BBB;
				border-left: solid 1px #BBB;
				border-bottom: solid 1px #FFF;
				border-right: solid 1px #FFF;
				background: #FFF;
				overflow: scroll;
				padding: 5px;
			}			

			
		</style>
		
		<script src="jquery.js" type="text/javascript"></script>
		<script src="jquery.easing.js" type="text/javascript"></script>
		<script src="jqueryFileTree.js" type="text/javascript"></script>
		<link href="jqueryFileTree.css" rel="stylesheet" type="text/css" media="screen" />
		
		
</head>

<body>
<script type="text/javascript">
			
			$(document).ready( function() {
				var path=$("#path").val();				
				$('#fileTreeDemo_1').fileTree({ root: path, script: 'jqueryFileTree.jsp' }, function(file) { 
					var n = file.indexOf("Directory");
				});				
			});
		</script>
<div class="row">
				<div class="col-lg-6">
					<div class="input-group">
<br><br><h1>Package</h1><br>
			<div class="input-group">
			
		<form action="welcome.jsp" method="POST">
		<br/>
		<div class="example">
			<div id="fileTreeDemo_1" class="demo"></div>		
		</div>
		<%
		URL location = NewRobotServlet.class.getProtectionDomain().getCodeSource().getLocation();
	        String currentPath = location.getFile().toString();
		String path=getServletContext().getRealPath("/Directory/").toString();
		if(session.getAttribute("gae_role")!=null)
		if(! session.getAttribute("gae_role").equals("OWNER")) 
			path=path+session.getAttribute("user");
		%>
				<input type="hidden" id="path" name="path" value=<%=path%>/>
				<br/>
	<button type="submit" class="btn btn-success" style="background:white; color:#18bc9c">HOME</button>				
		</form>
			</div>
			</div>
			</div>
			</div>
</body>
</html>