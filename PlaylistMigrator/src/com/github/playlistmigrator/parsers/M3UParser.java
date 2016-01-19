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

public class M3UParser implements PlaylistParser{
	
	/** UTF8 Byte-order mark */
	public static final String UTF8_BOM  = "\uEFBB";
	/** UTF16 Byte-order mark */
	public static final String UTF16_BOM = "\uFEFF";

	private final File file;
	
	public M3UParser( final File file ){
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
			
			String line = null;
			while((line = br.readLine())!= null){
				line = line.trim();
					
				// Skip lines starting with # (They are comments, or extended directives)
				if( line.startsWith("#") ) continue;
				
				// Skip empty lines
				if( line.isEmpty() ) continue;
								
				// Check for URL
				if( line.startsWith("http://") || line.startsWith("https://") ){
					// Extract file name
					String filename = FilenameUtils.getBaseName( line );
					String filetype = FilenameUtils.getExtension( line );
					
					try{
						// Download the file and store it as a temporary
						ReadableByteChannel in=Channels.newChannel( new URL(line).openStream() );
						files.add( File.createTempFile( filename, filetype ) );
						FileOutputStream fout = new FileOutputStream( filename );
						FileChannel out       = fout.getChannel();
						out.transferFrom(in, 0, Long.MAX_VALUE);
						
						fout.close();
						out.close();
					}catch(IOException e){
						System.out.println("File at " + line + " not found.");
					}
				}else{
					// Must be a file. Correct *nix style '~' paths
					if( line.startsWith("~" + File.separator) ){
						line = line.replace( "^~", System.getProperty("user.home") );
					}
					System.out.println( line );
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
