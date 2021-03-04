/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordstrength;

/**
 *
 * @author vince
 */
public class StrengthChecker {
    // member variables
    private int minLength = 8;
    private int maxLength = minLength * 2;
    
    // CONSTANTS
    private final int MIN_DEFAULT = 8;
    private final int MAX_DEFAULT = 20;
    private final String SPEC_CHAR = ".*[$%^&*@#!].*";
    private final String SPACE = ".*\\s.*";
    private final String DIGIT = ".*\\d.*";
    private final String ALPHA = ".*[a-zA-Z].*";
    private final String UPPER = ".*[A-Z].*";
    private final String LOWER = ".*[a-z].*";
    
    public String verifyPassword(String pswd){
        // by default, password do not meet requirement
        StringBuilder errorMessage = new StringBuilder();
        
        // check for special char
        errorMessage.append(checkSpecialChar(pswd));
        // check length
        errorMessage.append(checkLength(pswd));
        // check space
        errorMessage.append(checkSpace(pswd));
        // check digit
        errorMessage.append(checkDigit(pswd));
        //check alpha
        errorMessage.append(checkAlpha(pswd));
        
        return errorMessage.toString();
    }// close verifyPassword
    
    /**
     * 
     * @param pswd
     * @return null if special char found, error message if not
     */
    private String checkSpecialChar(String pswd){
        if(pswd.matches(SPEC_CHAR)){
            return "";
        }// close if
        
        return "no special characters found\n";
    }// close checkSpecialChar
    
    /**
     * 
     * @param pswd
     * @return null if digit found, error message if not
     */
    private String checkDigit(String pswd){
        if(pswd.matches(DIGIT)){
            return "";
        }// close if
        
        return "no digit found\n";
    }// close check digit
    
    /**
     * 
     * @param pswd
     * @return error message if space found, null if not
     */
    private String checkSpace(String pswd){
        if(pswd.matches(SPACE)){
            return "no spaces allowed\n";
        }// close if
        
        return "";
    }// close checkSpace
    
    /**
     * 
     * @param pswd
     * @return null if lowercase and capital letter found, error if not 
     */
    private String checkAlpha(String pswd){
        // check for any letter
        if(pswd.matches(ALPHA)){
            
            // check for capital letter
            if(!pswd.matches(UPPER)){
                return "requires at least 1 capital letter\n";
            }//close nested if
            
            // check for lowercase letter
            if(!pswd.matches(LOWER)){
                return "requires at least 1 lowercase letter\n";
            }//close nested if
            
            return "";
        }// close if
        
        return "requires at least 1 capital and 1 lowercase letter\n";
        }// close checkAlhpa}
    
    /**
     * 
     * @param pswd
     * @return null if length requirements met, error message if not
     */
    private String checkLength(String pswd){
        int lgth = pswd.length();
        
        if(lgth < minLength){
            return "password too short\n";
        }// close if
        else if (lgth > maxLength){
            return "password too long \n";
        }// close elseif
        
        return ""; 
        
    }// close checkLength
    
    public void setLength(int min, int max){
        if(min < 1){
            minLength = MIN_DEFAULT;
            maxLength = MAX_DEFAULT;
        }// end if
        
        else {
            // make min minLength
            minLength = min;
        }// end else
        
        // cannot have a max less than min
        if (max < min) { 
            // overwrite max to min * 2
            maxLength = min * 2;
        }// end if
        else {
            // make max maxLength
            maxLength = max;
        }// end else
        
    }// close setLength
    
    public int getMinLength(){
        return minLength;
    }// close getMinLength
    
    public int getMaxLength(){
        return maxLength;
    }// close getMaxLength
    
}
