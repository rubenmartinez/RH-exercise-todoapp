package net.rubenmartinez.rhe.todo.domain;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="todo_item", indexes = { @Index(columnList = "ownerUserId") })
@Getter @Setter @ToString
public class TodoEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long ownerUserId;

    private String title;
    private boolean completed;
    
    @CreatedDate
    private LocalTime createdDate;
    
    @LastModifiedDate
    private LocalTime lastModifiedDate;    
}