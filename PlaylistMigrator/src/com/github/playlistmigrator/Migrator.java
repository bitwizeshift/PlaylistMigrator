package com.github.playlistmigrator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import com.github.playlistmigrator.loggers.MigratorLogger;

public class Migrator {
	
	private final MigratorConfig config;
	private final File[]         files;
	private final File           dest;
	private final MigratorLogger logger;

	public Migrator( File[] files, File destination, MigratorConfig config, MigratorLogger logger ){
		this.files = files;
		this.dest = destination;
		this.config = config;
		this.logger = logger;
	}
	
	/**
	 * 
	 */
	public void migrate( File base ){
		int current = 1;
		int total   = files.length;
		
		for( File src : this.files ){
			if( src.isFile() ){

				File destFile;
				try{
					if( config.forcesFlatten() ){
						destFile = new File( dest.getAbsoluteFile() + File.separator + src.getName() );
	
						copyFile( src, destFile );
					}else{
						//destFile = new File( );
					}
					logger.logCopy( current, total, src.getAbsolutePath(), dest.getAbsolutePath() );
				}catch( IOException e ){
					logger.logError( current, total, src.getAbsolutePath(), e.getMessage() );
				}catch( MigratorException e ){
					logger.logSkip( current, total, src.getAbsolutePath(), e.getMessage() );
				}
			}else if(!src.exists()){
				logger.logSkip( current, total,src.getName(), "File does not exist" );
			}else if(src.isDirectory()){
				logger.logSkip( current, total, src.getName(), "Specifies a directory");
			}else{
				// Shouldn't get here
				logger.logError( current, total, src.getName(), "Unspecified error");
			}
			++current;
		}
	}
	
	public static void copyFile( File sourceFile, File destFile ) throws IOException, MigratorException {
	    if(destFile.exists()) {
	        throw new MigratorException("File '" + sourceFile.getName() + "' exists in destination.");
	    }

	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	    } finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}
	
	
}
