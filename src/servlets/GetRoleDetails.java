package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

/**
 * Servlet implementation class GetRoleDetails
 */
@WebServlet("/GetRoleDetails")
public class GetRoleDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRoleDetails() {
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
		try {
			
			String role_name = request.getParameter("role_name");
			System.out.println(role_name);
			@SuppressWarnings("unchecked")
			HashMap<String, List<String>> rolemap = (HashMap<String, List<String>>)session.getAttribute("rolemap");
			if (rolemap == null) System.out.println("rolemap nul");
			List<String> setOfvalues = rolemap.get(role_name);
			System.out.println(setOfvalues.get(0));
			JSONObject jobj = new JSONObject();
			jobj.put("gae_role",setOfvalues.get(0));
			jobj.put("new_robot",setOfvalues.get(1));
			jobj.put("edit_robot",setOfvalues.get(2));
			jobj.put("view_robot",setOfvalues.get(3));
			jobj.put("edit_role",setOfvalues.get(4));
			jobj.put("edit_user",setOfvalues.get(5));
			response.getWriter().write(jobj.toString());


		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
