package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;

import com.google.api.services.storage.StorageScopes;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;

public class UpdateACL {
	public static void listBlob(Storage storage, Bucket bucket){
		Iterator<Blob> blobIterator = bucket.list().iterateAll();
		   // List the blobs in a particular bucket
		    System.out.println("My blobs:");
		    while (blobIterator.hasNext()) {
		      Blob b = blobIterator.next();
		  	  System.out.println("Blob Name: " + b.getName());
		      BlobId blobId2 = BlobId.of(bucket.getName(), b.getName());
		    }
		   
	}
		
	public static void DeleteRobotACL(String roleName){
		try{
			Path path = Paths.get(CreateNewRobotAcl.class.getResource(".").toURI());
	        Path projPath = path.getParent().getParent().getParent(); 
	        File newPath = new File(projPath+File.separator+"StorageAuth"+File.separator+"StorageSample-49761e6d6339.json");
	        
			InputStream credentialStream = new FileInputStream(newPath);
			Collection<String> scopes = StorageScopes.all();
			Storage storage = StorageOptions.newBuilder().setCredentials(ServiceAccountCredentials.fromStream(credentialStream).createScoped(scopes)).setProjectId("storagesample-151118").build().getService();
		    System.out.println("Storage connection established");
		    
			String bucketName = "robocode-storage"; // bucket of project
		    
		    Boolean newBucketFlag = CreateNewRobotAcl.findBucket(storage, bucketName);
		    Bucket bucket = null;
		    if (!newBucketFlag){
		         bucket = storage.create(BucketInfo.of(bucketName));
		    }
		    else{
		    	bucket = storage.get(bucketName);
		    }
		    System.out.println("find Bucket: "+ newBucketFlag);
	    
				String driverClassName = "com.mysql.jdbc.Driver";
				String connectionUrl = "jdbc:mysql://104.154.142.10/robocode";
				String dbUser = "himanshi";
				String dbPwd = "aggarwal";
				Class.forName(driverClassName).newInstance();
				Connection connection = DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
				Statement statement = connection.createStatement();
				System.out.println("roleName "+roleName);
				String selectString3="SELECT StorageDirPath, robotId from robot where id in (SELECT robotId from robot_role where role='"+ roleName +"');";
				System.out.println(selectString3);
				ResultSet resultset3 = statement.executeQuery(selectString3);
				
				while(resultset3.next()){
					System.out.println("resultset3 not null :) ");
					String directoryStructure = resultset3.getString(1);
					String robotName = resultset3.getString(2);
					String blobName = (directoryStructure+"/"+robotName);
					System.out.println(bucket.getName()+" "+blobName);
					BlobId blobId = BlobId.of(bucketName, blobName);
					if (blobId == null) System.out.println("null blob");
					String selectString2="SELECT username from users where role='"+ roleName.trim() +"';";
					System.out.println(selectString2);
					ResultSet resultset2 = statement.executeQuery(selectString2);
					while(resultset2.next()){
						String email = resultset2.getString(1);
						if(!email.equals("guestuser@gmail.com")){
							System.out.println("email: "+email+" blobId: "+ blobId.getName());
							Acl acl = storage.getAcl(blobId, new User(email));
							boolean acl2 = storage.deleteAcl(blobId, new User(email));
							System.out.println("ACL: "+ acl2);
						}
					}
				}
				
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void UpdateRobotACL(String roleName, String NewGAERole){
		try{				
			
			Path path = Paths.get(CreateNewRobotAcl.class.getResource(".").toURI());
	        Path projPath = path.getParent().getParent().getParent(); 
	        File newPath = new File(projPath+File.separator+"StorageAuth"+File.separator+"StorageSample-49761e6d6339.json");
	        
			InputStream credentialStream = new FileInputStream(newPath);
			Collection<String> scopes = StorageScopes.all();
			Storage storage = StorageOptions.newBuilder().setCredentials(ServiceAccountCredentials.fromStream(credentialStream).createScoped(scopes)).setProjectId("storagesample-151118").build().getService();
		    System.out.println("Storage connection established");
		    
			String bucketName = "robocode-storage"; // bucket of project
		    
		    Boolean newBucketFlag = CreateNewRobotAcl.findBucket(storage, bucketName);
		    Bucket bucket = null;
		    System.out.println("find Bucket: "+ newBucketFlag);
		    if (!newBucketFlag){
		         bucket = storage.create(BucketInfo.of(bucketName));
		    }
		    else{
		    	bucket = storage.get(bucketName);
		    }
			Role aclRole = Role.READER;
			if(NewGAERole.equals("OWNER")) aclRole = Role.OWNER;

					String driverClassName = "com.mysql.jdbc.Driver";
					String connectionUrl = "jdbc:mysql://104.154.142.10/robocode";
					String dbUser = "himanshi";
					String dbPwd = "aggarwal";
					Class.forName(driverClassName).newInstance();
					Connection connection = DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
					Statement statement = connection.createStatement();
					System.out.println("roleName "+roleName);
					String selectString="SELECT StorageDirPath, robotId from robot where id in (SELECT robotId from robot_role where role='"+ roleName +"');";
					System.out.println(selectString);
					ResultSet resultset = statement.executeQuery(selectString);
					

					while(resultset.next()){
						String directoryStructure = resultset.getString(1);
						String robotName = resultset.getString(2);
						String blobName = (directoryStructure+"/"+robotName);
						System.out.println(bucket.getName()+" "+(directoryStructure+"/"+robotName));
						BlobId blobId = BlobId.of(bucketName, blobName);
						if (blobId == null) System.out.println("null blob");
						String selectString2="SELECT username from users where role='"+ roleName +"'";
						ResultSet resultset2 = statement.executeQuery(selectString2);
						while(resultset2.next()){
							String email = resultset2.getString(1);
							if (!email.equals("guestuser@gmail.com")){
								System.out.println("email: "+email+" blobId: "+ blobId.getName());
								Acl acl = storage.getAcl(blobId, new User(email));
								System.out.println("ACL: "+ acl.getRole());
		
								Acl acl2 = storage.updateAcl(blobId, Acl.of(new User(email), aclRole));
								System.out.println("ACL: "+ acl2.getRole());
							}
						}

					}
					
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}	
			
	}

}
