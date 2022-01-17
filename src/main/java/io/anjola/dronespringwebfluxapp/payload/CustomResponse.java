package io.anjola.dronespringwebfluxapp.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomResponse {
    private String message;
    private boolean status;
    private final LocalDateTime timestamp;

    public CustomResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
        timestamp = LocalDateTime.now();
    }
}
