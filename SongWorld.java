/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrays;

import java.util.Scanner;

/**
 * client class of LyricMachine
 * @author vince
 */
public class SongWorld {
    public static void main(String[] args) {
        //load data into lyric machine
        LyricMachine lm = new LyricMachine();
        lm.initializeArray(8, 7);
        lm.readInLyrics("lyrics.txt");
        
        menu(lm);
        
    } // close main
    
    public static void menu(LyricMachine lm){
        int menuSelection = 0;
        
        // loops through menu until user exits
        while(true){
            System.out.println("\nSONG EDITOR MENU");
            System.out.println("----------------");
        
            System.out.println("1. display lyrics");
            System.out.println("2. edit lyrics");
            System.out.println("3. exit");
            
            menuSelection = getUserSelection(1, 3);
            
            switch(menuSelection){
                case 1: 
                    lm.displayLyrics();
                    break;
                case 2:
                    editLyrics(lm);
                case 3:
                    System.exit(0); // terminate JVM
                    break;
            }
            
        }// close while
    }// close menu
    
    public static int getUserSelection(int min, int max){
        int menuSelection = 0;
        
        // instantiate scanner
        Scanner input = new Scanner(System.in);
        
        // turns false once user input is verified to work
        boolean integerFlag = true;
            
        // loops unitl user enters valid input
        while(integerFlag){
            System.out.print("\nselection (int): ");
            
            // holds user input as string
            String inputHolder = input.next();
            
            // catches if user inputs non int
            try{
                menuSelection = Integer.parseInt(inputHolder);
                integerFlag = false;
            } // end try
            catch (NumberFormatException ne){
                System.out.println("not an integer... try again");
                integerFlag = true;
            }// end catch
            
            // catch if user input out of range
            if(!integerFlag && (menuSelection < min || menuSelection > max)){
                System.out.println("out of range... try again");
                integerFlag = true;
            }
                
        }// end check valid integer loop
        
        return(menuSelection);
        
    }// close getUserSelection
    
    public static void editLyrics(LyricMachine lm){
        System.out.println("\nselect verse 1-" + lm.lyrics[0].length);
        
        int verse = getUserSelection(1, lm.lyrics[0].length);
        
        int lines = lm.displayVerse(verse);
        
        // line defaults to 1 in case verse only has 1 line
        int line = 1;
        
        // ask user for line #
        if(lines > 1){
            System.out.println("\nselect line 1-" + lines);
            line = getUserSelection(1, lines);
        }// end if
        
        // instantiate scanner
        Scanner input = new Scanner(System.in);
        
        // get new line from user
        System.out.print("new line lyric: ");
        String newLine = input.nextLine();
        
        // put new line in correct location
        lm.lyrics[line - 1][verse - 1] = newLine;
        
        System.out.println("VERSE " + verse + " LINE " + line + " HAS BEEN SUCCESFULLY CHANGED");
         
        // go back to menu
        menu(lm);
    }// close editLyrics
            
} //close class
