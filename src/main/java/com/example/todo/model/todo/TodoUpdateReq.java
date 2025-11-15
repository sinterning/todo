package com.example.todo.model.todo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class TodoUpdateReq extends TodoCreateReq {
    @NotNull(message = "ID 不能为空")
    private Long id;
}
