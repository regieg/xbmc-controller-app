package ch.morefx.xbmc.model;

import java.util.List;

import ch.morefx.xbmc.CommandExecutor;
import ch.morefx.xbmc.XbmcConnection;
import ch.morefx.xbmc.command.GetAlbumsCommand;
import ch.morefx.xbmc.command.GetArtistsCommand;
import ch.morefx.xbmc.command.GetSongsCommand;
import ch.morefx.xbmc.command.JsonCommand;
import ch.morefx.xbmc.command.JsonCommandExecutor;
import ch.morefx.xbmc.command.PlayerOpenCommand;
import ch.morefx.xbmc.command.PlaylistAddCommand;
import ch.morefx.xbmc.command.PlaylistClearCommand;
import ch.morefx.xbmc.util.Check;

public class AudioLibrary {

	private CommandExecutor executor;
	
	public AudioLibrary(XbmcConnection connection) {
		Check.notNull(connection, "connection is null");
		
		this.executor = new JsonCommandExecutor(connection);
	}
	
	public void playSong(Song song){
		Check.notNull(song, "song is null!");
		
		//executor.executeDirect("{\"jsonrpc\": \"2.0\", \"method\": \"Playlist.Clear\", \"params\": { \"playlistid\": 0 }, \"id\": 1}");
		execute(new PlaylistClearCommand(Playlist.Audio));		
		
		//executor.executeDirect("{\"jsonrpc\": \"2.0\", \"method\": \"Playlist.Add\", \"params\": { \"playlistid\": 0, \"item\": { \"albumid\": " + song.getAlbumId() + " } }, \"id\": 1}");
		execute(new PlaylistAddCommand(Playlist.Audio, song.getAlbum()));
		
		//executor.executeDirect("{\"jsonrpc\": \"2.0\", \"method\": \"Player.Open\", \"params\": { \"item\": { \"playlistid\": 0, \"position\": " + song.getPosition() + " } }}");
		execute(new PlayerOpenCommand(Playlist.Audio, song));
	}
	
	public List<Artist> getArtists(){
		GetArtistsCommand command = new GetArtistsCommand();
		execute(command);
		return command.getArtists();
	}
	
	public List<Album> getAlbums(Artist artist){
		Check.notNull(artist, "artist is null!");
		
		GetAlbumsCommand command = new GetAlbumsCommand(artist);
		execute(command);
		return command.getAlbums();
	}
	
	public List<Song> getSongs(Album album){
		Check.notNull(album, "album is null!");
		
		GetSongsCommand command = new GetSongsCommand(album);
		execute(command);
		return command.getSongs();
	}
	
	private void execute(JsonCommand command){
		this.executor.execute(command);
	}
}
