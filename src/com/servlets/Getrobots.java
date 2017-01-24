package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import servlets.CheckAccess;

public class Getrobots extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			
			String domainName = req.getParameter("domain_name");
			session.setAttribute("packageId", domainName);
			String role =(String) session.getAttribute("role");
			String username = (String) session.getAttribute("user");
			System.out.println("Get Robots tenant name is" + domainName);
			HashMap<String, List<String>> domainMap = (HashMap<String, List<String>>)session.getAttribute("DomainMap");
			System.out.println(domainMap);
			List<String> setOfvalues = domainMap.get(domainName);
			//List<String> setOfvalues = CheckAccess.findRobotsRole(role,username, domainName );

			out.println("<option>Select Robot</option>");
			for(int i =0 ; i < setOfvalues.size(); i++)
			{
				out.print(
						//"<option value='1'>1</option>"
						"<option value='" + setOfvalues.get(i) + "'>" + setOfvalues.get(i) + "</option>"
						);			
			}			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
