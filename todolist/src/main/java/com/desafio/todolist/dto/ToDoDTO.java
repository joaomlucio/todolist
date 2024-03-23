package com.desafio.todolist.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ToDoDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String description;
    private LocalDateTime dueDate;
}
