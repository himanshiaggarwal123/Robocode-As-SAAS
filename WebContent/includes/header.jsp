

<%@page import="java.sql.*"%>
<link href="css/bootstrap.css" rel="stylesheet">
<link
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
	rel="stylesheet">
<link href="css/style_header.css" rel="stylesheet">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- Bootstrap Core CSS - Uses Bootswatch Flatly Theme: http://bootswatch.com/flatly/ -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="css/freelancer.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<link
	href='https://fonts.googleapis.com/css?family=Righteous|Fredoka+One'
	rel='stylesheet' type='text/css'>

<link
	href="http://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic"
	rel="stylesheet" type="text/css">

<script src="http://code.jquery.com/jquery.min.js"></script>
</head>
<body id="page-top" class="index">
<%
String new_robot="";
String edit_robot="";
String view_robot="";
String edit_user="";
String edit_role="";
try{
	String driverClassName = "com.mysql.jdbc.Driver";
	String connectionUrl = "jdbc:mysql://104.154.142.10/robocode";
	String dbUser = "himanshi";
	String dbPwd = "aggarwal";
	Class.forName(driverClassName).newInstance();;
	Connection connection = DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
	String sql="Select new_robot,edit_robot,view_robot,edit_user,edit_role from role where role_name='"+session.getAttribute("role")+"';";
	Statement st=connection.createStatement();
	ResultSet rs=st.executeQuery(sql);
	System.out.println(sql);
	rs.next();
		new_robot=rs.getString("new_robot");
		if(new_robot.equals("no")){
			new_robot="none";
		}
		else{
			new_robot="block";
		}
		edit_robot=rs.getString("edit_robot");
		if(edit_robot.equals("no")){
			edit_robot="none";
		}
		else{
			edit_robot="block";
		}
		view_robot=rs.getString("view_robot");
		if(view_robot.equals("no")){
			view_robot="none";
		}
		else{
			view_robot="block";
		}
		edit_user=rs.getString("edit_user");

		if(edit_user.equals("no")){
			edit_user="none";
		}
		else{
			edit_user="block";
		}

		edit_role=rs.getString("edit_role");
		System.out.println(edit_role);
		if(edit_role.equals("no")){
			edit_role="none";
		}
		else{
			edit_role="block";
		}
		System.out.println(edit_role);%>


	<!-- Navigation -->
	<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header page-scroll">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="welcome.jsp">Robocode</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li class="hidden"><a href="#page-top"></a></li>
				<li class="dropdown"><a href="#new">New</a>
					<ul class="dropdown-menu">
						<li style="display:<%=new_robot%>;"><a href="NewRobot.jsp">Robot</a></li>
						<li style="display:<%=new_robot%>;"><a href="NewPackage.jsp">Package</a></li>
						<li style="display:<%=edit_role%>;"><a href="NewRole.jsp">Role</a></li>	
					</ul></li>

				<li class="dropdown"><a href="#edit">Edit</a>
					<ul class="dropdown-menu">
						<li style="display:<%=edit_robot%>;"><a href="Edit_Robot.jsp">Robot</a></li>
						<li style="display:<%=edit_user%>;"><a href="adminInterface.jsp">User</a></li>
						<li style="display:<%=edit_role%>;"><a href="EditRole.jsp">Role</a></li>
					</ul></li>
					<li class="dropdown"><a href="#view">View</a>
					<ul class="dropdown-menu">
						<li style="display:<%=view_robot%>;"><a href="ViewRobot.jsp">Robot</a></li>
						<li style="display:<%=new_robot%>;"><a href="ViewPackage.jsp">Package</a></li>
						<li style="display:<%=edit_role%>;"><a href="ViewRole.jsp">Role</a></li>
					</ul></li>
				<li class="page-scroll"><a href="PlayBattle.jsp">Play Battle!</a></li>
				<!-- li class="page-scroll"><a href="#view">Settings</a></li-->
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user"></span> <%=session.getAttribute("user") %><!-- span class="caret"></span--></a>
      				
        			<ul class="dropdown-menu">
          				<li><a href="#"><%= session.getAttribute("role") %></a></li>
         				<li class="page-scroll"><a href="logout.jsp">Logout</a></li>
        			</ul>
     			 </li>
				<!-- li class="page-scroll"><a href="#view"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li-->
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	</nav>
	<% }
catch(Exception e){
	
}
	%>
	</body>
<script>

function submit() {
	$("#role").submit();
}

function submit1() {
	$("#heirarchy").submit();
}
function submit2() {
	$("#heirarchyc").submit();
}
function submit3() {
	$("#editrole").submit();
}
function submit4() {
	$("#heirarchyEdit").submit();
}
function submit5() {
	$("#packageEdit").submit();
}
function submit6() {
	$("#maph").submit();
}
function submit8() {
	$("#Ppermissionedit").submit();
}
</script>


</html>