package com.moviehub.DAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.moviehub.model.Account;
import com.moviehub.model.Film;
import com.moviehub.model.FilmSaved;
import com.moviehub.model.FilmWatched;
import com.moviehub.model.Profile;



public class DBConnector {
	
    Statement stm = null;
    ResultSet rs = null;
    Connection cnn = null;
    
    public void getInstance() {
        try {
            String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=APP_FILM;user=sa;password=sa";
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
            cnn =  DriverManager.getConnection(dbURL);
            System.out.println("Đã kết nối database\n");
        }
        catch ( SQLException e) {
            System.out.println("Không thể kết nối database\n" + e);
        }
    }
    
    //get data from table Account
    public ArrayList<Account> getDataAccount(){
        ArrayList<Account> ds = new ArrayList<>();
        getInstance();
        try {        
            stm = cnn.createStatement();
            String query = "SELECT * FROM ACCOUNT";
            rs = stm.executeQuery(query);
            while(rs.next()){
                ds.add(new Account(rs.getString(1).trim(),rs.getString(2).trim(),rs.getInt(3)));
            }
            stm.close();
            CloseConnect();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.toString());
        }
        return ds;
    }
    
    
    //get data from table USER_PROFILE
    public ArrayList<Profile> getDataProfile(){
    	
    	ArrayList<Profile> ds = new ArrayList<>();
        getInstance();
        try {        
            stm = cnn.createStatement();
            String query = "SELECT * FROM USER_PROFILE";
            rs = stm.executeQuery(query);
            while(rs.next()){
            	
            	Profile profile = new Profile();
            	profile.setUsername(rs.getString(1).trim());
            	profile.setFistName(rs.getString(2).trim());
            	profile.setLastName(rs.getString(3).trim());
            	if(rs.getString(4)==null)
            	{
            		profile.setImage("saitama.png");
            	}
            	else {
            		profile.setImage(rs.getString(4).trim());
				}
            	
            	System.out.println("Profile: "+profile.toString());
                ds.add(profile);
            }
            stm.close();
            CloseConnect();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.toString());
        }
        return ds;
    }
    
    //add new user
    public boolean addAccount(Account account) throws SQLException{
        getInstance();
        int isSuccess = 0;
        String insertAccount = "insert into ACCOUNT values "
        		+ "('"+account.getUsername().trim()+"', "
        		+ "'"+account.getPassword().trim()+"', '0')";
        String insertProfile = "insert into USER_PROFILE values ('"
        		+account.getUsername().trim()+"', N'No', N'Fullname', 'null')";
        stm = (Statement) cnn.createStatement();
        
        isSuccess = stm.executeUpdate(insertAccount);
        stm.executeUpdate(insertProfile);
       
        return isSuccess>0 ? true : false;
    }
    
    //Update info of account
    public boolean updateInfoAccount(Profile profile) throws SQLException {
    	
    	getInstance();
        int isSuccess = 0;
      //  FileOutputStream stream = new FileOutputStream();
        
        String updateProfile = "update USER_PROFILE set firstName = N'"+profile.getFistName()+"', "
        		+ "lastName = N'"+profile.getLastName()+"',"
        		+ "avatar = '"+profile.getImage()+"'"
        		+ "where username_ = '"+profile.getUsername()+"'";
       
        stm = (Statement) cnn.createStatement();
        
        isSuccess = stm.executeUpdate(updateProfile);
    	
        return isSuccess>0 ? true : false;
	}
    
    //get all film from db
    public ArrayList<Film> getDataFilm(){
    	
    	ArrayList<Film> ds = new ArrayList<>();
        getInstance();
        try {        
            stm = cnn.createStatement();
            String query = "SELECT * FROM FILM";
            rs = stm.executeQuery(query);
            while(rs.next()){
                ds.add(new Film(rs.getString(1).trim(),rs.getString(2).trim(),rs.getString(3).trim(), rs.getString(4).trim(), rs.getString(5).trim(), rs.getString(6).trim(), rs.getString(7).trim(), rs.getString(8).trim(), rs.getString(9).trim()));
            }
            stm.close();
            CloseConnect();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.toString());
        }
        return ds;
    }
    
  //get film by genre from db
    public ArrayList<Film> getDataFilmByGenre(String genre){
    	
    	ArrayList<Film> ds = new ArrayList<>();
        getInstance();
        try {        
            stm = cnn.createStatement();
            String query = "SELECT * FROM FILM WHERE genre = '"+genre+"'";
            rs = stm.executeQuery(query);
            while(rs.next()){
                ds.add(new Film(rs.getString(1).trim(),rs.getString(2).trim(),rs.getString(3).trim(), rs.getString(4).trim(), rs.getString(5).trim(), rs.getString(6).trim(), rs.getString(7).trim(), rs.getString(8).trim(), rs.getString(9).trim()));
            }
            stm.close();
            CloseConnect();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.toString());
        }
        return ds;
    }
    
    //get film watched of user
    public ArrayList<FilmWatched> getDataFilmWatched(String username){
    	
    	ArrayList<FilmWatched> ds = new ArrayList<>();
        getInstance();
        try {        
            stm = cnn.createStatement();
            String query = "SELECT * FROM WATCHED_FILM WHERE username_ = '"+username+"'";
            rs = stm.executeQuery(query);
            while(rs.next()){
                ds.add(new FilmWatched(rs.getString(1).trim(),rs.getInt(2),rs.getString(3).trim()));
            }
            stm.close();
            CloseConnect();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.toString());
        }
        return ds;
    }
    
    //get film saved of user
    public ArrayList<Film> getDataFilmSaved(String username){
    	
    	ArrayList<FilmSaved> ds = new ArrayList<>();
    	ArrayList<Film> films = new ArrayList<>();
        getInstance();
        try {        
            stm = cnn.createStatement();
            String query = "SELECT * FROM SAVED_FILM WHERE username_ = '"+username+"'";
            rs = stm.executeQuery(query);
            while(rs.next()){
                ds.add(new FilmSaved(rs.getString(1).trim(),rs.getString(2)));
            }
            
            for(FilmSaved filmSaved : ds)
            	films.add(this.getDataFilmById(filmSaved.getIdFilm()));
            
            stm.close();
            CloseConnect();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.toString());
        }
        return films;
    }
    
    //get film by id
    public Film getDataFilmById(String id){
    	Film film = new Film();
        getInstance();
        try {        
            stm = cnn.createStatement();
            String query = "SELECT * FROM FILM WHERE id_film = '"+id+"'";
            rs = stm.executeQuery(query);
            rs.next();
            film = new Film(rs.getString(1).trim(),rs.getString(2).trim(),rs.getString(3).trim(), rs.getString(4).trim(), rs.getString(5).trim(), rs.getString(6).trim(), rs.getString(7).trim(), rs.getString(8).trim(), rs.getString(9).trim());
            stm.close();
            CloseConnect();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.toString());
        }
        return film;
    }
    
    //get current progress of film by id
    public int getCurrentProgressFilmById(String id, String username){
    	int progress = 0;
        getInstance();
        try {        
            stm = cnn.createStatement();
            String query = "SELECT * FROM WATCHED_FILM WHERE id_film = '"+id+"' AND username_ = '"+username+"'";
            rs = stm.executeQuery(query);
            if(rs.next())
            	progress = rs.getInt(2);
            
            stm.close();
            CloseConnect();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.toString());
        }
        return progress;
    }
    
    //update film views
    public boolean updateFilmView(String id, int views)
    {
    	int isUpdated = 0;
        getInstance();
        try {        
            stm = cnn.createStatement();
            String update = "update FILM set film_views = '"+views+"' where id_film = '"+id+"'";
            
            isUpdated = stm.executeUpdate(update);
            
            stm.close();
            CloseConnect();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.toString());
        }
        return isUpdated > 0 ? true : false;
    }
    
    //update film watched
    public boolean updateFilmWatched(FilmWatched filmWatched)
    {
    	int isUpdated = 0;
    	boolean isExist = false;
    	ArrayList<FilmWatched> filmWatcheds = this.getDataFilmWatched(filmWatched.getUsername());
    	
    	for(FilmWatched watched : filmWatcheds)
    	{
    		if(filmWatched.getIdFilm().equals(watched.getIdFilm()) && filmWatched.getUsername().equals(watched.getUsername()))
    		{
    			isExist = true;
    			break;
    		}
    	}
    	
    	getInstance();
    	
    	if(isExist)
    	{
	        String update = "update WATCHED_FILM set cur_progress = '"+filmWatched.getCurProgress()+"' where id_film = '"+filmWatched.getIdFilm()+"' AND username_='"+filmWatched.getUsername()+"'";
	        try {
	        	stm = cnn.createStatement();
				isUpdated = stm.executeUpdate(update);
				stm.close();
			} catch (SQLException ex) {
				System.out.println("Error: " + ex.toString());
			}
	        CloseConnect();
	        
            return isUpdated > 0 ? true : false;
    	}
    	else {
    		String insertFilmWatched = "insert into WATCHED_FILM values ( '"+filmWatched.getIdFilm()+"', "+filmWatched.getCurProgress()+", '"+filmWatched.getUsername()+"')";
	        try {
	        	stm = cnn.createStatement();
				isUpdated = stm.executeUpdate(insertFilmWatched);
				stm.close();
			} catch (SQLException ex) {
				System.out.println("Error: " + ex.toString());
			}
	        CloseConnect();
	        
            return isUpdated > 0 ? true : false;
		}
    	
    }
    
    
  //update film loved
    public boolean updateFilmLoved(String filmId, String username)
    {
    	int isUpdated = 0;
    	boolean isExist = false;
    	ArrayList<Film> filmSaveds = this.getDataFilmSaved(username);
    	
    	for(Film saved : filmSaveds)
    	{
    		if(saved.getId_film().equals(filmId))
    		{
    			isExist = true;
    			break;
    		}
    	}
    	
    	getInstance();
    	
    	if(isExist)
    	{
	        String removeQuery = "Delete from SAVED_FILM where username_='"+username+"' AND id_film='"+filmId+"'";
	        try {
	        	stm = cnn.createStatement();
				isUpdated = stm.executeUpdate(removeQuery);
				stm.close();
			} catch (SQLException ex) {
				System.out.println("Error: " + ex.toString());
			}
	        CloseConnect();
	        
            return isUpdated > 0 ? true : false;
    	}
    	else {
    		String insertFilmWatched = "insert into SAVED_FILM values ( '"+filmId+"', '"+username+"')";
	        try {
	        	stm = cnn.createStatement();
				isUpdated = stm.executeUpdate(insertFilmWatched);
				stm.close();
			} catch (SQLException ex) {
				System.out.println("Error: " + ex.toString());
			}
	        CloseConnect();
	        
            return isUpdated > 0 ? true : false;
		}
    	
    }
    
    //disconnect from server
    public void CloseConnect() {
        if (cnn != null) {
            try {
                cnn.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }
}
