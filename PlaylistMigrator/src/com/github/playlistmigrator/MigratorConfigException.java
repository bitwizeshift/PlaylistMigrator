package com.github.playlistmigrator;

@SuppressWarnings("serial")
public class MigratorConfigException extends Exception{
	
	private final String message;
	
	public MigratorConfigException( String message ){
		this.message = message;
	}
	
	@Override
	public String getMessage(){
		return this.message;
	}

}
