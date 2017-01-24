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
<title>New Robot</title>
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
</head>

<body>
<%@include file="includes/header.jsp" %>
<body>
	<!--  <div class="container">
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">
					<!--  <i class="fa fa-file-text"></i>	Create a New Robot,<<%= (String) session.getAttribute("user") %>
						</h1>
				</div>
				<!-- /.col-lg-12 
			</div>
			</div> -->
			<!-- /.row -->
			<br><br><br><br>
			<div class="row">
				<div class="col-lg-6">
<!--  <form method="post" action="editservlet"> -->
					<div class="input-group">
						<%
						String userName = (String) session.getAttribute("user");
						if (userName == null){
							session.setAttribute("user", "chandni.shankar08@gmail.com");
							session.setAttribute("role", "collaborator");
						}
						System.out.println((String)session.getAttribute("user") +" "+(String)session.getAttribute("role"));
							String userid = (String)session.getAttribute("user");
							Set<String> list_of_tenants = new HashSet<String>();
							Set<String> list_of_domains = new HashSet<String>();
							Set<String> list_of_robots = new HashSet<String>();
							Set<String> list_of_storageDirs = new HashSet<String>();

							HashMap<String, List<String>> map = new HashMap<String, List<String>>();
							HashMap<String, List<String>> domain_robot_map = new HashMap<String, List<String>>();							
								try {
									String driverClassName = "com.mysql.jdbc.Driver";
									String connectionUrl = "jdbc:mysql://104.154.142.10/robocode";
									String dbUser = "himanshi";
									String dbPwd = "aggarwal";
									Class.forName(driverClassName).newInstance();;
									Connection connection = DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
									Statement statement = connection.createStatement();
									String whereClause = "role='viewer'";
									String role = (String) session.getAttribute("role");
									String selectString="SELECT packageID, robotID, StorageDirPath from robot where userID='"+userid+"'";
									System.out.println(selectString);
									resultset = statement
											.executeQuery(selectString);
									
											%>
<script type="text/javascript">
		function getDomains() {

			var x = document.getElementById("domain_name").value;
			
			$.ajax({
				url : "GetrobotDomain",
				data : "tenant_name=" + x + "",
				type : 'POST',
				async : false,
				success : function(html) {
					console.log("html:" + html);
					$("#package").html(html);
				},
				error : function(html) {
					console.log("error html:" + html);
				}
			});
		}	
		</script>	<br><br><h1>New Robot</h1><br>
		&nbsp;&nbsp;&nbsp;&nbsp;<!-- label>Select User</label>
						<select name="domain_name" id="domain_name" class="form-control" onchange="getDomains()"
							>
							<option>Select User</option>

							<%
							session.setAttribute("tenantMap", map);
							session.setAttribute("DomainMap", domain_robot_map);
							session.setAttribute("userx", "Esther");
								%>
						</select --> <br /> 
						<input name="userID" type="hidden" id='userID' value = <%= userid%>>
						
						<script type="text/javascript">
		function getRobots() {

			var x = document.getElementById("package").value;
			console.log("x ",x);
			if (x == "other"){
				document.getElementById("packageOther").style.display = 'block';
				document.getElementById("packageSelect").style.display = 'None';

			}
			
		}	
		</script>		
		<script>
		
		 function getPackage(){
		       window.location = 'SelectPackageNew.jsp';
		 }
		</script>
		<label>Select Package</label>
		<%String val=request.getParameter("dir");
		if(val==null){
			val=" ";
		}
		%>
		<input type="text" id="package" name="package"  onClick="getPackage();" value=<%=val %>>					
								<%
								Set<String> packageList = new HashSet<String>(); 
								while (resultset.next()) {
									packageList.add(resultset.getString(3));
								}
								Iterator iterator = packageList.iterator();
								while (iterator.hasNext()) {
									String packageName = (String) iterator.next();
								
									%>
							<%} 
								}catch (Exception e) {
									out.println("wrong entry" + e);
								}
							%>
							
						</div>
						&nbsp;&nbsp;&nbsp;&nbsp;<label>Enter Robot Name. Example: MyFirstRobot. Must not contain spaces.</label>
					    <br>
						&nbsp;&nbsp;&nbsp;&nbsp;<label> Robot Name: </label> &nbsp;&nbsp;&nbsp;&nbsp;<input width="200px" name="roboName" id="robo_name"
								type="text" class="fa-la"/>
						
						</div>
						</div>
						<br>
						<script type="text/javascript">
				 function NewRobot(){
					 //var other = document.getElementById("selectedOther").value;
						//console.log("other",other);

						var robotPackage=document.getElementById("package").value;
						console.log("robotPackage1",robotPackage);
						if(robotPackage == 'other' || robotPackage == undefined){
							robotPackage = document.getElementById("selectedOther").value
						}
						
						console.log("robotPackage",robotPackage);
			        	 var name = document.getElementById("robo_name").value;
			        	 var user = document.getElementById("userID").value;
			        	 alert("user "+ user);
			        	 var role = document.getElementById("role").value;
			            $.ajax({
			                url: 'newRobot',
			                type: 'POST',
			                data: "RobotInfo="+robotPackage+"-"+name+"-"+user+"-"+role,
			                async : false,
			                success : function(html) {
			    				console.log(html);
			    				window.location.replace("NewRobot2.jsp"); 
			                }
			            });  
			        	event.preventDefault();
			        	}
				 </script>
				 
				 	&nbsp;&nbsp;&nbsp;&nbsp;<label>Select Role Permision</label>
					<div id="roleselect" style="display:block">
						
						<select multiple name="role" id="role" class="form-control">
							<% try{ 
								String driverClassName = "com.mysql.jdbc.Driver";
								String connectionUrl = "jdbc:mysql://104.154.142.10/robocode";
								String dbUser = "himanshi";
								String dbPwd = "aggarwal";
								Class.forName(driverClassName).newInstance();;
								Connection connection = DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
								Statement statement2 = connection.createStatement();
								String selectString="SELECT DISTINCT role_name from role";
								resultset = statement2.executeQuery(selectString);
								while (resultset.next()) {
									String role = (String) resultset.getString(1);
									%>
									<option value="<%=role%>"><%=role%></option>
									
									
								<%	
								}
							}
							catch(Exception e){
							
							}
							%>
	
						</select> <br /> 
						</div>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="submit" class="btn btn-success" id="create" onclick="NewRobot();">Next</button>
					</form>
				</div> 
				<!-- /.col-lg-6 (nested) -->
			</div>
			<!-- /.row (nested) -->
		</div>
		<!-- /.panel-body -->
	</div>

</body>
</html>