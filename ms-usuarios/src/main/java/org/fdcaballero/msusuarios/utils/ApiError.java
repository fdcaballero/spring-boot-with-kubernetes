package org.fdcaballero.msusuarios.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus status, String message, String errors) {
        this.status = status;
        this.message = message;
        this.errors = Collections.singletonList(errors);
    }
}
