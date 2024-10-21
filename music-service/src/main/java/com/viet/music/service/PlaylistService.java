package com.viet.music.service;

import java.util.List;

import org.springframework.stereotype.Service;


import com.viet.music.dto.response.SongResponse;
import com.viet.music.entity.Playlist;
import com.viet.music.entity.Song;
import com.viet.music.exception.AppException;
import com.viet.music.exception.ErrorCode;
import com.viet.music.repository.PlaylistRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@Slf4j
public class PlaylistService {
	PlaylistRepository playlistRepository;
	SongService songService;
	
	public List<SongResponse> GetAllSongsInPlaylist(String id) {
		Playlist playlist=playlistRepository
				.findById(id)
				.orElseThrow(
						() -> new AppException(ErrorCode.KHONG_TON_TAI_PLAYLIST
								));
		List<String> SongIDs=playlist.getSongIDs();
		return songService.getSongAllById(SongIDs);
	}

	public List<SongResponse> addSongs(String playlistId,List<String> id) {
		Playlist playlist=playlistRepository
				.findById(playlistId)
				.orElseThrow(
						() -> new AppException(ErrorCode.KHONG_TON_TAI_PLAYLIST
								));
		playlist.setSongIDs(id);
		try {
			playlist=playlistRepository.save(playlist);
		} catch (Exception e) {
			throw  new AppException(ErrorCode.LOI_KHONG_LUU_DUOC) ;
		}
		return GetAllSongsInPlaylist(playlist.getId());
	}
}
