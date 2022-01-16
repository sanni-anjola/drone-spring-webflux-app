package io.anjola.dronespringwebfluxapp.service;

import io.anjola.dronespringwebfluxapp.model.Drone;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface DroneService {
    Mono<Drone> registerDrone(Mono<Drone> dronePayload);
}
