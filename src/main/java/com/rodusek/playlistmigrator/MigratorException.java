package com.rodusek.playlistmigrator;

public class MigratorException extends Exception{
    
    private final String message;
    
    public MigratorException( String message ){
        this.message = message;
    }
    
    @Override
    public String getMessage(){
        return this.message;
    }

}
