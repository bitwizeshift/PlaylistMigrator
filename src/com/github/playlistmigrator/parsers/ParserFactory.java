package com.github.playlistmigrator.parsers;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

public class ParserFactory {

	/**
	 * Gets the appropriate parser based on the file's extension. Returns
	 * null on error
	 * 
	 * @param file the playlist file to open
	 * @return the PlaylistParser
	 */
	public static PlaylistParser getParser( File file ){
		
		String ext = FilenameUtils.getExtension(file.getName());
		
		switch(ext){
		case "m3u": /* no break */
		case "m3u8":
			return new M3UParser( file );
		default:
			break;
		}
				
		return null;
	}	
	
}
