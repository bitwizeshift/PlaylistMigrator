# PlaylistMigrator

This is a small toy project I wrote to help migrate playlists stored in `.m3u` format (soon to support more formats). 
It copies all files in a given playlist over to a specified output. This can be done in one of two different ways:

## Features

- [x] Two modes of migrating files
  - [x] Flattens files to the same directory
  - [x] Preserves subdirectory structures
- [x] Support for `.pls` playlist
- [x] Support for `.m3u` playlist

### 1. Preserve the directory structure. 

This method attempts to discover the nearest ancestor of all the songs in a playlist, and -- if found -- will 
create directories corresponding to the path to each song so that each song exists in their respective directories.

### 2. Flatten the directory structure

This method copies all files into the same directory, regardless of what directory they originated in. This is the
simpler option, however can cause collisions if two songs share the same name.

## How to Build

To build this program, just run `ant build` (or just `ant`, as build is default).
The built jar can then be found in `/dist/`, and the libraries involved will be in `/dist/lib`.

To clean up everything, run either `ant clean` for a quick clean, or `ant clean-all` to clear the ivy cache.

## How to Use

This program is (currently) command-line only. To run it, simply run `java -jar PlaylistMigrator.jar <playlist file> <destination> [options...]`

For a full list of options, pass either `-h` or `--help`.

## <a name="license"></a>License

<img align="right" src="http://opensource.org/trademarks/opensource/OSI-Approved-License-100x137.png">

This project is licensed under the [MIT License](http://opensource.org/licenses/MIT):

Copyright &copy; 2016 Matthew Rodusek

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.