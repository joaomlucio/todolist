package com.desafio.todolist.controller;

import org.springframework.web.bind.annotation.RestController;

import com.desafio.todolist.dto.PaginationDTO;
import com.desafio.todolist.dto.PaginationParamsDTO;
import com.desafio.todolist.dto.ToDoCreateDTO;
import com.desafio.todolist.dto.ToDoDTO;
import com.desafio.todolist.dto.ToDoUpdateDTO;
import com.desafio.todolist.service.ToDoService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("/todo")
public class ToDoController {
    @Autowired
    private ToDoService service;

    /**
     * @param dto
     * @return
     * @throws Exception
     */
    @PostMapping("entries")
    public ResponseEntity<ToDoDTO> createEntity(@Valid @RequestBody ToDoCreateDTO dto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.create(dto));
    }

    /**
     * @param params
     * @return
     * @throws NotFoundException
     */
    @GetMapping("entries")
    public ResponseEntity<PaginationDTO<ToDoDTO>> getAllEntities(@Valid PaginationParamsDTO params) throws NotFoundException {
        return ResponseEntity.of(this.service.findAll(params));
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("entries/{id}")
    public ResponseEntity<ToDoDTO> getOneEntity(@PathVariable(value="id") Long id ) throws Exception {
        return ResponseEntity.of(this.service.findOne(id));
    }

    /**
     * @param id
     * @param dto
     * @return
     * @throws Exception
     */
    @PatchMapping("entries/{id}")
    public ResponseEntity<ToDoDTO> updateEntity(@PathVariable(value="id") Long id, @Valid @RequestBody ToDoUpdateDTO dto) throws Exception {
        return ResponseEntity.of(this.service.update(id, dto));
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping("entries/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteEntity(@PathVariable(value="id") Long id ) throws Exception {
        this.service.delete(id);
    }
}
