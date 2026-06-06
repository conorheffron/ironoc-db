package com.ironoc.db.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDto {

    private Long employerId;

    private Long personId;

    @NotEmpty(message = "Job Title is not defined.")
    @Size(min = 2, max = 45, message = "Job Title should be between 2-45 characters.")
    private String title;

    @NotEmpty(message = "Employer Name is not defined.")
    @Size(min = 2, max = 45, message = "Employer Name should be between 2-45 characters.")
    private String employerName;

    @Min(value = 1900, message = "Start Year must be 1900 or later.")
    @Max(value = 2100, message = "Start Year must be 2100 or earlier.")
    @NotNull(message = "Start Year is not defined.")
    private Integer startYear;
}
