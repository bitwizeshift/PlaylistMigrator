package com.github.playlistmigrator.loggers;

public interface MigratorLogger {
	
	/**
	 * Logs when a file is copied 
	 * 
	 * @param current the current file number 
	 * @param total the total number of files
	 * @param from the source file being copied
	 * @param to the destination of the copy
	 */
	public void logCopy( int current, int total, String from, String to );
	
	/**
	 * Logs when a file is skipped
	 * 
	 * @param current the current file number
	 * @param total the total number of files
	 * @param filename the source file being copied
	 * @param reason the reason for skipping
	 */
	public void logSkip( int current, int total, String filename, String reason );
	
	/**
	 * Logs when a file fails to copy
	 * 
	 * @param current the current file number
	 * @param total the total number of files
	 * @param filename the source file being copied
	 * @param message the error message
	 */
	public void logError( int current, int total, String filename, String message );
	
}
