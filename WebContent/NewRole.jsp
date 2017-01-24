<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%
	ResultSet resultset = null;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New Role</title>
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
        form {
			    /* Just to center the form on the page */
			    margin: 0 auto;
			    width: 400px;

			}
		form div + div {
    		margin-top: 1em;
			}
		label {
			    /* To make sure that all labels have the same size and are properly aligned */
			    display: inline-block;
			    width: 130px;
			    text-align: left;
			}
		input:focus, textarea:focus {
			    /* To give a little highlight on active elements */
			    border-color: #000;
			}
		.button {
		    /* To position the buttons to the same position of the text fields */
		    padding-left: 90px; /* same size as the label elements */
		}
		button {
		    /* This extra margin represent roughly the same space as the space
		       between the labels and their text fields */
		    margin-left: .5em;
		    
		}
		
    </style>
</head>

<body>
<%@include file="includes/header.jsp" %>
<body>
	<script type="text/javascript">
    function CreateRole() {  
    	var role, cr, er, vr, ero, eu;
    	if(document.getElementById('role_name').value == null || document.getElementById('role_name').value == ""){
    		alert("Role name cannot be null");
    		return false;
    	} 
    	else{
    		role = document.getElementById('role_name').value;
    	}

    	if (document.getElementById('createRobot').checked){
     		cr = "yes";
    	}
    	else{
    		cr = "no";
    	}
    	if (document.getElementById('editRobot').checked){
     		er = "yes";
    	}
    	else{
    		er = "no";
    	}
    	if (document.getElementById('viewRobot').checked){
     		vr = "yes";
    	}
    	else{
    		vr = "no";
    	}
    	if (document.getElementById('editRole').checked){
     		ero = "yes";
    	}
    	else{
    		ero = "no";
    	}
    	if (document.getElementById('editUser').checked){
     		eu = "yes";
    	}
    	else{
    		eu = "no";
    	}
    	$.ajax({
			url : "createNewRole",
			data : "role_name=" + role + "&createRobot="+cr+ "&editRobot="+er+"&viewRobot="+vr+"&editRole="+ero+"&editUser="+eu,
			type : 'POST',
			async : false,
			success : function(html) {
				console.log("response received");
				document.getElementById('message').innerHTML = 'Successfully updated';
				window.location.replace("welcome.jsp");
				
			},
			error : function(html) {
				console.log("error html:" + html);
			}
		});
		    	  	    	
    }
    </script>
	<br><br><h1>New Role</h1><br>
	<!-- form method="post" id="roleData" action="createNewRole" -->
		<form id="roleData">
	
				<div id="message" ></div>
	
		<div>
        	<label for="name">Name</label>
        	<input type="text" id="role_name" name="role_name" />
    	</div>
    	<br>
	    <div>
	        <label for="rperm">Robot Permission</label>
	        <input type="checkbox" id="createRobot" name="createRobot" value="createRobot"> Create &nbsp;
	        <input type="checkbox" id="editRobot" name="editRobot" value="editRobot"> Edit &nbsp;
	        <input type="checkbox" id="viewRobot" name="viewRobot" value="viewRobot"> View
	    </div>
    	<br>
	    <div>
	        <label for="roperm">Role Permission</label>
	        <input type="checkbox" id="editRole" name="editRole" value="editRole"> Edit
	    </div>    	<br>
	    <div>
	    <label for="uperm">User Permission</label>
	        <input type="checkbox" id="editUser" name="editUser" value="editUser"> Edit
	    </div> <br>
			<div >
	        <input type="submit" value="Create" onclick="return CreateRole()"> 
	    </div>
	</form>
</body>
</html>