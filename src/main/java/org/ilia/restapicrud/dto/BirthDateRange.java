package org.ilia.restapicrud.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.ilia.restapicrud.validation.annotation.BirthDateRangeConstraint;

import java.time.LocalDate;

@Value
@BirthDateRangeConstraint
public class BirthDateRange {

    @NotNull(message = "Enter 'from' date")
    LocalDate from;

    @NotNull(message = "Enter 'to' date")
    LocalDate to;
}
