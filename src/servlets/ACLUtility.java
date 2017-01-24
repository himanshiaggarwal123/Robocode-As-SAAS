package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import com.google.api.services.storage.StorageScopes;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class ACLUtility {
	Storage storage;
	Bucket bucket;
	public void createStorageConnection(String bucketName){
		
		try {
			Path path = Paths.get(ACLUtility.class.getResource(".").toURI());
		
        Path projPath = path.getParent().getParent().getParent(); 
        File newPath = new File(projPath+File.separator+"StorageAuth"+File.separator+"StorageSample-49761e6d6339.json");
        
		InputStream credentialStream = new FileInputStream(newPath);
		Collection<String> scopes = StorageScopes.all();
		storage = StorageOptions.newBuilder().setCredentials(ServiceAccountCredentials.fromStream(credentialStream).createScoped(scopes)).setProjectId("storagesample-151118").build().getService();
	    System.out.println("Storage connection established");
	    
		//String bucketName = "robocode-storage"; // bucket of project
	    
	    Boolean newBucketFlag = CreateNewRobotAcl.findBucket(storage, bucketName);
	    bucket = null;
	    if (!newBucketFlag){
	         bucket = storage.create(BucketInfo.of(bucketName));
	    }
	    else{
	    	bucket = storage.get(bucketName);
	    }
	    System.out.println("find Bucket: "+ newBucketFlag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
