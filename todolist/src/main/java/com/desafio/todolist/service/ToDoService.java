package com.desafio.todolist.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.todolist.dto.PaginationDTO;
import com.desafio.todolist.dto.PaginationParamsDTO;
import com.desafio.todolist.dto.ToDoCreateDTO;
import com.desafio.todolist.dto.ToDoDTO;
import com.desafio.todolist.dto.ToDoUpdateDTO;
import com.desafio.todolist.dto.mapper.ToDoMapper;
import com.desafio.todolist.model.ToDoModel;
import com.desafio.todolist.repository.ToDoRepository;

@Service
@Transactional
public class ToDoService {
    @Autowired
    private ToDoRepository repository;

    private ToDoMapper mapper = new ToDoMapper();

    // Uses a specific DTO for creation
    // The DTO is mapped into a model and then save using the model's repository
    public ToDoDTO create(ToDoCreateDTO dto) {
        ToDoModel model = this.mapper.convertCreateDtoToModel(dto);
        model = this.repository.saveAndFlush(model);
        return mapper.convertToDto(model);
    }

    // Retrieves a model instance given the id through the model's repository find method
    public Optional<ToDoDTO> findOne(Long id) throws NotFoundException{
        Optional<ToDoModel> model = this.repository.findById(id);
        if (model.isEmpty()) return Optional.empty();
        return Optional.of(this.mapper.convertToDto(model.get()));
    }

    // Retrieves specified model instances given the pagination params through the model's repository special find method
    public Optional<PaginationDTO<ToDoDTO>> findAll(PaginationParamsDTO params) throws NotFoundException{
        PaginationDTO<ToDoDTO> dto = new PaginationDTO<>();
        Pageable pageable = PageRequest.of(Integer.parseInt(params.getPage()) - 1, Integer.parseInt(params.getSize()),
				Sort.by(Direction.valueOf(params.getSortDirection()), params.getSortField()));
        Page<ToDoModel> page = this.repository.findAll(pageable);

        if (page.isEmpty()){
            dto.setCount((long) 0);
            return Optional.of(dto);
        }

        dto.setCount((long) page.getSize());
        dto.setData(page.stream().map((element) -> this.mapper.convertToDto(element)).collect(Collectors.toList()));
        return Optional.of(dto);
    }

    // Updates a model instance with the given id. The non-null fields of the update dto is then copied into the models instance. The updated model is saved using the repository's save method
    public Optional<ToDoDTO> update(Long id, ToDoUpdateDTO dto) throws NotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        Optional<ToDoModel> model = this.repository.findById(id);
        if (model.isEmpty()) return Optional.empty();

        ToDoModel modelToUpdate =  model.get();
        
        String[] nullProperties = ToDoMapper.getNullPropertyNames(dto);
        BeanUtils.copyProperties(dto, modelToUpdate, nullProperties);
        
        modelToUpdate = this.repository.save(modelToUpdate);

        return Optional.of(this.mapper.convertToDto(modelToUpdate));
    }

    // Deletes a model instance with the given id.
    public Optional<ToDoDTO> delete(Long id) throws NotFoundException{
        Optional<ToDoModel> model = this.repository.findById(id);
        if (model.isEmpty()) return Optional.empty();
        this.repository.delete(model.get());
        return Optional.of(this.mapper.convertToDto(model.get()));
    }
}
