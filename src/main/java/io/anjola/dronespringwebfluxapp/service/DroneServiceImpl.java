package io.anjola.dronespringwebfluxapp.service;

import com.sun.xml.bind.v2.TODO;
import io.anjola.dronespringwebfluxapp.model.Drone;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DroneServiceImpl implements DroneService{
    @Override
    public Mono<Drone> registerDrone(Mono<Drone> dronePayload) {
        return null;
    }

//    TODO
//    Register Drone
//    Load Drone with Medication
//    Get All Medication Items for a given drone
//    Check Available drones for loading
//    Check drone battery level


}
