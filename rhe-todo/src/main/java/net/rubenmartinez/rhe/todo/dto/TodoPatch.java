package net.rubenmartinez.rhe.todo.dto;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TodoPatch {
    private Optional<Long> ownerUserId;

    private Optional<String> title;
    private Optional<Boolean> completed;
}