package io.anjola.dronespringwebfluxapp.config;

import io.anjola.dronespringwebfluxapp.enums.Model;
import io.anjola.dronespringwebfluxapp.enums.State;
import io.anjola.dronespringwebfluxapp.model.Drone;
import io.anjola.dronespringwebfluxapp.model.Medication;
import io.anjola.dronespringwebfluxapp.repository.DroneRepository;
import io.anjola.dronespringwebfluxapp.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@RequiredArgsConstructor
public class InitializeDatabase {
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    @PostConstruct
    public void initializeData(){
        droneRepository.deleteAll();
        medicationRepository.deleteAll();

        droneRepository.save(Drone.builder()
                .id(1L)
                .serialNumber("drone111")
                .model(Model.LIGHTWEIGHT)
                .battery(100.00)
                .weight(200.00)
                .state(State.IDLE)
                .build());

        droneRepository.save(Drone.builder()
                .id(2L)
                .serialNumber("drone112")
                .model(Model.CRUISERWEIGHT)
                .battery(100.00)
                .weight(500.00)
                .state(State.IDLE)
                .build());

        droneRepository.save(Drone.builder()
                .id(3L)
                .serialNumber("drone113")
                .model(Model.HEAVYWEIGHT)
                .battery(100.00)
                .weight(400.00)
                .state(State.IDLE)
                .build());

        droneRepository.save(Drone.builder()
                .id(4L)
                .serialNumber("drone114")
                .model(Model.MIDDLEWEIGHT)
                .battery(100.00)
                .weight(300.00)
                .state(State.LOADING)
                .build());

        droneRepository.save(Drone.builder()
                .id(5L)
                .serialNumber("drone115")
                .model(Model.LIGHTWEIGHT)
                .battery(100.00)
                .weight(200.00)
                .state(State.DELIVERING)
                .build());

        medicationRepository.save(Medication
                .builder()
                .id(1L)
                .name("Med101")
                .weight(30.0)
                .code("MED101")
                .image("image1")
                .build());

        medicationRepository.save(Medication
                .builder()
                .id(2L)
                .name("Med102")
                .weight(70.0)
                .code("MED102")
                .image("image2")
                .build());

        medicationRepository.save(Medication
                .builder()
                .id(3L)
                .name("Med103")
                .weight(130.0)
                .code("MED103")
                .image("image3")
                .build());

        medicationRepository.save(Medication
                .builder()
                .id(4L)
                .name("Med104")
                .weight(200.0)
                .code("MED104")
                .image("image4")
                .build());

        medicationRepository.save(Medication
                .builder()
                .id(5L)
                .name("Med105")
                .weight(50.0)
                .code("MED105")
                .image("image5")
                .build());

    }
}
