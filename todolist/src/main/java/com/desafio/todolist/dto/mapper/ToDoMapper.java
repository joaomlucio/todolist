package com.desafio.todolist.dto.mapper;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import com.desafio.todolist.dto.ToDoCreateDTO;
import com.desafio.todolist.dto.ToDoDTO;
import com.desafio.todolist.model.ToDoModel;

@Component
public class ToDoMapper {
    private ModelMapper mapper = new ModelMapper();

    public ToDoDTO convertToDto(ToDoModel model) throws ParseException {
        return mapper.map(model, ToDoDTO.class);
    }
    
    public ToDoModel convertToModel(ToDoDTO dto) throws ParseException {
        return mapper.map(dto, ToDoModel.class);
    }
    
    public ToDoModel convertCreateDtoToModel(ToDoCreateDTO dto) throws ParseException {
        return mapper.map(dto, ToDoModel.class);
    }
    
    public ToDoModel convertUpdateDtoToModel(ToDoCreateDTO dto) throws ParseException {
        return mapper.map(dto, ToDoModel.class);
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
            .map(FeatureDescriptor::getName)
            .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
            .toArray(String[]::new);
    }
}
