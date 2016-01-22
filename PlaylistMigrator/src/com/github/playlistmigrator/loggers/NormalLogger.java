package com.github.playlistmigrator.loggers;

import org.apache.commons.io.FilenameUtils;

public class NormalLogger implements MigratorLogger{
	
	private static final String ERROR_FORMAT = "(%d/%d) unable to copy '%s'";
	private static final String SKIP_FORMAT = "(%d/%d) skipping '%s'";
	private static final String COPY_FORMAT = "(%d/%d) copying %s";

	
	/**
	 * Logs when a file is copied 
	 * 
	 * @param current the current file number 
	 * @param total the total number of files
	 * @param from the source file being copied
	 * @param to the destination of the copy
	 */
	@Override
	public void logCopy( int current, int total, String from, String to ){
		String filename = FilenameUtils.getName( from );
		System.out.println( String.format(COPY_FORMAT, current, total, filename ));		
	}
	
	/**
	 * Logs when a file is skipped
	 * 
	 * @param current the current file number
	 * @param total the total number of files
	 * @param filename the source file being copied
	 * @param reason the reason for skipping
	 */
	@Override
	public void logSkip( int current, int total, String filename, String reason ){
		String name = FilenameUtils.getName( filename );
		System.out.println( String.format(SKIP_FORMAT, current, total, name ));		
	}
	
	/**
	 * Logs when a file fails to copy
	 * 
	 * @param current the current file number
	 * @param total the total number of files
	 * @param filename the source file being copied
	 * @param message the error message
	 */
	@Override
	public void logError( int current, int total, String filename, String message ){
		String name = FilenameUtils.getName( filename );
		System.out.println( String.format(ERROR_FORMAT, current, total, name ));
	}
	
	@Override
	public void logCreate( String directory ){}
}
