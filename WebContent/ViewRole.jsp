<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%
	session = request.getSession();

	ResultSet resultset = null;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Role</title>
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
	function getRoleDetails(){
		var role = document.getElementById("role_name").value;
		$.ajax({
			url : "getRoleDetails",
			data : "role_name=" + role,
			type : 'POST',
			async : false,
			success : function(response) {
				var data = JSON.parse(response);
				if(data.new_robot == 'yes'){
					$('#createRobot').prop('checked', true);
				}
				else{
					$('#createRobot').prop('checked', false);
				}
				if(data.edit_robot == 'yes'){
					$('#editRobot').prop('checked', true);
				}
				else{
					$('#editRobot').prop('checked', false);
				}
				if(data.view_robot == 'yes'){
					$('#viewRobot').prop('checked', true);
				}
				else{
					$('#viewRobot').prop('checked', false);
				}
				if(data.edit_role == 'yes'){
					$('#editRole').prop('checked', true);
				}
				else{
					$('#editRole').prop('checked', false);
				}
				if(data.edit_user == 'yes'){
					$('#editUser').prop('checked', true);
				}
				else{
					$('#editUser').prop('checked', false);
				}
				
				document.getElementById("restofdets").style.display = 'block';
				

				
			},
			error : function(html) {
				console.log("error html:" + html);
			}
		});
		

	}


    </script>
	<br><br><h1>Edit Role</h1><br>
	<!-- form method="post" id="roleData" action="createNewRole" -->
		<form id="roleData">
	
				<div id="message" ></div>
	
		<div>
        	<label for="roles">Roles</label>
        	<select name="role_name" id="role_name" onchange="getRoleDetails()">
        	<option>Select Role</option>
        	<%
        	try {
				String driverClassName = "com.mysql.jdbc.Driver";
				String connectionUrl = "jdbc:mysql://104.154.142.10/robocode";
				String dbUser = "himanshi";
				String dbPwd = "aggarwal";
				Class.forName(driverClassName).newInstance();;
				Connection connection = DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
				Statement statement = connection.createStatement();
				String selectString="SELECT role_name, gae_role, new_robot, edit_robot, view_robot, edit_role, edit_user from role";
				resultset = statement
						.executeQuery(selectString);
				HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        		while(resultset.next()){
    				List<String> role_values = new ArrayList<String>();
        			String role = (String) resultset.getString(1);
        			role_values.add((String) resultset.getString(2));    
        			role_values.add((String) resultset.getString(3));        		
        			role_values.add((String) resultset.getString(4));        		
        			role_values.add((String) resultset.getString(5));        		
        			role_values.add((String) resultset.getString(6));        		
        			role_values.add((String) resultset.getString(7));
        			map.put(role, role_values);
        			System.out.println(role + " "+role_values.get(0));
					
        	%>		
        	<option value=<%=role %>><%=role %></option>
    	<%			}
        		 
        	%>
        	</select>
        
        	<%
        	session.setAttribute("rolemap", map);
        	connection.close();
    		}
        	catch(Exception e){
        		System.out.println(e.getMessage());
        	}	
        	%>
       </div>
        <div id="restofdets" style="display:none">
        	
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
			
	    </div>
	</form>
</body>
</html>