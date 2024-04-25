package org.ilia.restapicrud.handleException;

import lombok.Value;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Value
public class ApiError {

    HttpStatus status;
    Map<String, String> errors;
}
