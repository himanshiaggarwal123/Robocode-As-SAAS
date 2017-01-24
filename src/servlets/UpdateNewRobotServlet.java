package servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Statement;

import DTO.RobotDTO;
import DTO.UserDTO;
import Service.UpdateRobotRestClientService;

/**
 * Servlet implementation class UpdateRobotServlet
 */

public class UpdateNewRobotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection = null;
	PreparedStatement ptmt = null;
	ResultSet resultSet = null;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateNewRobotServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String robotCode = (String)request.getAttribute("robocode");

		HttpSession session = request.getSession();
		String url = "jdbc:mysql://104.154.142.10/robocode";
		String user = "himanshi";
		String password = "aggarwal";

		RobotDTO robotDTO = null;
		//String userName = session.getAttribute("userx").toString();
		String RobotCode = request.getParameter("textArea");
		robotDTO = (RobotDTO) session.getAttribute("RobObj");
		robotDTO.setUpdatedDate(String.valueOf(new Date()));
		robotDTO.setRobotCode(RobotCode);
		String workingDir = System.getProperty("user.dir");
		System.out.println("in update: " + robotDTO.getUserId() + " "+ robotDTO.getPackageId() + " " + robotDTO.getRobotName());
	    //System.getProperty("user.dir");
		String fileName = robotDTO.getRobotName()+".java";
		String relativePath = File.separator 
		        + robotDTO.getUserId() + File.separator + robotDTO.getPackageId() + File.separator;
		System.out.println(workingDir  + relativePath+ fileName);
		File outFile =  new File(workingDir  + relativePath+ fileName);
		outFile.getParentFile().mkdirs();
		outFile.createNewFile();
		
		
		
		/*File userDir = new File(workingDir, robotDTO.getUserId());
		if (!userDir.exists()) {
		    System.out.println("creating directory: " + robotDTO.getUserId());
		    try{
		        userDir.mkdir();
		    } 
		    catch(SecurityException se){
		    }        
		}
		String filePath = workingDir+"/"+robotDTO.getUserId();
		String relativePath = "/"+ robotDTO.getUserId()+"/"+robotDTO.getPackageId()+"/";
		File packageDir = new File(filePath, robotDTO.getPackageId());

		if (!packageDir.exists()) {
		    System.out.println("creating directory: " + packageDir);
		    try{
		    	packageDir.mkdir();
		    } 
		    catch(SecurityException se){
		    }        
		}*/
		//robotDTO.setFilePath("C:/robocode/robots/"+robotDTO.getPackageId()+"/"+robotDTO.getRobotName()+".java");
		robotDTO.setFilePath(workingDir + relativePath + robotDTO.getRobotName()+".java");

		//UpdateRobotRestClientService updateRobot = new UpdateRobotRestClientService();
		//String message = updateRobot.updateRobot(robotDTO);

		//request.setAttribute("message", message);
		request.setAttribute("date", robotDTO.getUpdatedDate());
		
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			//System.out.println(robotDTO.getPackageId()+" "+robotDTO.getRobotId());
			//set updated date
			String sql = "UPDATE robot SET UpdatedDate='"+robotDTO.getUpdatedDate()+"'WHERE RobotID='"+robotDTO.getRobotName()+"'";
			//set the code for the robot
			System.out.println("robotDTO Update: "+ robotDTO.getRobotName());
			String sql2 = "UPDATE robot SET RobotCode=? WHERE RobotID='"+robotDTO.getRobotName()+"'";
			//create a new file with the updated robot code

			PreparedStatement statement = conn.prepareStatement(sql);
			int count = statement.executeUpdate();
			PreparedStatement statement2 = conn.prepareStatement(sql2);
			statement2.setString(1, RobotCode);
			count = statement2.executeUpdate();
			//System.out.println(robotDTO.getRobotCode());
			/*try (Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("C://robocode//robots//"+robotDTO.getPackageId()+"//"+robotDTO.getRobotName()+".java"), "utf-8"))) {
				writer.write(robotDTO.getRobotCode());
			}*/
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(workingDir + relativePath + fileName), "utf-8"))) {
				writer.write(robotDTO.getRobotCode());
			}
			//String filePath2="C:/robocode/robots/"+robotDTO.getPackageId()+"/"+robotDTO.getRobotName()+".java";
			String filePath2=workingDir + relativePath+robotDTO.getRobotName()+".java";
			//update file in database
			sql="UPDATE robot SET file = ? WHERE robotID='"+robotDTO.getRobotName()+"'";
			
			statement = conn.prepareStatement(sql);
			File file=new File(filePath2);
			InputStream inputStream = new FileInputStream(file);
			statement.setBinaryStream(1,inputStream,(int)file.length());
			count = statement.executeUpdate();
			conn.close();
			System.out.println("updated message: Updated");

			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		
		RequestDispatcher rd=request.getRequestDispatcher("NewRobot2.jsp");    
		rd.forward(request, response);
//		PrintWriter out = response.getWriter();
//				out.println(RobotCode);
//				out.close();
		//rd.include(request,response);
	}

}
