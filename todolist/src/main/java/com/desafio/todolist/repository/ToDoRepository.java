package com.desafio.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.todolist.dto.ToDoCreateDTO;
import com.desafio.todolist.model.ToDoModel;

@Repository
public interface ToDoRepository extends JpaRepository<ToDoModel, Long>{

    ToDoModel save(ToDoCreateDTO dto);
}
