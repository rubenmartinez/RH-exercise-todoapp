package net.rubenmartinez.rhe.todo.dto;

import lombok.Data;
import net.rubenmartinez.rhe.todo.domain.TodoEntity;

@Data
public class TodoNew {
    private Long ownerUserId;
    private String title;
    private boolean completed;
}