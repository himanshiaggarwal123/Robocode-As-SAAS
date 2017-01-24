package servlets;


import java.text.MessageFormat;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserRescources {
	 private WebTarget webTarget;
	 private Client client;
	 private static final String BASE_URI = "https://cloudcomputing-147420.appspot.com/api";
	 
	 public UserRescources() {
	        client = ClientBuilder.newClient();
	        webTarget = client.target(BASE_URI).path("users");
	        System.out.println("client url: " + webTarget.getUri());
	 }
	 public <T> T findUser(Class<T> responseType, String id) throws ClientErrorException {
	        WebTarget resource = webTarget;
	        resource = resource.path(MessageFormat.format("{0}", new Object[]{id}));
	        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
	 }
	 public Response editUser(Object requestEntity, String username) throws ClientErrorException {
	        return  webTarget.path(MessageFormat.format("{0}", new Object[]{username}))
	                .request(MediaType.APPLICATION_JSON)
	                .put(Entity.entity(requestEntity, MediaType.APPLICATION_JSON));
	 }
	
	 public <T> T getAllUsers(GenericType<T> superType) throws ClientErrorException {
	        WebTarget resource = webTarget;
	        return resource.request(MediaType.APPLICATION_JSON).get(superType);
	 } 
	 public <T> T createUser(Object requestEntity, Class<T> responseType) throws ClientErrorException {
	        return webTarget.request(MediaType.APPLICATION_JSON)
	                .post(Entity.entity(requestEntity, MediaType.APPLICATION_JSON),responseType);
	 }
	 public void close() {
	        client.close();
	 }
}
