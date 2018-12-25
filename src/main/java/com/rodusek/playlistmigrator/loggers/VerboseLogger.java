package com.rodusek.playlistmigrator.loggers;

import org.apache.commons.io.FilenameUtils;

public class VerboseLogger implements MigratorLogger{
    
    private static final String ERROR_FORMAT = "(%d/%d) failed to copy '%s', message: \"%s\"";
    private static final String SKIP_FORMAT = "(%d/%d) skipping '%s', reason: %s";
    private static final String COPY_FORMAT = "(%d/%d) copying %s to %s";
    private static final String CREATE_FORMAT = "Creating director %s";

    @Override
    public void logCopy(int current, int total, String from, String to) {
        System.out.println( String.format(COPY_FORMAT, current, total, from, to ));        
    }

    @Override
    public void logSkip(int current, int total, String filename, String reason) {
        String name = FilenameUtils.getName( filename );
        System.out.println( String.format(SKIP_FORMAT, current, total, name, reason ));        
    }

    @Override
    public void logError(int current, int total, String filename, String message) {
        String name = FilenameUtils.getName( filename );
        System.out.println( String.format(ERROR_FORMAT, current, total, name, message ));
    }
    
    @Override
    public void logCreate(String directory) {
        System.out.println( String.format( CREATE_FORMAT, directory ) );
    }

}
