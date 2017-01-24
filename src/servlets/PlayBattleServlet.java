package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.DriverManager;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import robocode.CompileRobocode;
import DTO.RobotDTO;
import DTO.UserDTO;
import Service.RobotClientRestService;
import edu.utdallas.Compile;

public class PlayBattleServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//String[] robotNames = req.getParameterValues("selectrobots");
		try{
			
			String driverClassName = "com.mysql.jdbc.Driver";
			String connectionUrl = "jdbc:mysql://104.154.142.10/robocode";
			String dbUser = "himanshi";
			String dbPwd = "aggarwal";
			Class.forName(driverClassName).newInstance();
			Connection connection = DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
			Statement statement = connection.createStatement();
			
			String robotNames = req.getParameter("robots");
			//System.out.println(robotNames);
		String joinedrobotNames = "";
		if (robotNames.charAt(0)==',') joinedrobotNames = robotNames.substring(1,robotNames.length());
		//System.out.println(joinedrobotNames);
		req.setAttribute("robots", joinedrobotNames);

		String[] robots = joinedrobotNames.split(",");
		System.out.println(26);
		for (int i = 0; i< robots.length; i++){
			System.out.println(robots[i]);
			int pos=0;
			for(int j=robots[i].length()-1;i>=0;j--){
				if(robots[i].charAt(j)=='.'){
					pos=j;
					break;
				}
			}
			String  packageName =  robots[i].substring(0, pos);
			String robotName = robots[i].substring(pos + 1 , robots[i].length() );
			System.out.println("package: " + packageName);
			
			String robotPath = "RoboCode-Applet" + File.separator +"coderobo"+File.separator+"coderobo"+File.separator+"applet7"+File.separator+ packageName ;
			String selectString="SELECT RobotCode from robot where StorageDirPath='"+ packageName +"' and robotID='" +robotName+"'"  ;
			System.out.println(selectString);
			ResultSet resultset = statement.executeQuery(selectString);
			String RobotCode = "";
			while (resultset.next()){
				RobotCode = resultset.getString(1);
			}
			if (RobotCode.equals(null)){
				System.out.println("robotCode null");
			}
			else{
				//System.out.println(RobotCode.length());
			}
			URL location = PlayBattleServlet.class.getProtectionDomain().getCodeSource().getLocation();
	        String currentPath = location.getFile().toString();
	        
			String pathServlet = req.getServletContext().getRealPath("/");;
			//System.out.println("ServletPath:"+ pathServlet);
	       // String grandParent = PlayBattleServlet.class.getResource("../../../").toString();
	        //System.out.println(grandParent);
	        String relPath = "";
			byte[] compiledCode = Compile.compile(packageName, robotName, RobotCode, getServletContext().getRealPath("/")+"robocode.jar");
			
			if (currentPath.charAt(0) == '/' ){
				relPath = currentPath.substring(1, currentPath.length());
			}
			else if(currentPath.substring(0,5).equals("file:")){
				relPath = currentPath.substring(6, currentPath.length());
			}
			else{
				relPath = currentPath;
			}
			//String copyPackPath = pathServlet + robotPath;
			String copyPackPath = relPath  + packageName;
	        System.out.println("copyPP "+ copyPackPath);
			String copyPath = copyPackPath + "/" + robotName + ".class";
			System.out.println("copyPath "+ copyPath + " copyPackPath "+ packageName);
			File outFile =  new File(copyPath);
			outFile.getParentFile().mkdirs();
			outFile.createNewFile();
			Path path = Paths.get(copyPath);
			//Path path2 = Paths.get("C:/Users/chand/Desktop/My Workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/RobocodeV1/RoboCode-Applet/coderobo/coderobo/applet7/sample/changedRobo.class");
			//Path path = Paths.get("C:/Users/chand/Desktop/CloudComputing/Project/WorkingHimanshiVersion/RobocodeV12-workingButPlayBattle/RobocodeV12/RobocodeV12/WebContent/RoboCode-Applet/fwdforce_com/appweb/src/Robots/"+ robotName + ".class");
			Files.write(path, compiledCode); 
			//Files.write(path2, compiledCode);
			String appletPath = pathServlet+"applet9.jar";
			System.out.println(appletPath);
			String command = "jar uvf "+ appletPath + " " + packageName;
			String command2 = "jarsigner -keystore "+ (pathServlet+"mykeystore")+" -storepass mypassword "+ appletPath+" myalias";
			System.out.println(command);
			
			
			Process p = null;
			Runtime r1 = Runtime.getRuntime();
			try{
				System.out.println("before cmd");
			p=r1.exec(command, null,new File(relPath));
			
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String s = null;
			System.out.println("br: ");
			while((s = br.readLine())!=null){
				System.out.println(s);
			}
			String t = null;
			System.out.println("bre: ");

			while((t = bre.readLine())!=null){
				System.out.println(t);
			}
			System.out.print(br);
			System.out.print("\n error: "+ bre);

			p.waitFor();
			Process np = null;
			Runtime r2 = Runtime.getRuntime();
			np = r2.exec(command2, null,new File(relPath));
			np.waitFor();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(np.getInputStream()));
			BufferedReader bre2 = new BufferedReader(new InputStreamReader(np.getErrorStream()));
			String s2 = null;
			System.out.println("br2: ");
			while((s2 = br2.readLine())!=null){
				System.out.println(s2);
			}
			String t2 = null;
			System.out.println("bre2: ");

			while((t2 = bre2.readLine())!=null){
				System.out.println(t2);
			}
			System.out.print(br2);
			System.out.print("\n error: "+ bre2);

			}
			catch(Exception e){System.out.println("run cmd not working");}
			
		}
		JSONObject jobj = new JSONObject();
				String urlToRedirect = "PlayBattle2.jsp";
				try {
					jobj.put("url",urlToRedirect);

					jobj.put("robots",joinedrobotNames);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				resp.getWriter().write(jobj.toString());
		}
		catch(Exception e){
			System.out.println("ERRRROOORRR");
			System.out.println("msg: "+ e);
		}
		
	}

}	
	

