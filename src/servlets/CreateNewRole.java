package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateNewRole
 */
@WebServlet("/CreateNewRole")
public class CreateNewRole extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewRole() {
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
		System.out.println("In Servlets.CreteNewRole");
		try{
			
			String driverClassName = "com.mysql.jdbc.Driver";
			String connectionUrl = "jdbc:mysql://104.154.142.10/robocode";
			String dbUser = "himanshi";
			String dbPwd = "aggarwal";
			Class.forName(driverClassName).newInstance();
			Connection connection = DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
			
			String roleName = request.getParameter("role_name");
			String GAERole = "";			
			String newRobot = request.getParameter("createRobot");
			String editRobot = request.getParameter("editRobot");
			String viewRobot = request.getParameter("viewRobot");
			String editRole = request.getParameter("editRole");
			String editUser = request.getParameter("editUser");
			if(newRobot.equals("yes")|| editRobot.equals("yes")|| editRole.equals("yes")|| editUser.equals("yes")){
				GAERole="OWNER";
			}
			else{
				GAERole="READER";				
			}
			System.out.println(roleName + " "+ newRobot+ " "+ editRobot+ " "+ viewRobot+ " "+ editRole+ " "+ editUser+ " "+ GAERole);
			String sql = "INSERT INTO role (role_name, gae_role, new_robot, edit_robot, view_robot, edit_role, edit_user) VALUES(?, ?, ?, ?, ?, ?, ?) ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, roleName);
			statement.setString(2, GAERole);
			statement.setString(3, newRobot);
			statement.setString(4, editRobot);
			statement.setString(5, viewRobot);
			statement.setString(6, editRole);
			statement.setString(7, editUser);
			int count = statement.executeUpdate();
			System.out.println(count+":count");
			connection.close();
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

}
