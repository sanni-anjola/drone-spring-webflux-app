package io.anjola.dronespringwebfluxapp.service;

import io.anjola.dronespringwebfluxapp.enums.State;
import io.anjola.dronespringwebfluxapp.exception.ApplicationException;
import io.anjola.dronespringwebfluxapp.model.Drone;
import io.anjola.dronespringwebfluxapp.model.Medication;
import io.anjola.dronespringwebfluxapp.payload.CustomResponse;
import io.anjola.dronespringwebfluxapp.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
@Slf4j
public class DroneServiceImpl implements DroneService{
    private final DroneRepository droneRepository;
    private final Scheduler scheduler;

    public DroneServiceImpl(DroneRepository droneRepository,@Qualifier("jdbcScheduler") Scheduler scheduler) {
        this.droneRepository = droneRepository;
        this.scheduler = scheduler;
    }

    @Override
    public Mono<Drone> registerDrone(Mono<Drone> dronePayload) {
        return dronePayload.map(droneRepository::save);
    }

    @Override
    public Mono<CustomResponse> loadDroneWithMedicationItem(Mono<Long> droneIdParam, Mono<Medication> medicationPayload) {
        return droneIdParam.map(droneRepository::findById)
                .flatMap(optionalDrone -> optionalDrone
                        .map(Mono::just)
                        .orElseGet(() -> Mono.error(new ApplicationException("Done not found"))))
                .filter(drone -> drone.getState() != State.IDLE)
                .switchIfEmpty(Mono.error(new ApplicationException("Drone not in idle state cannot be loaded")))
                .filter(drone -> drone.getBattery() < 25)
                .switchIfEmpty(Mono.error(new ApplicationException("Drone with battery level lower than 25% cannot be loaded")))
                .flatMap(drone -> {
                    double totalWeight = getDroneMedicationWeight(drone);
                    return medicationPayload.filter(medication ->
                            drone.getWeight() < totalWeight + medication.getWeight())
                            .switchIfEmpty(Mono.error(new ApplicationException("Drone cannot be loaded with medications over " + drone.getWeight() + "gr")))
                            .map(medication -> drone.getMedications().add(medication))
                            .thenReturn(drone);
                })
                .map(droneRepository::save)
                .map(savedDrone -> new CustomResponse("Medication added successfully", true));
    }

    @Override
    public Flux<Medication> getMedicationItemsForADrone(Mono<Long> droneIdParam) {
        return droneIdParam.map(droneRepository::findById)
                .flatMap(optionalDrone ->
                        optionalDrone
                                .map(Mono::just)
                                .orElseGet(() -> Mono.error(new ApplicationException("Drone not found")))
                )
                .flatMap(drone -> Mono.just(drone.getMedications()))
                .flatMapIterable(medItem -> medItem);
    }

    @Override
    public Flux<Drone> getAllDrones() {
        return Mono.fromCallable(droneRepository::findAll)
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(scheduler);
    }

    @Override
    public Flux<Drone> getAvailableDrones() {
        return getAllDrones()
                .filter(drone -> drone.getState() == State.IDLE &&
                        drone.getBattery() >= 25 &&
                        getDroneMedicationWeight(drone) < drone.getWeight());

    }

    private double getDroneMedicationWeight(Drone drone) {
        return drone.getMedications().stream().mapToDouble(Medication::getWeight).sum();
    }


//    TODO
//    Get All Medication Items for a given drone
//    Check Available drones for loading
//    Check drone battery level


}
