package io.anjola.dronespringwebfluxapp.controller;

import io.anjola.dronespringwebfluxapp.model.Drone;
import io.anjola.dronespringwebfluxapp.model.Medication;
import io.anjola.dronespringwebfluxapp.payload.CustomResponse;
import io.anjola.dronespringwebfluxapp.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class DroneController {
    private final DroneService droneService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Drone> getAll(){
        return droneService.getAllDrones();
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Drone> registerDrone(@RequestBody Mono<Drone> drone){
        return droneService.registerDrone(drone);
    }

    @PatchMapping(value = "/load-drone/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CustomResponse> loadDrone(@PathVariable Long id, @RequestBody Mono<Medication> medication){
        return droneService.loadDroneWithMedicationItem(Mono.just(id), medication);
    }
    @GetMapping(value = "/check-medication/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Medication> getMedications(@PathVariable Long id){
        return droneService.getMedicationItemsForADrone(Mono.just(id));
    }

    @GetMapping(value = "/available-drones", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Drone> getAvailableDrones(){
        return droneService.getAvailableDrones();
    }

    @GetMapping(value = "/battery-level/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Double> getBatteryLevel(@PathVariable Long id){
        return droneService.getDroneBatteryLevel(Mono.just(id));
    }
}
