package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
/**
 * Servlet implementation class UpdateRoleACL
 */
@WebServlet("/UpdateRoleACL")
public class UpdateRoleACL extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateRoleACL() {
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
		doGet(request, response);
		HttpSession session = request.getSession();
		String dat = request.getParameter("dataStr");
		String[] words = dat.split("\\;");
		System.out.println(words[0] +" "+words[1]);
		String robotPackage =words[0];
		String name = words[1];
		String robouser = words[2];
		String role = words[3];
		String oldRole = words[1];
		String newRole = words[2];
		String username = words[0];
		//list of robot in previous role
		//remove use acl from list of robots
		//get list of robots in new role 
		//add user acl to list of robots
		try{
			String driverClassName = "com.mysql.jdbc.Driver";
			String connectionUrl = "jdbc:mysql://104.154.142.10/robocode";
			String dbUser = "himanshi";
			String dbPwd = "aggarwal";
			Class.forName(driverClassName).newInstance();
			Connection connection = DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
			Statement statement = connection.createStatement();
			String selectString="SELECT StorageDirPath, robotId from robot where id in (SELECT robotId from robot_role where role='"+ oldRole +"');"  ;
			ResultSet resultset = statement.executeQuery(selectString);
			ACLUtility au = new ACLUtility();
			au.createStorageConnection("robocode-storage");
			Storage storage = au.storage;
			Bucket bucket = au.bucket;
			while(resultset.next()){
				BlobId blobId = BlobId.of(bucket.getName(), (resultset.getString(1)+"/"+resultset.getString(2)));
				boolean acl2 = storage.deleteAcl(blobId, new User(username));
			}
			Role aclRole = CreateNewRobotAcl.findGAERole(newRole, statement);
			selectString="SELECT StorageDirPath, robotId from robot where id in (SELECT robotId from robot_role where role='"+ newRole +"');"  ;
			resultset = statement.executeQuery(selectString);
			while(resultset.next()){
				BlobId blobId = BlobId.of(bucket.getName(), (resultset.getString(1)+"/"+resultset.getString(2)));
				Acl acl = storage.createAcl(blobId, Acl.of(new User(username), aclRole));			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}

	}

}
