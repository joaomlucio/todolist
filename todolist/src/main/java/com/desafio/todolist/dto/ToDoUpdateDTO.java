package com.desafio.todolist.dto;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class ToDoUpdateDTO {
    private String title;

    private String description;

    private LocalDateTime dueDate; 
}
