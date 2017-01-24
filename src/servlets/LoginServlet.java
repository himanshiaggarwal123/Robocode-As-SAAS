package servlets;

import java.io.ByteArrayInputStream;

import java.io.IOException;
//import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.*;


import org.codehaus.jettison.json.JSONObject;
import org.w3c.dom.*;

import java.io.*;

import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;

import DAO.LoginDAO;
import DTO.UserDTO;
import Service.LoginRestClientService;
import java.nio.file.*;


public class LoginServlet extends HttpServlet {
	HttpSession session = null;
	UserDTO userDTO = null;
	private static final long serialVersionUID = 1L;
	private String inclause = "";
	private int nnum = 0;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{

			String encodedMsg = request.getQueryString();
	        byte[] decodedBytes = Base64.getDecoder().decode(encodedMsg);
	        String plainMsg = EncryptUtil.decryptWithPrivateKey(decodedBytes, EncryptUtil.getRoboPrivateKey());       
	        String[] params = plainMsg.split("&");
	        String enusername = params[0].split("=")[1];
	        String enpassword = params[1].split("=")[1];	        
	        String entimeStamp = params[2].split("=")[1];   
	        User user= CheckUser.CheckUserPresent(enusername, enpassword);
	        System.out.println("Login Servlet back");
	        
			session = request.getSession();
			session.setAttribute("user", user.getUsername());
			session.setAttribute("role", user.getRole());
			String driverClassName = "com.mysql.jdbc.Driver";
			String connectionUrl = "jdbc:mysql://104.154.142.10/robocode";
			String dbUser = "himanshi";
			String dbPwd = "aggarwal";
			String gaeRole = "";
			try {
				Class.forName(driverClassName).newInstance();;
				Connection connection = (Connection) DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
				Statement statement = (Statement) connection.createStatement();
				String role = user.getRole();
				String selectString="SELECT gae_role from role where role_name='"+ role +"'";
				System.out.println(selectString);
				ResultSet resultset = statement
						.executeQuery(selectString);
				if(resultset.next()){
					gaeRole = resultset.getString(1);
				}
				session.setAttribute("gae_role", gaeRole);
				if (user.getRole().equals("guest"))
					GuestAccess.getGuestAccess("guest", user.getRole());
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
			response.sendRedirect("welcome.jsp");


		} catch (Exception e) {
			System.out.println("Exception encountered."+ e.getMessage()+ " ");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (userDTO == null) {
			userDTO = new UserDTO();
		}

		if (request.getParameter("username") != null) {
			String userName = "arun";
			String password = "arun";
			session = request.getSession();
			userDTO.setUserName(userName);
			userDTO.setPassWord(password);
			session.setAttribute("userx", userName);
		}

		List<String> userRoles = new ArrayList<String>();
		String roleName = request.getParameter("user_role");
		System.out.println("current role:" + roleName);
		if (roleName == null) {

		} else {
			session.setAttribute("SelectedItem", roleName);
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		LoginRestClientService loginRestClientService = new LoginRestClientService();
		List<UserDTO> user_DTO = loginRestClientService.validateUser(userDTO);

		if (user_DTO.size() == 1) {
			if (!(user_DTO.get(0).getUserName().equalsIgnoreCase("Admin"))) {
				session.setAttribute("userDTO", user_DTO.get(0));

				session.setAttribute("userName", user_DTO.get(0).getUserName());
				session.setAttribute("UserId", user_DTO.get(0).getUserId());
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn = (Connection) DriverManager.getConnection(
							"jdbc:mysql://robocodedb.cloudapp.net:3306/Role",
							"naren", "naren");
					String sqle = "SELECT * FROM Pages where HierarchicalRoleID in ";
					String sqll = "SELECT HierarchicalRoleId from UserHierarchicalRole Where UserId = '"
							+ user_DTO.get(0).getUserName() + "'";
					PreparedStatement ppl = (PreparedStatement) conn
							.prepareStatement(sqll);
					ResultSet rl = (ResultSet) ppl.executeQuery();
					while (rl.next()) {
						userRoles.add(rl.getString("HierarchicalRoleId"));
					}

					System.out.println("roles for the user "
							+ user_DTO.get(0).getUserName() + " is"
							+ Arrays.toString(userRoles.toArray()));

					String pages = "";
					inclause = inclause + "('" + roleName + "',";

					trecurs(conn, roleName);
					inclause = inclause.substring(0, inclause.length() - 1)
							+ ")";
					System.out.println("Tree traversal" + inclause);
					sqle = sqle + inclause;
					PreparedStatement pps = (PreparedStatement) conn
							.prepareStatement(sqle);
					ResultSet rs = (ResultSet) pps.executeQuery();
					while (rs.next()) {
						pages = pages + rs.getString("servicename") + "|";
					}
					// close connection
					System.out.println(pages);
					session.setAttribute("roleaccess", pages);
					session.setAttribute("userRole", userRoles);
					conn.close();
				} catch (Exception ex) {
					System.out.println("Exception" + ex.toString());
				}
				response.sendRedirect("welcome.jsp");
			
			} else {
				RequestDispatcher rd = request
						.getRequestDispatcher("admin.jsp");
				rd.forward(request, response);
			}
		} else {
			request.setAttribute("message", "Username or password doesnt exist");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.include(request, response);
		}

		inclause = "";
		out.close();
	}

	private void trecurs(Connection c, String rl) {
		try {
			String sqlr = "SELECT * FROM Hierarchy where ParentHierarchicalRoleId = '"
					+ rl + "'";
			PreparedStatement ppr = (PreparedStatement) c
					.prepareStatement(sqlr);
			ResultSet rr = (ResultSet) ppr.executeQuery();
			while (rr.next()) {
				inclause = inclause + "'" + rr.getString("HierarchicalRoleId")
						+ "',";
				trecurs(c, rr.getString("HierarchicalRoleId"));
			}
		} catch (Exception ex) {
			System.out.println("Exception" + ex.toString());
		}
	}
}