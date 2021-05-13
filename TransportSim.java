/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transportFam;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * programs models transportation safety simulates average cost and casualty
 * outcomes of a certain number of passengers
 *
 * @author vince
 */
public class TransportSim {

    public static void main(String[] args) {
		
        TransportVehicle vehicle1 = new TransportVehicle();

        TransportVehicle vehicle2 = new CommercialAirline();

        CommercialAirline southjet23 = new CommercialAirline();
        southjet23.setAverageSpeed(380);
        southjet23.setAltitude(28000);
        southjet23.setCallSign("southjet23");
        southjet23.setHoursOfOperation(76);
        southjet23.setEngineCycles(890);

        double timePGHtoATL = computeTravelTime(southjet23, 526);
        System.out.println("Travel time of plane from PGH to Atlanta: " + timePGHtoATL + " hours");

        PassengerTrain amtrak23 = new PassengerTrain();

        amtrak23.setPassengerCarCount(23);
        amtrak23.setAverageSpeed(375);
        amtrak23.setPassengerCount(472);
        amtrak23.setCallSign("Amtrak23");

        if(amtrak23 instanceof Derailable){
        attemptDerailment((Derailable) amtrak23);
        }    

        double trainTime = computeTravelTime(amtrak23, 630);
        System.out.println("Travel time of a train from pitt to NYC: " + trainTime + " hours");

        Automobile DodgeCharger = new Automobile();
        DodgeCharger.setAverageSpeed(100);
        DodgeCharger.setCarSeats(2);
        DodgeCharger.setCallSign("DodgeCharger");
        DodgeCharger.setPassengerCount(2);
       
        Bus BigBird = new Bus();
        BigBird.setAverageSpeed(65);
        BigBird.setMaxPass(30);
        BigBird.setNumOfExits(2);
        BigBird.setNumOfWheels(4);
        BigBird.setCallSign("BigBird");
        BigBird.setPassengerCount(8);
        
        Van MovingVan = new Van();
        MovingVan.setCallSign("MovingVan");
        MovingVan.setFoldableSeats(3);
        MovingVan.setMoreSpace(12);
        MovingVan.setSlidingDoors(2);
        MovingVan.setAverageSpeed(85);
        MovingVan.setPassengerCount(2);
        
        Jet RedWing = new  Jet();
        RedWing.setCallSign("RedWing");
        RedWing.setAltitude(9000);
        RedWing.setAverageSpeed(678.98);
        RedWing.setCrewCount(2);
        RedWing.setGallonsOfFuel(40.5);
        
        DuckyBoat Rock = new DuckyBoat();
        Rock.setAverageSpeed(35);
        Rock.setCallSign("Rock");
        Rock.setFloatableWater(10.0);
        Rock.setNumbenches(6);
        Rock.setnumPedals(3);
        Rock.setspeedInWater(15.0);
        Rock.setMilesOnEngine(350000);
       
        
        
        
        
            
        
        
        

        double carTime = computeTravelTime(DodgeCharger, 630);
        System.out.println("Travel time for a car to go from Pitt to NYC: " + carTime + " hours.");

        List<TransportVehicle> vehicleList = new LinkedList<>();

        vehicleList.add(amtrak23);
        vehicleList.add(southjet23);
        vehicleList.add(DodgeCharger);

        int pTotal = computeTotalPassCount(vehicleList);
        System.out.println("Total number of Passangers: " + pTotal);
    }

    public static int computeTotalPassCount(List<TransportVehicle> vList) {
        int passTotal = 0;
		
        if (vList != null && !vList.isEmpty()) {
            for (TransportVehicle v : vList) {
                System.out.println("***************************************");
                System.out.println("Examining Vehicle: " + v.getCallSign());
                System.out.println("Passenger Count: " + v.getPassengerCount());
                passTotal = passTotal + v.getPassengerCount();
            } 
        }

        return passTotal;

    }

    public static void attemptDerailment(Derailable dr) {
        System.out.println("Derailment Check: " + dr.canBeDerailed());
    }

    public static double computeTravelTime(TransportVehicle vehicle, double distance) {

        if (vehicle instanceof Racecar) {
            System.out.println("Ooh, I was passed a racecar subclass.");

            Racecar racer = (Racecar) vehicle;
            System.out.println("WheelBase measurement: " + racer.getWheelBaseInches());
        } else if (vehicle instanceof CommercialAirline) {
            System.out.println("Ahh yes I was passed a CommercialAirliner flying is the way we wanna go.");

            CommercialAirline plane = (CommercialAirline) vehicle;
            System.out.println("Planes's altitude ");
        }
        return distance / vehicle.getAverageSpeed();
    }

}
