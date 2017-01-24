package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Acl.User;

public class CheckAccess {
	
	public static List<String> findRobotsRole(String role, String username, String packageName){
		List<String> setOfvalues = new ArrayList<String>();
		try{
			System.out.println("FindRobot role: "+ role+" "+username);
			ACLUtility au = new ACLUtility();
			au.createStorageConnection("robocode-storage");
			Storage storage = au.storage;
			Bucket bucket = au.bucket;
			String driverClassName = "com.mysql.jdbc.Driver";
			String connectionUrl = "jdbc:mysql://104.154.142.10/robocode";
			String dbUser = "himanshi";
			String dbPwd = "aggarwal";
			Class.forName(driverClassName).newInstance();
			Connection connection = DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
			Statement statement = connection.createStatement();
			String selectString="SELECT StorageDirPath, robotId from robot where StorageDirPath='"+packageName+"' and id in (SELECT robotId from robot_role where role='"+ role +"') or userId='"+username+"';"  ;
			System.out.println(selectString);
			ResultSet resultset = statement.executeQuery(selectString);
			
			while(resultset.next()){
				String robopackageName = resultset.getString(1);
				String robotname = resultset.getString(2);
				BlobId blobId = BlobId.of(bucket.getName(), (robopackageName+"/"+robotname));
				Acl acl = storage.getAcl(blobId, new User(username));	
				if (acl.getEntity().toString().equals(("user-"+username))){
					setOfvalues.add(robotname);
				}
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return setOfvalues;

	}

}
