package com.example.todo.model.todo;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TodoQueryReq {

    @Size(max = 255, message = "名称长度超限")
    private String name;

    private TodoStatus status;

    private LocalDate dueDateFrom;

    private LocalDate dueDateTo;

    //format: field dir,  example: ["status desc","name asc"]
    private List<String> sortFields;
}
