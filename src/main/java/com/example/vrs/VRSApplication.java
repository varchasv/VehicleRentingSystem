package com.example.vrs;

import com.example.vrs.controllers.VehicleController;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class VRSApplication extends Application<VRSConfiguration> {
    public static void main(String[] args) throws Exception{
        VRSApplication app = new VRSApplication();
        app.run(args);
    }

    @Override
    public void run(VRSConfiguration vrsConfiguration, Environment environment) throws Exception {
        VehicleController vehicleController = new VehicleController();
        environment.jersey().register(vehicleController);

    }
}

// To run add prog agruments as server
