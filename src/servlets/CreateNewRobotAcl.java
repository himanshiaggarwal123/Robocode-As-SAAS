package servlets;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.api.services.storage.StorageScopes;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Acl.Entity;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;


public class CreateNewRobotAcl {
	
	private static String projectNo = "339730413148";

	public Bucket updateBucket(Storage storage,String bucketName) {
	    // [START updateBucket]
	    BucketInfo bucketInfo = BucketInfo.newBuilder(bucketName).setVersioningEnabled(true).build();
	    Bucket bucket = storage.update(bucketInfo);
	    // [END updateBucket]
	    return bucket;
	}
	
	public Blob updateBlob(Storage storage,String bucketName, String blobName) {
	    // [START updateBlob]
	    Map<String, String> newMetadata = new HashMap<>();
	    newMetadata.put("key", "value");
	    storage.update(BlobInfo.newBuilder(bucketName, blobName).setMetadata(null).build());
	    Blob blob = storage.update(BlobInfo.newBuilder(bucketName, blobName)
	        .setMetadata(newMetadata)
	        .build());
	    // [END updateBlob]
	    return blob;
	}
	
	
	  public boolean deleteBlob(Storage storage,String bucketName, String blobName) {
		    // [START deleteBlob]
		    BlobId blobId = BlobId.of(bucketName, blobName);
		    boolean deleted = storage.delete(blobId);
		    if (deleted) {
		      // the blob was deleted
		    } else {
		      // the blob was not found
		    }
		    // [END deleteBlob]
		    return deleted;
		}
	
	
	
	
	
	
	
	public static Blob createBlob(Bucket bucket, String directoryStructure, String roboText ){
		Blob newBlob = bucket.create(directoryStructure, roboText.getBytes(UTF_8), "text/plain");
	   // BlobId blobId = BlobId.of(bucketName, blobName);
	   // BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
	//Blob blob = storage.create(blobInfo);
		return newBlob;
	}
	
	public static Role findGAERole(String role, Statement statement){
		Role aclRole = null;
		try {
			String selectString="SELECT gae_role from robocode.role where role_name='"+ role +"'"  ;
			System.out.println("In GAEROne Finding");
			ResultSet	resultset = statement.executeQuery(selectString);
			
			if(resultset.next()){
				if((resultset.getString(1)).equals("OWNER")){
					aclRole = Role.OWNER;
				}
				else{
					aclRole = Role.READER;
				}
			}
			

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return aclRole;
	}
	public static void createBlobAcl(Storage storage, Bucket bucket, String directoryStructure, String role){
		BlobId blobId = BlobId.of(bucket.getName(), directoryStructure );
		try{
			/*
			 * connects to DB, finds the GAE_Role for the new role from role table
			 * select users from users table to add acl for the blob
			 */
			
			String driverClassName = "com.mysql.jdbc.Driver";
			String connectionUrl = "jdbc:mysql://104.154.142.10/robocode";
			String dbUser = "himanshi";
			String dbPwd = "aggarwal";
			Class.forName(driverClassName).newInstance();
			Connection connection = DriverManager.getConnection(connectionUrl,dbUser,dbPwd);
			Statement statement = connection.createStatement();
			
			Role aclRole = findGAERole(role, statement);
			String selectString="SELECT username from robocode.users where role='"+ role +"'"  ;
			ResultSet	resultset = statement.executeQuery(selectString);

			while(resultset.next()){
				String email = (String)resultset.getString(1);
				Acl acl = storage.createAcl(blobId, Acl.of(new User(email), aclRole));
			}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
	}
	
	
	
	public static void displayBucketInfo(Storage storage){
		Iterator<Bucket> bucketIterator = storage.list().iterateAll();
	    System.out.println("My buckets:");
	    while (bucketIterator.hasNext()) {
	      Bucket storeBuckets = bucketIterator.next();
		  System.out.println("Bucket Name:"+storeBuckets.getName());
		  
	      List<Acl> aclList= storeBuckets.getAcl();
	      for(Acl bucketAcl:aclList){
	    	  System.out.println("Bucket ACL Role: "+bucketAcl.getRole()+" Bucket ACL Entity: "+ bucketAcl.getEntity());
	      }
	    }
	}
	
	public static Boolean findBucket(Storage storage,String bucketName){
		Iterator<Bucket> bucketIterator = storage.list().iterateAll();
	    Boolean newBucketFlag = false;
	    while (bucketIterator.hasNext()) {
	      Bucket storeBuckets = bucketIterator.next();
		  if (storeBuckets.getName().equals(bucketName)){
			  newBucketFlag = true;
		  }     
	    }
	    return newBucketFlag;
	}

	public static void StorageACL(String[] args, String[] roles) throws IOException, Exception{
		System.out.println("InNewACL");
        Path path = Paths.get(CreateNewRobotAcl.class.getResource(".").toURI());
        Path projPath = path.getParent().getParent().getParent(); 
        File newPath = new File(projPath+File.separator+"StorageAuth"+File.separator+"StorageSample-49761e6d6339.json");
        System.out.println(newPath);
        
		InputStream credentialStream = new FileInputStream(newPath);
		Collection<String> scopes = StorageScopes.all();
		Storage storage = StorageOptions.newBuilder().setCredentials(ServiceAccountCredentials.fromStream(credentialStream).createScoped(scopes)).setProjectId("storagesample-151118").build().getService();
	    System.out.println("Storage connection established");
	    
		String bucketName = "robocode-storage"; // bucket of project
	    
	    Boolean newBucketFlag = findBucket(storage, bucketName);
	    Bucket bucket = null;
	    System.out.println("find Bucket: "+ newBucketFlag);
	
	    if (!newBucketFlag){
	         bucket = storage.create(BucketInfo.of(bucketName));
	    }
	    else{
	    	bucket = storage.get(bucketName);
	    }
		      
	    String directoryStructure = "default";
	    String roboText = "dummy text";
	    String role = "viewer";
	    
	    if (args.length>0){
	    	directoryStructure = args[0];
	    	roboText = args[1];
	    	
	    }
	    
	    Blob newBlob = createBlob(bucket, directoryStructure, roboText);
	    System.out.println("Blob created");
	    if(roles.length>1){
	    	for (int i=0; i< roles.length; i++)
	    		role = roles[i];
	    	createBlobAcl(storage, bucket, directoryStructure, role);   
	    }
	    else{
	    	role = roles[0];
	    	createBlobAcl(storage, bucket, directoryStructure, role);   
	    }
	        
	    System.out.println("Blob ACL created");
    }
}
