package io.anjola.dronespringwebfluxapp.repository;

import io.anjola.dronespringwebfluxapp.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, Long> {
}
