/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrays;

import java.io.File; // import file class
import java.io.FileNotFoundException; // handle errors
import java.util.Scanner; // scanner class to read text     

/**
 * object blueprint class for a tool storing and displaying lyrics
 * @author vince
 */
public class LyricMachine {
    String[][] lyrics;
    String songTitle;
    String artist;
    String releaseYear;

    /**
     * Call this method first to create the container in which we'll
     * store our song's lyrics
     * @param maxLinesInVerse
     * @param numVerses 
     */
    public void initializeArray(int maxLinesInVerse, int numVerses){
        lyrics = new String[maxLinesInVerse][numVerses];

    } // close initialize array

    // Method to read in lyrics, one line
    // at a time
    public void readInLyrics(String textFileName){
        
        try{
            // instantiate file
            File lyricText = new File(textFileName);
            // instanitate scanner to read file
            Scanner reader = new Scanner(lyricText);
            
            // title, artist, and year are first 3 lines
            // account for emtpy line after
            songTitle = reader.nextLine();
            artist = reader.nextLine();
            releaseYear = reader.nextLine();
            reader.nextLine();
            
            // counter for line in verse, and verse
            int lineInVerse = 0;
            int verse = 0;
            
            // go through lyrics line by line and put into array
            while(reader.hasNextLine()){
                // get line from lyric text file
                String line = reader.nextLine();
                
                // puts lyric in array if scanner doesnt read empty line
                if(!line.isEmpty()){
                    lyrics[lineInVerse][verse] = line;
                    lineInVerse++;
                }// close if
                
                // empty line signals new verse
                // reset line in verse counter
                // incriment verse counter
                else{
                    lineInVerse = 0;
                    verse++;
                }// close else
                
            }// close while
        }// close try
        
        // catches if file not found
        catch(FileNotFoundException e){
            System.out.println("error... file not found");
            e.printStackTrace();
        }// close catch
        
    } // close readInLyrics
    
    public int displayVerse(int verse){
        System.out.println();
        System.out.println("VERSE: " + verse);
        
        // count number of lines
        int lines = 0;
        
        for(int line = 0; line < lyrics.length; line++){
            String lineText = lyrics[line][verse - 1];
            
            if(lineText != null)
                {
                    System.out.println("line " + (line+1) + ": " + lineText);
                    //increment lines
                    lines++;
                }// close if
                
                // if value of lineText is null
                // this signals new verse
                // so end line forloop by making loop variable go out of bounds
                else{
                    line = lyrics.length;
                }// close else
            
        }// close forloop
        
        return(lines);
    }// close display verse

    /**
     * display lyrics
     */
    public void displayLyrics(){
        // print song name, artist, and release year
        System.out.println("\nsong: " + songTitle);
        System.out.println("artist: " + artist);
        System.out.println("release year: " + releaseYear + "\n");
        
        
        // print lyrics
        for(int verse = 0; verse < lyrics[0].length; verse++){
            System.out.println("VERSE " + (verse+1));
            for(int line = 0; line < lyrics.length; line++){
                String lineText = lyrics[line][verse];
                
                if(lineText != null)
                {
                    System.out.println("line " + (line+1) + ": " + lineText);
                }//close if
                
                // if value of lineText is null
                // this signals new verse
                // so end line forloop by making loop variable go out of bounds
                else{
                    line = lyrics.length;
                }// close else
                
                // print empty line at end of verse
                if(line >= (lyrics.length - 1)){
                    System.out.println();
                }// close if
                
            }// close line forloop
        }// close verse forloop
    }

} // close class
