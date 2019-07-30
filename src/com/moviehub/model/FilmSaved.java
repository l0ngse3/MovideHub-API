package com.moviehub.model;

public class FilmSaved {
	
	private String username;
	private String idFilm;
	 
	public FilmSaved() {
	}
	
	public FilmSaved(String idFilm, String username) {
		this.username = username;
		this.idFilm = idFilm;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIdFilm() {
		return idFilm;
	}
	public void setIdFilm(String idFilm) {
		this.idFilm = idFilm;
	}
	 
	 
}
