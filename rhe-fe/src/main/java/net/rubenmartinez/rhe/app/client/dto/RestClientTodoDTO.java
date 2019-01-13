package net.rubenmartinez.rhe.app.client.dto;

import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class RestClientTodoDTO {
    private Long id;
    private Long ownerUserId;
    private String title;
    private boolean completed;
    private LocalTime createdDate;
    private LocalTime lastModifiedDate;   
}
