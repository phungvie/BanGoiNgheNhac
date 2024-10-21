package com.viet.music.dto.request;

import java.time.LocalDate;
import java.util.List;

public class SongRequest {
	String id;
	String name;
	String title;
	String album;
	int duration; // duration in seconds
	String desc;
	LocalDate releaseDate;
	List<String> artistID;
}
