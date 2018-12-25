package com.rodusek.playlistmigrator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import com.rodusek.playlistmigrator.loggers.MigratorLogger;

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
    public void migrate( ){
        int current = 1;
        int total   = files.length;
        String base = null;
        Path pathBase = null;
        
        // Find the common ancestor path of all files
        if( !config.forcesFlatten() ){
            base = commonPath(this.files);
            pathBase = Paths.get(base);
        }
        
        // Migrate all files
        for( File src : this.files ){
            if( src.isFile() ){

                File destFile;
                try{
                    if( config.forcesFlatten() ){
                        destFile = new File( dest.getAbsoluteFile() + File.separator + src.getName() );
    
                        copyFile( src, destFile );
                    }else{
                        
                        String path = src.getAbsolutePath();

                        Path pathAbsolute = Paths.get( src.getParentFile().getAbsolutePath() );
                        Path pathRelative = pathBase.relativize(pathAbsolute);
                        
                        File destPath = new File( dest.getAbsolutePath() + File.separator + pathRelative.toString() );
                        if( !destPath.exists() || !destPath.isDirectory() ){
                            destPath.mkdirs();
                            System.out.println("Creating directory: " + pathRelative.toString() );
                        }
                        destFile = new File( destPath.getAbsolutePath() + File.separator + src.getName() );
                        
                        copyFile( src, destFile );
                    }
                    logger.logCopy( current, total, src.getAbsolutePath(), dest.getAbsolutePath() );
                }catch( IOException e ){
                    logger.logError( current, total, src.getAbsolutePath(), e.getMessage() );
                }catch( MigratorException e ){
                    logger.logSkip( current, total, src.getAbsolutePath(), e.getMessage() );
                }
            }else if(!src.exists()){
                System.out.println( src.getAbsolutePath() );
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
    
    public static String commonPath(String[] paths){
        String commonPath = "";
        String[][] directories = new String[paths.length][];
        
        // Split all paths into a list of directory arrays
        for( int i = 0; i < paths.length; ++i ){
            directories[i] = paths[i].split( Pattern.quote( System.getProperty("file.separator") ) );
        }
        
        // Search through directory pieces to find matches
        for( int i = 0; i < directories[0].length; ++i){
            String thisFolder = directories[0][i];
            boolean allMatched = true;
            
            for( int j = 1; j < directories.length && allMatched; j++ ){
                // If there are no more directories, stop looking
                if(directories[j].length < i){
                    allMatched = false;
                    break;
                }
                //check if it matched
                allMatched &= directories[j][i].equals(thisFolder); 
            }
            
            //if they all matched this directory name
            if(allMatched){
                commonPath += thisFolder + File.separator; //add it to the answer
            }else{
                break;
            }
        }
        return commonPath;
    }
    
    public static String commonPath(File[] files){
        String[] paths = new String[files.length];
        for( int i = 0; i < paths.length; ++i ){
            paths[i] = files[i].getAbsolutePath();
        }
        return commonPath(paths);
    }

    
}
