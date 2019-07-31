package com.moviehub.ApiClient;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.moviehub.DAO.DBConnector;
import com.moviehub.model.Comment;


@Path("/Comment")
public class CommentService {

	//get all comment by film id
	@GET
	@Path("/GetComment/{filmId}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getCommentByFilmId(@PathParam("filmId") String filmId)
	{
		DBConnector db = new DBConnector();
		System.out.println("get Comment By Film Id");
		
		ArrayList<Comment> list = db.getCommentByFilmId(filmId);
		
		return  Response.
				status(Response.Status.OK).
				entity(new Gson().toJson(list)).
				build();
	}
	
	//add new comment
	@PUT
	@Path("/AddNewComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response addNewComment(String comment)
	{
		DBConnector db = new DBConnector();
		System.out.println("Add New Comment: "+ comment);
		
		boolean isAdded = db.addComment(new Gson().fromJson(comment, Comment.class));
		
		return  Response.
				status(Response.Status.OK).
				entity(new Gson().toJson(isAdded)).
				build();
	}
	
	//update comment by comment id
	@POST
	@Path("/UpdateComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response updateComment(String comment)
	{
		DBConnector db = new DBConnector();
		System.out.println("update Comment: "+comment);
		
		boolean isUpdated = db.updateCommentByCommentId(new Gson().fromJson(comment, Comment.class));
		
		return  Response.
				status(Response.Status.OK).
				entity(new Gson().toJson(isUpdated)).
				build();
	}
	
	//delete comment by comment id
	@DELETE
	@Path("/DeleteComment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response deleteComment(String comment)
	{
		DBConnector db = new DBConnector();
		System.out.println("delete Comment");
		
		boolean isDeleted = db.deleteCommentByCommentId(new Gson().fromJson(comment, Comment.class));
		
		return  Response.
				status(Response.Status.OK).
				entity(new Gson().toJson(isDeleted)).
				build();
	}
}
