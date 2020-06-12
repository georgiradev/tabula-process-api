package com.internship.tabulaprocessing.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DepartmentDTO {

    @Min(value = 0, message = "id cannot be less than zero")
    private int id;

    @NotBlank(message = "Department name cannot be blank")
    @Size(
            min = 4,
            max = 25,
            message = "Department name cannot be less than 4 or more than 25 characters")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
