package com.desafio.todolist.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.desafio.todolist.dto.PaginationDTO;
import com.desafio.todolist.dto.PaginationParamsDTO;
import com.desafio.todolist.dto.ToDoCreateDTO;
import com.desafio.todolist.dto.ToDoDTO;
import com.desafio.todolist.dto.ToDoUpdateDTO;
import com.desafio.todolist.dto.mapper.ToDoMapper;
import com.desafio.todolist.helpers.LocalDateTimeTypeAdapter;
import com.desafio.todolist.model.ToDoModel;
import com.desafio.todolist.service.ToDoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebMvcTest(ToDoController.class)
class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDoService service;

    private ToDoMapper mapper = new ToDoMapper();

    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter()).create();



    @Test
    void shouldCreateATask() throws Exception {
        ToDoCreateDTO dto = new ToDoCreateDTO();
        LocalDateTime date = LocalDateTime.now();
        dto.setTitle("Basic Title");
        dto.setDescription("Basic Description");
        dto.setDueDate(date);

        ToDoModel model = new ToDoModel();
        model.setCreatedAt(date);
        model.setUpdatedAt(date);
        model.setId((long) 1);
        model.setTitle("Basic Title");
        model.setDescription("Basic Description");
        model.setDueDate(date);

        when(service.create(dto)).thenReturn(this.mapper.convertToDto(model));
        this.mockMvc.perform(post("/todo/entries").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(dto))).andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(Math.toIntExact(model.getId()))));
    }

    @Test
    void shouldUpdateATask() throws Exception {
        ToDoUpdateDTO dto = new ToDoUpdateDTO();
        dto.setTitle("Update Title");

        LocalDateTime date = LocalDateTime.now();

        ToDoModel model = new ToDoModel();
        model.setCreatedAt(date);
        model.setUpdatedAt(date);
        model.setId((long) 1);
        model.setTitle("Update Title");
        model.setDescription("Basic Description");
        model.setDueDate(date);

        when(service.update((long) 1, dto)).thenReturn(Optional.of(this.mapper.convertToDto(model)));
        this.mockMvc.perform(patch("/todo/entries/1").contentType(MediaType.APPLICATION_JSON).content(this.gson.toJson(dto))).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.title", is(model.getTitle())));
    }

    @Test
    void shouldDeleteATask() throws Exception {
        when(service.delete((long) 1)).thenReturn(Optional.of(this.mapper.convertToDto(new ToDoModel())));
        this.mockMvc.perform(delete("/todo/entries/1").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnOneTask() throws Exception {
        ToDoModel model = new ToDoModel();
        model.setId((long) 1);

        when(service.findOne((long) 1)).thenReturn(Optional.of(this.mapper.convertToDto(model)));
        this.mockMvc.perform(get("/todo/entries/1").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(Math.toIntExact(model.getId()))));
    }

    @Test
    void shouldReturnAllTasks() throws Exception {
        PaginationDTO<ToDoDTO> dto = new PaginationDTO<>();
        PaginationParamsDTO params = new PaginationParamsDTO();
        dto.setCount((long) 1);

        params.setPage("1");
        params.setSize("100");

        when(service.findAll(params)).thenReturn(Optional.of(dto));
        this.mockMvc.perform(get("/todo/entries").contentType(MediaType.APPLICATION_JSON).param("page", params.getPage()).param("size", params.getSize())).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.count", is(Math.toIntExact(dto.getCount()))));
    }
}