package com.example.vrs.controllers;

import com.example.vrs.models.Booking;
import com.example.vrs.models.Vehicle;
import com.example.vrs.models.VehicleType;
import com.example.vrs.services.VehicleService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Path("/vehicle")
public class VehicleController {
    VehicleService service = new VehicleService();

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Vehicle registerVehicle(Vehicle vehicle){

        Vehicle result = service.registerVehicle(vehicle);
        return result;
    }

    @GET
    @Path("/locateVehicle")
    @Produces(MediaType.APPLICATION_JSON)
    public Booking locateVehicle(@QueryParam("regNo") String regNo){
        return service.locateVehicle(regNo);
    }

    @GET
    @Path("/allVehicles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vehicle> vehicleList(){
        return service.allVehicles();
    }

    @GET
    @Path("/availableVehicles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vehicle> availableVehiclesList(@QueryParam("type") VehicleType vehicleType,
                                    @QueryParam("startTime") String startTime,
                                    @QueryParam("endTime") String endTime ) throws Exception{
        LocalDateTime st = LocalDateTime.parse(startTime);
        LocalDateTime et = LocalDateTime.parse(endTime);
        return service.availableVehicles(vehicleType,st,et);
    }

    @GET
    @Path("/rentedVehicles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vehicle> rentedVehiclesList( ){
        return service.rentedVehicles();
    }

    @POST
    @Path("/booking")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Booking createBooking(Booking booking) throws Exception{

        Booking result = service.createBooking(booking);
        return result;
    }

    @GET
    @Path("/booking")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Booking getBookingDetails(@QueryParam("id") int id) throws Exception{

        Booking result = service.getBookingDetails(id);
        return result;
    }

    @PUT
    @Path("/booking/{id}/return")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Booking returnBooking(@PathParam("id") int id) throws Exception{

        Booking result = service.returnBooking(id);
        return result;
    }

    @GET
    @Path("/booking/{id}/cost")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Booking getBookingCost(@PathParam("id") int id) throws Exception{

        Booking result = service.getBookingCost(id);
        return result;
    }

}


