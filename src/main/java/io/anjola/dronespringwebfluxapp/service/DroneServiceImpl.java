package io.anjola.dronespringwebfluxapp.service;

import com.sun.xml.bind.v2.TODO;
import io.anjola.dronespringwebfluxapp.enums.State;
import io.anjola.dronespringwebfluxapp.exception.ApplicationException;
import io.anjola.dronespringwebfluxapp.model.Drone;
import io.anjola.dronespringwebfluxapp.model.Medication;
import io.anjola.dronespringwebfluxapp.payload.CustomResponse;
import io.anjola.dronespringwebfluxapp.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService{
    private final DroneRepository droneRepository;
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
                    double totalWeight = drone.getMedications().stream().mapToDouble(Medication::getWeight).sum();
                    return medicationPayload.filter(medication ->
                            drone.getWeight() < totalWeight + medication.getWeight())
                            .switchIfEmpty(Mono.error(new ApplicationException("Drone cannot be loaded with medications over " + drone.getWeight() + "gr")))
                            .map(medication -> drone.getMedications().add(medication))
                            .thenReturn(drone);
                })
                .map(droneRepository::save)
                .map(savedDrone -> new CustomResponse("Medication added successfully", true));
    }


//    TODO
//    Register Drone
//    Load Drone with Medication
//    Get All Medication Items for a given drone
//    Check Available drones for loading
//    Check drone battery level


}
