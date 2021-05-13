/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transportFam;

/**
 *
 * @author vince
 */
public class Jet extends CommercialAirline{
    private int Speed;
    private int Seats;
    private int WeaponCap;
    private double GallonsOfFuel;
    
    public int getSpeed(){
        return Speed;
    }
    public void setSpeed(int S){
        Speed = S;
    }
    
    public int getSeats(){
        return Seats;
    }
    public void setSeats(int ST){
        Seats = ST;
    }
    
    public int getWeaponCap(){
        return WeaponCap;
    }
    public void setWeaponCap(int WC){
        WeaponCap = WC;
    }
    
    public double getGallonsOfFuel(){
        return GallonsOfFuel;
    }
    public void setGallonsOfFuel(double GOF){
        GallonsOfFuel = GOF;
    }
    
}
