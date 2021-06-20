package com.example.vrs.services;

import com.example.vrs.models.Booking;
import com.example.vrs.models.Vehicle;
import com.example.vrs.models.VehicleType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class VehicleService {
    Random random = new Random();
    List<Vehicle> vehicleList = new ArrayList<>();
    Map<String,Vehicle> vehicleMap = new HashMap<>(); //vehicle registation no --> vehicle

    Map<Integer,Vehicle> parkingLot = new HashMap<>(); //parking No --> vehicle // later on asked to get the bike parked on this parking no

    Map<String ,Booking> userBookingMap = new HashMap<>(); //user emailId --> booking
    Map<Integer,Booking> bookingMap = new HashMap<>(); // booking id --> booking
    Map<String,Booking> vehicleBookingMap = new HashMap<>(); // vehicle regis no -- > booking

    Map<VehicleType,Integer> vehicleTypeCost = Map.of(VehicleType.BIKE,10, VehicleType.CAR,20,VehicleType.JEEP,30,VehicleType.VAN,40);
    Set<Integer> barCodes = new HashSet<>();

    public Vehicle registerVehicle(Vehicle vehicle){
        vehicle.setAvailable(true);
        vehicleList.add(vehicle);
        int parkingLotNo = getRandomId(100,parkingLot.keySet());
        vehicle.setParkingNo(parkingLotNo);
        int barCode = getRandomId(100,barCodes);
        vehicle.setBarCode(String.valueOf(barCode));
        parkingLot.put(parkingLotNo , vehicle);
        vehicleMap.put(vehicle.getRegNo(),vehicle);
        return vehicle;
    }

    public List<Vehicle> allVehicles(){
        return vehicleList;
    }

    public List<Vehicle> availableVehicles(VehicleType vehicleType , LocalDateTime startTime, LocalDateTime endTime) throws Exception{
        List<Vehicle> allAvailVehicles = new ArrayList<>();
        for(Vehicle vehicle : vehicleList){
            if(vehicle.getVehicleType()==vehicleType){
                if(vehicleBookingMap.containsKey(vehicle.getRegNo())){
                    Booking booking = vehicleBookingMap.get(vehicle.getRegNo());
                    if( endTime.isBefore(booking.getStartTime())  || startTime.isAfter(booking.getEndTime())){
                        Vehicle v = (Vehicle) vehicle.clone();
                        v.setAvailable(true);
                        allAvailVehicles.add(v);
                    }
                } else {
                    allAvailVehicles.add(vehicle);
                }
            }

        }
        return allAvailVehicles;
    }

    public List<Vehicle> rentedVehicles(){
        List<Vehicle> rentedVehicles = new ArrayList<>();
        for(Vehicle vehicle : vehicleList){
            if( !vehicle.isAvailable()){
                rentedVehicles.add(vehicle);
            }
        }
        return rentedVehicles;
    }

    private  Vehicle getAvailableVehicle(VehicleType vehicleType ) throws Exception{
        for(Vehicle vehicle : vehicleList){
            if(vehicle.getVehicleType() == vehicleType && vehicle.isAvailable()){
                return vehicle;
            }
        }
        throw new Exception("vehicle not availabel of type "+vehicleType); // If not found , throw exception
    }

    public Booking createBooking(Booking booking) throws Exception{
        int id = getRandomId(100,bookingMap.keySet());
        booking.setId(id);
        Vehicle vehicle = getAvailableVehicle(booking.getVehicleType());
        vehicle.setAvailable(false);
        booking.setVehicle(vehicle);
        userBookingMap.put(booking.getEmailId(),booking);
        bookingMap.put(id,booking);
        parkingLot.remove(vehicle.getParkingNo());
        vehicleBookingMap.put(vehicle.getRegNo(),booking);
        return booking;
    }

    public Booking locateVehicle(String regNo){
        if(vehicleBookingMap.containsKey(regNo)){
            return vehicleBookingMap.get(regNo);
        }else{
            Booking b = new Booking();
            b.setVehicle(vehicleMap.get(regNo));
            return b;
        }
    }

    public Booking getBookingDetails(int id) throws Exception{
        if(bookingMap.containsKey(id)){
            return bookingMap.get(id);
        }
        throw new Exception("booking details not found for id : "+id);
    }

    public Booking returnBooking(int id) throws Exception{
        // delete from userBookingMap bookingMap vehicleBookingMap
        // set vehicle available status true
        // assign parking lot

        if(bookingMap.containsKey(id)){
            Booking booking = bookingMap.get(id);
            bookingMap.remove(id);
            userBookingMap.remove(booking.getEmailId());
            Vehicle vehicle = booking.getVehicle();
            vehicleBookingMap.remove(vehicle.getRegNo());
            vehicle.setAvailable(true);
            int parkingLotNo = getRandomId(100,parkingLot.keySet());
            vehicle.setParkingNo(parkingLotNo);
            return booking;
        }
        throw new Exception("booking details not found for id : "+id);


    }

    public Booking getBookingCost(int id) throws Exception{
        if(bookingMap.containsKey(id)){
            Booking booking = bookingMap.get(id);
            LocalDateTime tempDate = LocalDateTime.from(booking.getStartTime());
            long hours = tempDate.until( booking.getEndTime(), ChronoUnit.HOURS );
            long cost = vehicleTypeCost.get(booking.getVehicleType()) * hours;
            booking.setCost((int)cost);
            return booking;
        }
        throw new Exception("booking details not found for id : "+id);
    }

    private int getRandomId(int bound,Set<Integer> keys){
        int value = random.nextInt(21) + 1;//random.nextInt(max - min + 1) + min
        do{
            value = random.nextInt(21) + 1;//random.nextInt(max - min + 1) + min
        }while (keys.contains(value));
        return value;
    }
}
