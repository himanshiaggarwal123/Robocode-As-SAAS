package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;

public class GuestAccess {
	public static void listBlobs(Bucket bucket){
		Iterator<Blob> blobIterator = bucket.list().iterateAll();
	    System.out.println("My blobs:");
	    while (blobIterator.hasNext()) {
	    	System.out.println(blobIterator.next());
	    }
	}
	    
	public static void getGuestAccess(String role, String useremail) throws Exception{
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

	    String selectString = "SELECT StorageDirPath, robotId from robot where id in (SELECT robotId from robot_role where role='"+role+"');";
		ResultSet resultset = statement.executeQuery(selectString);
		System.out.println("In guestAccess: "+ selectString);
		while(resultset.next()){
			String directoryStructure = resultset.getString(1)+"/"+resultset.getString(2);
			System.out.println(directoryStructure);

			Role aclRole = CreateNewRobotAcl.findGAERole(role, statement);
			BlobId blobId = BlobId.of(bucket.getName(), directoryStructure );
			System.out.println("ACL before: "+useremail+" "+ aclRole +" "+ blobId.getName());
			Acl acl = storage.createAcl(blobId, Acl.of(new User(useremail), aclRole));
			System.out.println("ACL done: "+useremail+" "+ aclRole);

		   
	    }
	    
		
	}

}
