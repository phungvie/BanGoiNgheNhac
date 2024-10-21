package com.viet.music.dto.response;

import java.time.LocalDate;
import java.util.List;

public class SongResponse {
	String id;
	String name;
	String title;
	String album;
	int duration; // duration in seconds
	String file;
	String image;
	String desc;
	LocalDate releaseDate;
	List<String> artistID;
}
