package io.anjola.dronespringwebfluxapp.repository;

import io.anjola.dronespringwebfluxapp.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
}
