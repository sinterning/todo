package com.example.todo.model.todo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TodoCreateReq {

    @NotBlank(message = "名称不能为空")
    @Size(max = 255, message = "名称长度超限")
    private String name;

    @NotNull(message = "状态不能为空")
    private TodoStatus status;

    @NotNull(message = "截止日期不能为空")
    private LocalDate dueDate;

    @Size(max = 500, message = "描述长度超限")
    private String description;
}
