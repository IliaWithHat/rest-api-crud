package org.ilia.restapicrud.handleException;

import lombok.Value;

import java.util.Map;

@Value
public class ApiError {

    int status;
    Map<String, String> errors;
}
