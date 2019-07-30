package com.moviehub.ApiClient;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sun.misc.BASE64Decoder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.moviehub.DAO.DBConnector;
import com.moviehub.model.Profile;

@Path("/Profile")
public class UserProfileService {

	private static String HOST_STORAGE = "E:/work-space-android/film_test/ImageProfile/";
	
	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getProfile(@PathParam("username") String username)
	{
		DBConnector db = new DBConnector();
		System.out.println("http://localhost:8080/MovieHub_API/MovieHubClient/Profile/"+username);
		
		ArrayList<Profile> list = db.getDataProfile();
		Profile check = new Profile();
		for (Profile profile : list) {
			if(profile.getUsername().equals(username))
			{
				check = profile;
				break;
			}
		}
		
		System.out.println(new Gson().toJson(check));
		
		return Response.
				status(Response.Status.OK).
				entity(new Gson().toJson(check)).
				build();
	}
	
	@POST
	@Path("/updateInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response postUpdateInfoProfile(Profile profile)
	{
		DBConnector db = new DBConnector();
		System.out.println("Error: Save infor of profile "+ profile.toString());
		if(!profile.getImage().contains(profile.getUsername()) && !profile.getImage().equals("null"))
		{
			BASE64Decoder decoder = new BASE64Decoder();
			BufferedImage image = null;
			byte[] imageByte;
			
			try {
				// decode string into byte to save in server
				imageByte = decoder.decodeBuffer(profile.getImage());
				ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
				image = ImageIO.read(bis);
				bis.close();
				//save image of profile
				String extention = profile.getUsername()+".png";
				String locationOfImage = UserProfileService.HOST_STORAGE + extention;
				File outputfile = new File(locationOfImage);
				ImageIO.write(image, "png", outputfile);
				
				profile.setImage(extention);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		JsonObject result = new JsonObject();
		
		try {
			if(db.updateInfoAccount(profile))
			{
				result.addProperty("announce", "Saved");
			}
			else {
				result.addProperty("announce", "Can not save");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error: can not save infor of profile "+ profile.toString());
		}
		
		
		System.out.println(new Gson().toJson(result));
		
		return Response.
				status(Response.Status.OK).
				entity(new Gson().toJson(result)).
				build();
	}
}
