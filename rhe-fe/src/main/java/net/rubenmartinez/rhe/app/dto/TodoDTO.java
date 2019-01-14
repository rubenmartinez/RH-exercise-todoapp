package net.rubenmartinez.rhe.app.dto;

import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class TodoDTO {
	private Long id;
    private String title;
    private boolean completed;
    private LocalTime createdDate;
    private LocalTime lastModifiedDate;    
}