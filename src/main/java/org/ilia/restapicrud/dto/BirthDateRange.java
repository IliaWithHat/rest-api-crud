package org.ilia.restapicrud.dto;

import lombok.Value;
import org.ilia.restapicrud.validation.annotation.BirthDateRangeConstraint;

import java.time.LocalDate;

@Value
@BirthDateRangeConstraint
public class BirthDateRange {

    LocalDate from;

    LocalDate to;
}
