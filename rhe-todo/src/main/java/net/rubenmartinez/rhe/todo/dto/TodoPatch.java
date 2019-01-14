package net.rubenmartinez.rhe.todo.dto;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TodoPatch {
    private Optional<Long> ownerUserId;

    private Optional<String> title;
    private Optional<Boolean> completed;
}