package com.example.twixster;

public class movies {
	private String movieTitle;
	private String movieImg;
	private String movieSelect;
	private String movieTheater;
	private String movieTime;
	private String movieLink;

	public movies()
	{
		movieTitle=null;
		movieImg=null;
		movieSelect=null;
		movieTheater=null;
		movieTime=null;
		movieLink=null;
	}

	public String getmovieTitle() {
		return movieTitle;
	}
	public String getmovieImg() {
		return movieImg;
	}
	public String getmovieSelect() {
		return movieSelect;
	}
	public void setmovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	public void setmovieImg(String movieImg){
		this.movieImg=movieImg;
	}
	public void setmovieSelect(String movieSelect) {
		this.movieSelect= movieSelect;
	}
	public String getMovieTheater() {
		return movieTheater;
	}
	public void setMovieTheater(String movieTheater) {
		this.movieTheater = movieTheater;
	}
	public String getMovieTime() {
		return movieTime;
	}
	public void setMovieTime(String movieTime) {
		this.movieTime = movieTime;
	}
	public String getMovieLink() {
		return movieLink;
	}
	public void setMovieLink(String movieLink) {
		this.movieLink = movieLink;
	}
}

