package com.desafio.todolist.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PaginationDTO<T>{
    private Long count;
    private List<T> data = new ArrayList<T>();
}
