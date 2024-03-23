package com.desafio.todolist.model;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "to_dos")
@EntityListeners(AuditingEntityListener.class)
public class ToDoModel extends AbstractModel {
    @Column(name = "title", length = 255, nullable=false)
    private String title;

    @Column(name = "description", length = 255, nullable=false)
    private String description;

    @Column(name = "due_date", length = 255, nullable=false)
    private LocalDateTime dueDate;
}