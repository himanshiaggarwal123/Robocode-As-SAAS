<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Play Battle!</title>
	<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
	    <script language="javascript">
	   
	    function writeSummary(summary) {
	        summaryElem =
	            document.getElementById("summary");
	        summaryElem.innerHTML += "<br>";
	        summaryElem.innerHTML += summary;
 	        document.getElementById("test").value=summaryElem.innerHTML;
	    }
	    </script>
</head>
<body>

  <!-- %@include  file="RoboCode-Applet/coderobo/coderobo/robocode.html" % -->
  
  <%
    String robots = request.getParameter("robots");
	out.println("robots: "+ robots);
%>

     <div id="includedContent"></div>
     <script src =
      "http://www.java.com/js/deployJava.js"></script>
    <script> 
        <!-- ... -->
        deployJava.runApplet(attributes,
            parameters, '1.7'); 
    </script>  
    <p id="summary">  </p>
   <!--  <noscript>A browser with JavaScript enabled is required for this page to operate properly.</noscript>
    <h1>Dynamic Tree Applet Demo</h1>
    <h2>This applet has been deployed with the applet tag <em>without</em> using JNLP</h2>-->
    <applet alt = "Dynamic Tree Applet Demo" 
        code = 'robocode.Robocode'
        archive = "applet9.jar"
        width = 800,
        height = 600 >
        <param name="robots" value=<%=robots%> >
        </applet>   
        <form id = "skoresform" action = "ScoresShow.jsp" method = "POST" >
 <input type="hidden" name="test" id="test" />
 <input type="hidden" name="robot1" id="robot1" value="<%=robots%>"/>
 <input type = "Submit" value = "SCORES">
 </form> 
</body>
</html>