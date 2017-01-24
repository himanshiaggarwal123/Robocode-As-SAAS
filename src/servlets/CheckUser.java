package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;


public class CheckUser {
	
	
	public static User CheckUserPresent(String decUsername, String decPassword) throws IOException, JSONException{
		String enusername = decUsername;
		//Check if user is present in UserDB
		
		UserController uc = new UserController();
		User newUser = new User(enusername, decPassword, "defaultLoc", "guest");
		System.out.println(uc.isUserExist(enusername));
		if (!uc.isUserExist(enusername)){
			newUser = uc.createUser(newUser);
		}
		else{
			UserRescources wr = new UserRescources();
			newUser = wr.findUser(User.class, decUsername);

			}

        return newUser;
	}
}


