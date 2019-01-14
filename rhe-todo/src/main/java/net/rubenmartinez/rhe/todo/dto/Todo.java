package net.rubenmartinez.rhe.todo.dto;

import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Todo {
    private Long id;

    private Long ownerUserId;

    private String title;
    private boolean completed;
    
    private LocalTime createdDate;
    
    private LocalTime lastModifiedDate;    
}