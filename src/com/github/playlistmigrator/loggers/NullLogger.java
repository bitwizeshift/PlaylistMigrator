package com.github.playlistmigrator.loggers;

/**
 * @class NullLogger
 * 
 * The NullLogger class doesn't log any messages, and exists for quiet runs
 * of the application
 */
public class NullLogger implements MigratorLogger{

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
		// do nothing
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
		// do nothing
	}
	
	/**
	 * Logs when a file fails to copy
	 * 
	 * @param current the current file number
	 * @param total the total number of files
	 * @param filename the source file being copied
	 * @param message the error message
	 */
	public void logError( int current, int total, String filename, String message ){
		// do nothing
	}

	@Override
	public void logCreate(String directory) {
		// do nothing
	}

}
