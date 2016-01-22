# PlaylistMigrator

## Introduction

This is a small toy project I wrote to help migrate playlists stored in `.m3u` format (soon to support more formats). 
It copies all files in a given playlist over to a specified output. This can be done in one of two different ways:

####1. Preserve the directory structure. 

This method attempts to discover the nearest ancestor of all the songs in a playlist, and -- if found -- will 
create directories corresponding to the path to each song so that each song exists in their respective directories.

####2. Flatten the directory structure

This method copies all files into the same directory, regardless of what directory they originated in. This is the
simpler option, however can cause collisions if two songs share the same name.

## Build Instructions

To build this program, just run `ant build` (or just `ant`, as build is default).
The built jar can then be found in `/dist/`, and the libraries involved will be in `/dist/lib`.

To clean up everything, run either `ant clean` for a quick clean, or `ant clean-all` to clear the ivy cache.

## Use

This program is (currently) command-line only. To run it, simply run `java -jar PlaylistMigrator.jar <playlist file> <destination> [options...]`

For a full list of options, pass either `-h` or `--help`.