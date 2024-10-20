package com.viet.music.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viet.music.dto.ApiResponse;
import com.viet.music.dto.response.SongResponse;
import com.viet.music.service.PlaylistService;

import jakarta.websocket.server.PathParam;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@Slf4j
public class PlaylistController {
	
	PlaylistService playlistService;
	
	@PostMapping("/addSongs/{playlistId}")
	ApiResponse<List<SongResponse>> addSongs(@PathVariable String playlistId,@RequestBody List<String> id){
		return ApiResponse.<List<SongResponse>>builder()
                .result(playlistService.addSongs(playlistId,id))
                .build();
	}
	
	@GetMapping("getAllSongs/{id}")
	ApiResponse<List<SongResponse>> GetAllSongsInPlaylist(@PathVariable String id){
		 return ApiResponse.<List<SongResponse>>builder()
	                .result(playlistService.GetAllSongsInPlaylist(id))
	                .build();
	}
}
