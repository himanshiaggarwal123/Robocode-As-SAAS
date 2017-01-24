package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EditRole
 */
@WebServlet("/EditRole")
public class EditRole extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditRole() {
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
		System.out.println("In Servlets.EditRole");
		HttpSession session = request.getSession();

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
			HashMap<String, List<String>> rolemap = (HashMap<String, List<String>>)session.getAttribute("rolemap");
			List<String> setOfvalues = rolemap.get(roleName);
			String oldGaeRole = setOfvalues.get(0);
			System.out.println("oldGaeRole: "+oldGaeRole);
			if(!oldGaeRole.equals(GAERole)){
				UpdateACL.UpdateRobotACL(roleName, GAERole);
			}
			System.out.println(roleName + " "+ newRobot+ " "+ editRobot+ " "+ viewRobot+ " "+ editRole+ " "+ editUser+ " "+ GAERole);
			String sql = "UPDATE role SET gae_role=?, new_robot=?, edit_robot=?, view_robot=?, edit_role=?, edit_user=? WHERE role_name='"+roleName+"'";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, GAERole);
			statement.setString(2, newRobot);
			statement.setString(3, editRobot);
			statement.setString(4, viewRobot);
			statement.setString(5, editRole);
			statement.setString(6, editUser);
			int count = statement.executeUpdate();
			connection.close();
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}

		
	}

}
