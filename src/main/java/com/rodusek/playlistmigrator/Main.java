package com.rodusek.playlistmigrator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.rodusek.playlistmigrator.loggers.MigratorLogger;
import com.rodusek.playlistmigrator.loggers.NormalLogger;
import com.rodusek.playlistmigrator.loggers.NullLogger;
import com.rodusek.playlistmigrator.loggers.VerboseLogger;
import com.rodusek.playlistmigrator.parsers.ParserFactory;
import com.rodusek.playlistmigrator.parsers.PlaylistParser;
import joptsimple.OptionParser;
import joptsimple.OptionSet;


public class Main {
    
    
    //------------------------------------------------------------------------
    // Static constants
    //------------------------------------------------------------------------
    
    /*** Parser for optional parameters */
    private static final OptionParser optparser;
    static {
        optparser = new OptionParser();
        
        optparser.acceptsAll(Arrays.asList("help", "h"), "Display the help message");
        optparser.acceptsAll(Arrays.asList("flatten","f"), "Copies all files to a single directory");
        optparser.acceptsAll(Arrays.asList("download-streams","d"), "Downloads internet streams stored in playlist (skips on default)");
        optparser.acceptsAll(Arrays.asList("verbose","v"), "Verbose logging of actions");
        optparser.acceptsAll(Arrays.asList("quiet","q"), "Opts out of logging");
    }
    
    //------------------------------------------------------------------------
    // Entry Point
    //------------------------------------------------------------------------
    
    /**
     * Main 
     * 
     * @param args
     * @throws IOException 
     */
    public static void main( String[] args ) throws IOException {
        OptionSet options = parseOptions( args );
        PlaylistParser parser = null;
        Migrator       migrator = null;
        MigratorLogger logger = null;
        File[]         files = null;

        try{
            MigratorConfig config = new MigratorConfig( options );
            
            if( config.requestsHelp() ){
                printUsage();
                return;
            }
            File source = new File(config.getSource());
            File dest   = new File(config.getDestination());
            
            if( !source.isFile() ){
                System.out.println("Error: Playlist '" + source + "' is not a file.");
                return;
            }
            // If directory doesn't exist, create it
            if( !dest.isDirectory() ){
                dest.mkdirs();
            }
            
            // Select the correct logging method
            if( config.isQuiet() ){
                logger = new NullLogger();
            }else if(config.isVerbose() ){
                logger = new VerboseLogger();
            }else{
                logger = new NormalLogger();
            }
            
            parser = ParserFactory.getParser( source );
            files = parser.parse();
            
            migrator = new Migrator( files, dest, config, logger );
            migrator.migrate();
            
        }catch( MigratorConfigException e ){
            System.out.println(e.getMessage());
            printUsage();
            return;
        }
        
    }

    //------------------------------------------------------------------------
    // Console Utilities
    //------------------------------------------------------------------------
    

    /**
     * Prints the usage of this program to the user
     */
    public static void printUsage(){
        File jarfile = new File(
                           Main.class.getProtectionDomain().getCodeSource()
                        .getLocation()
                        .getPath()
                       );
        String jarname = jarfile.getName();
        
        System.out.println("Usage: java -jar " + jarname + " <playlist_file> <destination> [options...]");
        try{
            optparser.printHelpOn(System.out);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @param args
     * @return
     */
    private static OptionSet parseOptions( final String[] args ){
        OptionSet optSet = null;
    
        optSet = optparser.parse(args);

        return optSet;
    }
}
