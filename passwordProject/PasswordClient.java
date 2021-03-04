/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordstrength;

import java.util.Scanner;

/**
 *
 * @author vince
 */
public class PasswordClient {
    
    public static void main(String[] args){
        // create instance of password checking machine
        StrengthChecker checker = new StrengthChecker();
        
        // instantiate scanner
        Scanner input = new Scanner(System.in);
        
        // client password defaults to fail
        boolean fail = true;
        System.out.println("PASSWORD STRENGTH CHECKER");
        System.out.println("-------------------------");
        System.out.println("requirements:");
        System.out.println("1. min length of " + checker.getMinLength() + " characters");
        System.out.println("2. max length of " + checker.getMaxLength() + " characters");
        System.out.println("3. contains at least 1 special character: $%^&*@#!");
        System.out.println("4. contains at least 1 digit");
        System.out.println("5. contains at least 1 lowercase and 1 capital letter");
        System.out.println("6. contains no spaces");
        
        while(fail){
            System.out.println("\nenter password: ");
            
            String pswd = input.nextLine();
            String errors = checker.verifyPassword(pswd);
            
            if(errors == ""){
                fail = false;
            }
            else{
                System.out.println("\n" + errors);
            }
        }// close while
        
        System.out.println("password meets all requirements");
    }// close main
    
}
