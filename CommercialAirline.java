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
public class CommercialAirline extends TransportVehicle{
    private int crewCount;
    private double altitude;
    private double hoursOfOperation;
    private int engineCycles;
    
    
    final int ENGINE_CYCLE_CUTOFF = 34000;
    final int HOURS_CUTOFF = 34000;
    
    public int getCrewCount(){
        return crewCount; 
    }
    public void setCrewCount(int cc){
        crewCount = cc;
    }
	
    public double getAltitude(){
        return altitude;
    }
    public void setAltitude(int alt){
        altitude = alt;
    }
    
    public double getHoursOfOperation(){
        return hoursOfOperation;
    }
    public void setHoursOfOperation(double hoursOfOp){
        hoursOfOperation = hoursOfOp;
    }
    
    public int getEngineCycles(){
        return engineCycles;
    }
    public void setEngineCycles(int ec){
        engineCycles = ec;
    }
    
            
}
