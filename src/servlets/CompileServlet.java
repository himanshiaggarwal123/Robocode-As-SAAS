package servlets;
import robocode.RobocodeCompile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;

import DTO.RobotDTO;
import Service.UpdateRobotRestClientService;
import edu.utdallas.Compile;
@WebServlet("/CompileServlet")
public class CompileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection = null;
	PreparedStatement ptmt = null;
	ResultSet resultSet = null;

	public CompileServlet() {
		// TODO Auto-generated constructor stub
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String robotCode = (String)request.getAttribute("robocode");

		HttpSession session = request.getSession();
		RobotDTO robotDTO = null;
		String RobotCode=null,selectedItem = null,robotID=null, packageID=null;

		/*if (request.getParameter("RobotCode") != null) {
			selectedItem = request.getParameter("RobotCode");
		}
		String[] words = selectedItem.split("blah");*/
		
		robotDTO = (RobotDTO) session.getAttribute("RobObj");
		packageID=robotDTO.getPackageId();
		robotID=robotDTO.getRobotName();
		RobotCode = request.getParameter("textArea");
		System.out.println("Info "+packageID+" "+robotID);
		//RobotCode =words[2];
		//robotName = words[0];
		//packageID=words[1];
//		RobocodeCompile.CompileFile(robotID,packageID);
		
		Connection conn=null;
		try{
			byte[] compiledCode = Compile.compile(packageID, robotID, RobotCode, getServletContext().getRealPath("/")+"robocode.jar");
			String workingDir = System.getProperty("user.dir");
		    //System.getProperty("user.dir");
			System.out.println(workingDir);
			System.out.println("in update: " + robotDTO.getUserId() + " "+ robotDTO.getPackageId() + " " + robotDTO.getRobotName());
			String fileName = robotDTO.getRobotName()+".class";
			String relativePath = File.separator 
			        + robotDTO.getUserId() + File.separator + robotDTO.getPackageId() + File.separator;
			System.out.println(workingDir + relativePath+ fileName);
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
			Path path = Paths.get(workingDir + relativePath + fileName);
			Files.write(path, compiledCode); //creates, overwrites
			
			String url = "jdbc:mysql://104.154.142.10/robocode";
			String user = "himanshi";
			String password = "aggarwal";
			conn = DriverManager.getConnection(url, user, password);
			String sql = "UPDATE robot SET compiledCode=? WHERE RobotID='"+robotDTO.getRobotName()+"'";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setBlob(1, new SerialBlob(compiledCode));
			statement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace(System.err);
		}finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
		}
		
		
		System.out.println("Successfully compiled");
		request.setAttribute("message", "Successfully compiled");
		request.setAttribute("packageID", packageID);
		request.setAttribute("robotID", robotID);
		request.setAttribute("Robocode", RobotCode);
		//PrintWriter out = response.getWriter();
		//out.println(RobotCode);
		RequestDispatcher rd=request.getRequestDispatcher("Edit_Robot2.jsp");    
		rd.forward(request, response);
	}


}
