package com.ironoc.db.dto;

import module java.base;

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
public class PersonDto {

    private Long id;

    @NotEmpty(message = "Title is not defined.")
    @Size(min = 2, max = 5, message = "Title should be between 2-5 characters.")
    private String title;

    @NotEmpty(message = "First Name is not defined.")
    @Size(min = 3, max = 30, message = "First Name should be between 3-30 characters.")
    private String firstName;

    @NotEmpty(message = "Surname is not defined.")
    @Size(min = 3, max = 30, message = "Surname should be between 3-30 characters.")
    private String surname;

    @Min(value = 1, message = "Age is less than 1.")
    @Max(value = 90, message = "Age is greater than 90.")
    @NotNull(message = "Age is not defined.")
    private Integer age;
}
