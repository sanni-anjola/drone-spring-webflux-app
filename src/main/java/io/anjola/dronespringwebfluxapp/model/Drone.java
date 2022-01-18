package io.anjola.dronespringwebfluxapp.model;

import io.anjola.dronespringwebfluxapp.enums.Model;
import io.anjola.dronespringwebfluxapp.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Drone {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 0, max = 100)
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
