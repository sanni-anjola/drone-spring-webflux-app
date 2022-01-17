package io.anjola.dronespringwebfluxapp.service;

import io.anjola.dronespringwebfluxapp.model.Drone;
import io.anjola.dronespringwebfluxapp.model.Medication;
import io.anjola.dronespringwebfluxapp.payload.CustomResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface DroneService {
    Mono<Drone> registerDrone(Mono<Drone> dronePayload);
    Mono<CustomResponse> loadDroneWithMedicationItem(Mono<Long> droneIdParam, Mono<Medication> medicationPayload);
    Flux<Medication> getMedicationItemsForADrone(Mono<Long> droneIdParam);
}
