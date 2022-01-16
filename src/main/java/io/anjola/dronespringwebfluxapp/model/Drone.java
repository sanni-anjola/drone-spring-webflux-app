package io.anjola.dronespringwebfluxapp.model;

import io.anjola.dronespringwebfluxapp.enums.Model;
import io.anjola.dronespringwebfluxapp.enums.State;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Drone {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotBlank
    @Max(100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private Model model;

    @Max(500) @Min(0)
    private Double weight;

    @Max(100) @Min(0)
    private Double battery;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany
    private List<Medication> medications;
}
