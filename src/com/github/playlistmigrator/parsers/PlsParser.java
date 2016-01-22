package com.github.playlistmigrator.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.input.BOMInputStream;

public class PlsParser implements PlaylistParser{

	private final File file;
	
	public PlsParser( final File file ){
		this.file = file;
	}
	
	public File[] parse(){
		BufferedReader br = null;
		
		List<File> files = new ArrayList<File>();
		
		try {
			br = new BufferedReader( 
					new InputStreamReader( 
						new BOMInputStream( 
							new FileInputStream( this.file ) 
						) 
					) 
			      );
			
			String line = br.readLine();
			
			// If the first line isn't "[playlist]" it isn't a valid playlist
			// so return an empty list
			if( !line.startsWith("[playlist]") ) return new File[0];
			
			while((line = br.readLine())!= null){
				line = line.trim();
									
				// Find  "File#=" statements and add the files to the list
				if( line.startsWith("File") ){
					line = line.substring(line.indexOf("=") + 1);
					if( line.startsWith("~" + File.separator) ){
						line = line.replace( "^~", System.getProperty("user.home") );
					}
					files.add( new File(line) );
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try{
				br.close();
			}catch( Exception e ){
				
			}
		}

		return files.toArray( new File[files.size()] );
	}

}
