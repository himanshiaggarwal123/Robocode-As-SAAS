package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
/**
 * Servlet implementation class CreateRobot
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import DTO.RobotDTO;
import DTO.Robot_DTO;
import DTO.UserDTO;

/**
 * Servlet implementation class Login
 */
//@WebServlet("/createrobot")
public class NewRobotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection = null;
	PreparedStatement ptmt = null;
	ResultSet resultSet = null;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String selectedItem = null;
		HttpSession session = request.getSession();
		
		System.out.println(request.getParameter("RobotInfo"));
		if (request.getParameter("RobotInfo") != null) {
			selectedItem = request.getParameter("RobotInfo");
		}
		String[] words = selectedItem.split("-");
		System.out.println(words[0] +" "+words[1]);
		String robotPackage =words[0];
		String name = words[1];
		String robouser = words[2];
		String role = words[3];
		/*String allRoles = words[4];
		System.out.println("roles: "+ allRoles);
		String joinedRoles = "";
		if (allRoles.charAt(0)==',') joinedRoles = allRoles.substring(1,allRoles.length());
		String[] eachRole = joinedRoles.split(",");
		for(int i=0; i<eachRole.length; i++) System.out.println(eachRole);*/
		name=name.trim();
		session.setAttribute("roboName",name);
		request.setAttribute("roboName",name);
		request.setAttribute("package",robotPackage);
		session.setAttribute("package",robotPackage);
		String robotName = String.valueOf(request.getAttribute("roboName"));
		String directoryStructure = String.valueOf(request.getAttribute("package"));
		String packageName = "sample";
		System.out.println("roboName:"+robotName+" with package:"+packageName+" role: "+ role + " username: "+ robouser);
		String message = null;		
		RobotDTO robotDTO = new RobotDTO();
		
		session.setAttribute("tenant_name", robouser);
		RobotDTO robotAccessDTO = new RobotDTO();
		robotAccessDTO.setUserId(robouser);
		robotAccessDTO.setRobotName(name);
		robotAccessDTO.setPackageId(robotPackage);
		robotDTO.setRobotName(robotName);
		robotDTO.setPackageId(packageName);
		robotDTO.setUserId(robouser);
		robotDTO.setStoragePath(directoryStructure);
		robotDTO.setRole(role);
		robotDTO.setCreatedDate(String.valueOf(new Date()));
		
		
		String url = "jdbc:mysql://104.154.142.10/robocode";
		String username = "himanshi";
		String password = "aggarwal";
		String robotCode=
				"package "+packageName+";\n"+
				"import robocode.*;\n"+
				"//import java.awt.Color;\n"+
				"						\n"+
				"// API help: http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html\n"+
				"															\n"+	
				"/**\n"+
				 "* "+robotName+"- a robot by "+robouser+"\n"+
				" */\n"+
				"public class "+robotName+" extends Robot{"+
				"	/**\n"+
				"	 * run: "+robotName+"default behavior"+
				"	 */\n"+
				"	public void run() {\n"+
				"	// Initialization of the robot should be put here\n"+
				"												\n"+
				"	// After trying out your robot, try uncommenting the import at the top\n"+
				"	// and the next line:\n"+
				"						\n"+
				"	// setColors(Color.red,Color.blue,Color.green); // body,gun,radar\n"+
				"								\n"+
				"	// Robot main loop\n"+
				"		while(true) {\n"+
				"			// Replace the next 4 lines with any behavior you would like\n"+
				"			ahead(100);\n"+
				"			turnGunRight(360);\n"+
				"			back(100);\n"+
				"			turnGunRight(360);\n"+
				"		}\n"+
				"	}\n"+
				"	/**\n"+
				"	 * onScannedRobot: What to do when you see another robot\n"+
				"	 */\n"+
				"	public void onScannedRobot(ScannedRobotEvent e) {\n"+
				"		// Replace the next line with any behavior you would like\n"+
				"		fire(1);\n"+
				"	}\n"+
				"						\n"+
				"	/**\n"+
				"	 * onHitByBullet: What to do when you're hit by a bullet\n"+
				"	 */\n"+
				"	public void onHitByBullet(HitByBulletEvent e) {\n"+
				"		// Replace the next line with any behavior you would like\n"+
				"		back(10);\n"+
				"	}\n"+
				"					\n"+
				"	/**"+
				"	 * onHitWall: What to do when you hit a wall\n"+
				"	 */"+
				"	public void onHitWall(HitWallEvent e) {\n"+
				"		// Replace the next line with any behavior you would like\n"+
				"		back(20);\n"+
				"	}	\n"+
				"}	\n";
		System.out.println(robotDTO.getCreatedDate());
		try {
			String[] aclargs = new String[2];
			aclargs[0] = directoryStructure+robotName;
			aclargs[1] = robotCode;
			//aclargs[2] = role;	
			String[] eachRole = new String[1];
			eachRole[0] = role;
			String workingDir = System.getProperty("user.dir");
			System.out.println(workingDir);
			//String relativePath = "//" + user +"/"+packageName+"/";
			String fileName = robotName+".java";
			String relativePath = File.separator 
			        + robouser + File.separator + packageName + File.separator;
			System.out.println("newRobot: relativePath"+  relativePath);
			System.out.println("newRobot: "+ workingDir  + relativePath+ fileName);
			File outFile =  new File(workingDir  + relativePath+ fileName);
			outFile.getParentFile().mkdirs();
			outFile.createNewFile();
			Connection conn = DriverManager.getConnection(url, username, password);
			String sql = "INSERT INTO robot (CreatedDate,packageID,robotID,userID,filepath,dataaccess,role,StorageDirPath) VALUES(?, ?, ?, ?, ?, ?, ?, ?) ";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, robotDTO.getCreatedDate());
			statement.setString(3, robotName);
			statement.setString(2, packageName);
			statement.setString(4, robouser);
			statement.setString(6, "Y");
			statement.setString(7, role);
			statement.setString(8, directoryStructure);
			statement.setString(5, relativePath + fileName);
			int count = statement.executeUpdate();
			
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(workingDir+relativePath+fileName), "utf-8"))) {
				writer.write(robotCode);
			}

			String filePath2=relativePath+robotDTO.getRobotName()+".java";
			//update file in database
			sql="UPDATE robot SET file = ? WHERE robotID='"+robotName+"'";
			statement = conn.prepareStatement(sql);
			File file=new File(workingDir+filePath2);
			InputStream inputStream = new FileInputStream(file);
			statement.setBinaryStream(1,inputStream,(int)file.length());
			count = statement.executeUpdate();
			robotAccessDTO.setFilePath(workingDir + relativePath+robotName+".java");
			CreateNewRobotAcl.StorageACL(aclargs, eachRole);
			String sql2 = "Select id from robot where userId='"+robouser+"' and robotID='"+robotName+"'";
			Statement statement1 =  conn.createStatement();
			ResultSet resultset = statement1
					.executeQuery(sql2);
			String robotID = "";
			while(resultset.next()){
				robotID = (String)resultset.getString(1);
			}
			System.out.println(robotID);
			String robotRoleMap = "INSERT INTO robot_role (role,robotID) VALUES(?, ?) ";
			statement = conn.prepareStatement(robotRoleMap);
			statement.setString(1, role);
			statement.setString(2, robotID);
			count = statement.executeUpdate();
			conn.close();

		
		}
		catch (Exception e) {
			 e.printStackTrace();
		}
		
		
		session.setAttribute("RobObj", robotAccessDTO);
		session.setAttribute("roboname", robotName);
		session.setAttribute("DirPath", directoryStructure);
		session.setAttribute("robouser", robouser);

		
		String userName = (String) session.getAttribute("user");
		System.out.println("end"+userName + "robotDTO: "+ robotDTO.getPackageId()+" "+robotDTO.getRobotName()+" "+robotDTO.getStoragePath());

		if(userName != null){


			robotDTO.setUserId(userName);
			session.setAttribute("objCurrentRobot", robotDTO);
			session.setAttribute("message",message);
			
			
		}
	}
}