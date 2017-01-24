package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DeleteRole
 */
@WebServlet("/DeleteRole")
public class DeleteRole extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteRole() {
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
		System.out.println("In Servlets.DeleteRole");
		HttpSession session = request.getSession();

		try{
			
			String driverClassName = "com.mysql.jdbc.Driver";
			String connectionUrl = "jdbc:mysql://104.154.142.10/robocode";
			String dbUser = "himanshi";
			String dbPwd = "aggarwal";
			Class.forName(driverClassName).newInstance();
			Connection connection = DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
			
			String roleName = request.getParameter("role_name");
			
			UpdateACL.DeleteRobotACL(roleName);
			System.out.println("back: "+roleName);
			if (!roleName.equals("guest")){
			String sql = "DELETE FROM role WHERE role_name='"+roleName+"'";
			PreparedStatement statement = connection.prepareStatement(sql);
			int count = statement.executeUpdate();
			sql = "DELETE FROM robot_role WHERE role='"+roleName+"'";
			statement = connection.prepareStatement(sql);
			count = statement.executeUpdate();}
			String sql3 = "SELECT username from users where role='"+roleName+"'";
			System.out.println("guestAccess: "+sql3);
			Statement statement2 = connection.createStatement();
			ResultSet resultset = statement2
					.executeQuery(sql3);
			while(resultset.next()){
				if (!resultset.getString(1).equals("guestuser@gmail.com"))
					GuestAccess.getGuestAccess("guest", resultset.getString(1));
			}
			String sql = "UPDATE users SET role='guest' WHERE role='"+roleName+"'";
			PreparedStatement statement = connection.prepareStatement(sql);
			int count = statement.executeUpdate();
			connection.close();
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		}

}
