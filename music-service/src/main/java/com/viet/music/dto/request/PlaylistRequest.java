package com.viet.music.dto.request;

import java.util.List;

public class PlaylistRequest {
	String id;
	String name;
	String desc;
	List<String> songID; // List of Song objects
}
