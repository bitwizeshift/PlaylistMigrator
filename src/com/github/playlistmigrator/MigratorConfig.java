package com.github.playlistmigrator;

import java.util.List;

import joptsimple.OptionSet;

public class MigratorConfig {
	
	//------------------------------------------------------------------------
	// Members
	//------------------------------------------------------------------------
	
	private final boolean help;
	private final boolean flatten;
	private final boolean download;
	private final boolean verbose;
	private final boolean quiet;
	private final String  source;
	private final String  destination;
	
	//------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------

	/**
	 * Parses command-line parameters into a configuration
	 * 
	 * @param options OptionSet containing command-line parameters
	 * @throws MigratorConfigException
	 */
	public MigratorConfig( final OptionSet options ) throws MigratorConfigException{
	    this.help       = options.has("help");
	    this.flatten    = options.has("flatten");
	    this.download   = options.has("download-streams");
	    this.verbose    = options.has("verbose");
	    this.quiet      = options.has("quiet");
	    final List<String> args = (List<String>) options.nonOptionArguments();
	    
	    this.source = ((args.size() > 0) ? args.get(0) : null);
	    this.destination = ((args.size() > 1) ? args.get(1) : null);
	    
	    
	    if( args.size() > 2 ) throw new MigratorConfigException("Too many arguments");
	    if( !this.help && (args.size() < 2) )
	    	throw new MigratorConfigException("Too few arguments");
	    if( this.verbose && this.quiet )
	    	throw new MigratorConfigException("Conflicting arguments '--verbose' and '--quiet'");
	    
	}
	
	public boolean requestsHelp(){
		return this.help;
	}
	public boolean forcesFlatten(){
		return this.flatten;
	}
	public boolean allowsDownloads(){
		return this.download;
	}
	public boolean isVerbose(){
		return this.verbose;
	}
	public boolean isQuiet(){
		return this.quiet;
	}
	public String getSource(){
		return this.source;
	}
	public String getDestination(){
		return this.destination;
	}
}
