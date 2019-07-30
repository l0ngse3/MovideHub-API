package com.moviehub.ApiClient;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.moviehub.DAO.DBConnector;
import com.moviehub.model.Film;
import com.moviehub.model.FilmWatched;

@Path("/Film")
public class FilmService {
	
	//get all film 
	@GET
	@Path("/GetAll")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getAllFilm()
	{
		DBConnector db = new DBConnector();
		System.out.println("Get all film from DB");
		
		ArrayList<Film> list = db.getDataFilm();
		
		return  Response.
				status(Response.Status.OK).
				entity(new Gson().toJson(list)).
				build();
	}
	
	
	// get film by genre
	@GET
	@Path("/Genre/{genre}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getFilmByGenre(@PathParam("genre") String genre)
	{
		DBConnector db = new DBConnector();
		System.out.println("Get film by genre from DB");
		
		ArrayList<Film> list = db.getDataFilmByGenre(genre);
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("genre", genre);
		jsonObject.addProperty("list", new Gson().toJson(list));
		
		return  Response.
				status(Response.Status.OK).
				entity(jsonObject.toString()).
				build();
	}

	
//	get film saved of user
	@GET
	@Path("/Saved/{username}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getSavedFilm(@PathParam("username") String username)
	{
		DBConnector db = new DBConnector();
		System.out.println("Get film saved of user");
		ArrayList<Film> list = db.getDataFilmSaved(username);
		
		return  Response.
				status(Response.Status.OK).
				entity(new Gson().toJson(list)).
				build();
	}
	
//	get film by id
	@GET
	@Path("/id/{filmId}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getFilmById(@PathParam("filmId") String filmId)
	{
		DBConnector db = new DBConnector();
		
		Film film = db.getDataFilmById(filmId);
		System.out.println("Get film watched by Id" + new Gson().toJson(film));
			return  Response.
				status(Response.Status.OK).
				entity(new Gson().toJson(film)).
				build();
	}
	
//	get film watched of user
	@GET
	@Path("/Watched/{username}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getWathcedFilm(@PathParam("username") String username)
	{
		DBConnector db = new DBConnector();
		System.out.println("Get film watched of user");
		ArrayList<FilmWatched> list = db.getDataFilmWatched(username);
		
		return  Response.
				status(Response.Status.OK).
				entity(new Gson().toJson(list)).
				build();
	}
	
	// get progress of film watched
	@GET
	@Path("Watched/GetCurrentProgress/")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getFilmWatchedById(@QueryParam("filmId") String filmId, @QueryParam("username") String username)
	{
		DBConnector db = new DBConnector();
		
		int currentProgress = db.getCurrentProgressFilmById(filmId, username);
		System.out.println("Get film progress by Id: " + new Gson().toJson(currentProgress));
			return  Response.
				status(Response.Status.OK).
				entity(new Gson().toJson(currentProgress)).
				build();
	}
	
	@PUT
	@Path("Update/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public void updateFilmViews(@PathParam("id") String filmId)
	{
		DBConnector db = new DBConnector();
		
		Film film = db.getDataFilmById(filmId);
		int view = Integer.parseInt(film.getFilm_views());
		view+=1;
		boolean isUpdated = db.updateFilmView(filmId, view);
		String s = isUpdated ? "Views updated in "+filmId : "Cannot update film views in "+filmId;
		System.out.println(s);
	}
	
	@PUT
	@Path("UpdateWatchedFilm/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateFilmWatched(FilmWatched filmWatched)
	{
//		FilmWatched filmWatched = new FilmWatched();
		System.out.println(filmWatched.getIdFilm());
		
		boolean response= false;
		//update data when it was read
		DBConnector db = new DBConnector();
		boolean isUpdated = db.updateFilmWatched(filmWatched);
		//log some thing in consolse and response to client
		String s = isUpdated ? "Time progress updated" : "Cannot update Time progress";
		response = isUpdated ? true : false;
		System.out.println(s);
		
		return  Response.
				status(Response.Status.OK).
				entity(new Gson().toJson(response)).
				build();
	}
	
	@PUT
	@Path("Save/Loved")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateFilmLoved(@QueryParam("filmId") String filmId, @QueryParam("username") String username)
	{
//		FilmWatched filmWatched = new FilmWatched();
		System.out.println("Film loved " + filmId);
		
		boolean response= false;
		//update data when it was read
		DBConnector db = new DBConnector();
		boolean isUpdated = db.updateFilmLoved(filmId, username);
		//log some thing in consolse and response to client
		response = isUpdated ? true : false;
		System.out.println(response ? "Add film saved "+filmId : "Delete film saved "+filmId);
		
		return  Response.
				status(Response.Status.OK).
				entity(new Gson().toJson(response)).
				build();
	}
	
	@GET
	@Path("Save/IsSaved")
	@Produces(MediaType.APPLICATION_JSON)
	public Response isFilmSaved(@QueryParam("filmId") String filmId, @QueryParam("username") String username)
	{
//		FilmWatched filmWatched = new FilmWatched();
		System.out.println("Film loved " + filmId);
		
		boolean response= false;
		//update data when it was read
		DBConnector db = new DBConnector();
		ArrayList<Film> listSaved = db.getDataFilmSaved(username);
		//log some thing in consolse and response to client
		for(Film film : listSaved)
		{
			if(film.getId_film().equals(filmId))
			{
				response = true;
			}
		}
		
		System.out.println("Film saved id "+filmId+" of "+username+" : "+response);
		
		return  Response.
				status(Response.Status.OK).
				entity(new Gson().toJson(response)).
				build();
	}
}

