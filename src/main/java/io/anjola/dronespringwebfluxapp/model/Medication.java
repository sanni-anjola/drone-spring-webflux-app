package io.anjola.dronespringwebfluxapp.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Data
public class Medication {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9_-]+")
    private String name;

    @Min(0)
    private Double weight;

    @Pattern(regexp = "^[A-Z0-9_]+")
    private String code;

    private String image;

}
