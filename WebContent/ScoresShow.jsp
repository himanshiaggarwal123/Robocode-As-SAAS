<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="includes/dropdown.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Result</title>
<script type="text/javascript">
String test = request.getParameter(test);
</script>
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
<header>
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
		
				<div class="intro-text">
					<span class="name">
    <%
    try {
    	//scoressss
		String robots=request.getParameter("robot1");
	    String str=request.getParameter("test");
	    String[] arr = robots.split(",", -1);
	    String[] packageID=new String[arr.length];
	    String[] robotID=new String[arr.length]; 
		for(int i=0;i<arr.length;i++){
			int pos=0;
			for(int j=arr[i].length()-1;i>=0;j--){
				if(arr[i].charAt(j)=='.'){
					pos=j;
					break;
				}
			}
			packageID[i]=arr[i].substring(0, pos);
			robotID[i]=arr[i].substring(pos+1);
		}
	    //Scores
		String[] arr2 = str.split("<br>", -1);
	    double max=0;
	    double[] scores=new double[arr.length] ;
	    for(int i=0;i<arr.length;i++){
	    	String str_bot=arr2[arr2.length-arr.length+i]; 
	    	str_bot=str_bot.replace("=","");
			double int_bot = Double.parseDouble(str_bot);
			max=Math.max(max,int_bot);
			scores[i]=int_bot;
	    }
	    int pos;
	    for(int i=0;i<arr.length;i++){
	    	if(scores[i]==max){
	    		pos=i;
	    		out.println(robotID[i]+" is the Winner with Score : "+max);
	    		break;
	    	}
	    }
	    String driverClassName = "com.mysql.jdbc.Driver";
    	String connectionUrl = "jdbc:mysql://104.154.142.10/robocode";
    	String dbUser = "himanshi";
    	String dbPwd = "aggarwal";
    	Class.forName(driverClassName).newInstance();;
    	Connection connection = DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
    	for(int i=0;i<arr.length;i++){
        	String query="Select Games_win, Games_played, totalScores from robot where robotId = '"+robotID[i]+"' and packageId = '"+packageID[i]+"'";
        
        	Statement st=connection.createStatement();
       ResultSet rs=st.executeQuery(query);

       rs.next();
        	Double totalScores=rs.getDouble("totalScores")+scores[i];
        	int Games_win=rs.getInt("Games_win");
        	if(scores[i]==max){
        		Games_win+=1;
        	}
        	int Games_played=rs.getInt("Games_played")+1;
        	String query1="update robot set  Games_win = ?, Games_played = ?, totalScores = ? where robotId = ? and packageId = ?;";
        	PreparedStatement pst1=connection.prepareStatement(query1);
        	pst1.setInt(1,Games_win);
        	pst1.setInt(2,Games_played);
        	pst1.setDouble(3,totalScores);
        	pst1.setString(4,robotID[i]);
        	pst1.setString(5,packageID[i]);
        	pst1.execute();
    	}    	
    }
    catch(Exception e){
    	e.printStackTrace();
    }
    %>
 </span>
					<hr class="star-light">
					<span class="skills">Build the best, destroy the rest!</span>
				</div>
			</div>
		</div>
	</div>
	</header>
</body>
</html>