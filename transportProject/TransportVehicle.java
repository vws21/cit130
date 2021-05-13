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
public class TransportVehicle {
    private String callSign;
    private int passengerCount;
    private double averageSpeed;
    private double costPerPassMile;
    private double safetyCoefficient;
    private boolean inTransit;
    
    private double fuelCap;
    private double currentFuel;
    
    
    public int getPassengerCount(){
        return passengerCount;
    }
    public void setPassengerCount(int cnt){
        if(cnt >= 0){
            passengerCount = cnt;
            
        } else {
            passengerCount = 0;
        }
    }
    
    public double getAverageSpeed(){
        return averageSpeed;
    }
    public void setAverageSpeed(double avgSpeed){
        averageSpeed = avgSpeed;
    }
    
    public double getCostPerPassMile(){
        return costPerPassMile;
    }
    public void setCostPerPassMile(double CPPM){
        this.costPerPassMile = CPPM;
    }
     public double getSafetyCoefficient(){
        return safetyCoefficient;
    }
     public void setSafetyCoefficient(double SC){
         this.safetyCoefficient = SC;
     }
     
     public boolean isInTransit(){
         return inTransit;
     }
     public void setInTransit(boolean InTransit){
         this.inTransit = InTransit;
     }
     
     public double getFuelCap(){
         return fuelCap;
     }
     public void setFuelCap(double FC){
         this.fuelCap = FC;
     }
     
     public double getCurrentFuel(){
         return currentFuel;
     }
     public void setCurrentFuel(double CF){
         this.currentFuel = CF;
     }
     
     public String getCallSign(){
         return callSign;
     }
     public void setCallSign(String CallSign){
         this.callSign = CallSign;
     }
    
    
    
}
    
