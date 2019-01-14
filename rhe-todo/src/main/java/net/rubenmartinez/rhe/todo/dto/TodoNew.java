package net.rubenmartinez.rhe.todo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class TodoNew {
    private Long ownerUserId;
    private String title;
    private boolean completed;
}