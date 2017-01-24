package servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DTO.RobotDTO;

/**
 * Servlet implementation class NewPackage
 */
@WebServlet("/NewPackage")
public class NewPackage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewPackage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String url = "jdbc:mysql://104.154.142.10/robocode";
		String user = "himanshi";
		String password = "aggarwal";

		RobotDTO robotDTO = null;
	
		robotDTO = (RobotDTO) session.getAttribute("RobObj");
		robotDTO.setUpdatedDate(String.valueOf(new Date()));
		request.setAttribute("date", robotDTO.getUpdatedDate());
		
		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "UPDATE robot SET UpdatedDate='"+robotDTO.getUpdatedDate()+"'WHERE RobotID='"+robotDTO.getRobotName()+"'";
			//set the code for the robot
			String sql2 = "UPDATE robot SET RobotCode=? WHERE RobotID='"+robotDTO.getRobotName()+"'";
			//create a new file with the updated robot code

			PreparedStatement statement = conn.prepareStatement(sql);
			int count = statement.executeUpdate();
			PreparedStatement statement2 = conn.prepareStatement(sql2);

			
			count = statement.executeUpdate();
			conn.close();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		
		RequestDispatcher rd=request.getRequestDispatcher("welcome.jsp");    
		rd.forward(request, response);
//		PrintWriter out = response.getWriter();
//				out.println(RobotCode);
//				out.close();
		//rd.include(request,response);
	}

}

