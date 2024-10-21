package com.viet.music.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viet.music.dto.ApiResponse;
import com.viet.music.dto.response.SongResponse;
import com.viet.music.service.PlaylistService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("playlist")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PlaylistController {
	
	PlaylistService playlistService;
	
	@PostMapping("/addSongs")
	ApiResponse<List<SongResponse>> addSongs(List<String> id){
		return ApiResponse.<List<SongResponse>>builder()
                .result(playlistService.addSongs(id))
                .build();
	}
	
	@GetMapping("getAllSongs")
	ApiResponse<List<SongResponse>> GetAllSongsInPlaylist(String id){
		 return ApiResponse.<List<SongResponse>>builder()
	                .result(playlistService.GetAllSongsInPlaylist(id))
	                .build();
	}
}
