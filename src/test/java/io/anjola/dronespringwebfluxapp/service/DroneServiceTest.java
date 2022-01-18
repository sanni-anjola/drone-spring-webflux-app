package io.anjola.dronespringwebfluxapp.service;

import io.anjola.dronespringwebfluxapp.enums.Model;
import io.anjola.dronespringwebfluxapp.enums.State;
import io.anjola.dronespringwebfluxapp.exception.ApplicationException;
import io.anjola.dronespringwebfluxapp.exception.NotFoundException;
import io.anjola.dronespringwebfluxapp.model.Drone;
import io.anjola.dronespringwebfluxapp.model.Medication;
import io.anjola.dronespringwebfluxapp.repository.DroneRepository;
import io.anjola.dronespringwebfluxapp.repository.MedicationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class DroneServiceTest {

    @Autowired
    private DroneService droneService;

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    private Drone drone;
    private Medication medication;

    @BeforeEach()
    void initializeDatabase(){

        droneRepository.deleteAll();
        medicationRepository.deleteAll();
        drone = Drone
                .builder()
                .state(State.IDLE)
                .weight(100.0)
                .battery(100.0)
                .serialNumber("drone111")
                .model(Model.LIGHTWEIGHT)
                .build();

        medication = Medication
                .builder()
                .name("Med101")
                .weight(60.0)
                .code("MED101")
                .image("image1")
                .build();

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void registerDrone() {
        assertThat(droneRepository.count()).isEqualTo(0);


        StepVerifier.create(droneService.registerDrone(Mono.just(drone)))
                .expectNextMatches(savedDrone ->
                        savedDrone.getBattery() == 100.0 &&
                                savedDrone.getModel() == Model.LIGHTWEIGHT &&
                                savedDrone.getState() == State.IDLE)
                .verifyComplete();

        assertThat(droneRepository.count()).isEqualTo(1);
    }


    @Test
    void loadDroneWithMedicationItem() {
        Drone savedDrone = droneService.registerDrone(Mono.just(drone)).block();
        Medication savedMedication = medicationRepository.save(medication);
        assertThat(savedDrone).isNotNull();
        assertThat(savedDrone.getId()).isNotNull();
        assertThat(savedDrone.getState()).isEqualTo(State.IDLE);
        StepVerifier.create(droneService.loadDroneWithMedicationItem(Mono.just(savedDrone.getId()), Mono.just(savedMedication)))
                .expectNextMatches(customResponse -> customResponse.isStatus() &&
                        customResponse.getMessage().equals("Medication added successfully"))
                .verifyComplete();

        savedDrone = droneRepository.findById(savedDrone.getId()).get();
        assertThat(medicationRepository.count()).isEqualTo(1);
        assertThat(savedDrone.getMedications()).hasSize(1);
        assertThat(savedDrone.getMedications()).contains(savedMedication);

        StepVerifier.create(droneService.loadDroneWithMedicationItem(Mono.just(savedDrone.getId()), Mono.just(savedMedication)))
                .expectError(ApplicationException.class)
                .verify();

        System.out.println(savedDrone.getMedications());
        savedDrone.setState(State.LOADING);
        savedDrone = droneRepository.save(savedDrone);
        StepVerifier.create(droneService.loadDroneWithMedicationItem(Mono.just(savedDrone.getId()), Mono.just(savedMedication)))
                .expectError(ApplicationException.class)
                .verify();


        savedDrone.setBattery(24.0);
        savedDrone = droneRepository.save(savedDrone);
        StepVerifier.create(droneService.loadDroneWithMedicationItem(Mono.just(savedDrone.getId()), Mono.just(savedMedication)))
                .expectError(ApplicationException.class)
                .verify();
    }

    @Test
    void getMedicationItemsForADrone() {
        Drone savedDrone = droneService.registerDrone(Mono.just(drone)).block();
        Medication savedMedication = medicationRepository.save(medication);
        assertThat(savedDrone).isNotNull();
        assertThat(savedDrone.getId()).isNotNull();

        StepVerifier.create(droneService.loadDroneWithMedicationItem(Mono.just(savedDrone.getId()), Mono.just(savedMedication)))
                .expectNextMatches(customResponse -> customResponse.isStatus() &&
                        customResponse.getMessage().equals("Medication added successfully"))
                .verifyComplete();

        StepVerifier.create(droneService.getMedicationItemsForADrone(Mono.just(savedDrone.getId())))
                .expectNextMatches(medication -> medication.getName().equals(savedMedication.getName()))
                .verifyComplete();
    }

    @Test
    void getAllDrones() {
    }

    @Test
    void getAvailableDrones() {
    }

    @Test
    void getDroneBatteryLevel() {
    }

    @Test
    void droneBatteryLevelPeriodicTask() {
    }

    @Test
    void drainBatteryPeriodically() {
    }
}