package com.springboot.todojpa.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.todojpa.domain.ToDo;
import com.springboot.todojpa.domain.ToDoBuilder;
import com.springboot.todojpa.repository.ToDoRepository;
import com.springboot.todojpa.validation.ToDoValidationError;
import com.springboot.todojpa.validation.ToDoValidationErrorBuilder;

@RestController
@RequestMapping("/api")
public class ToDoController {

    private ToDoRepository repository;

    @Autowired
    public ToDoController(ToDoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todo")
    public ResponseEntity<Iterable<ToDo>> getTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<ToDo> getTodoById(@PathVariable String id) {
        Optional<ToDo> toDo = repository.findById(id);

        if(toDo.isPresent()) {
            return ResponseEntity.ok(toDo.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/todo/{id}")
    public ResponseEntity<ToDo> setCompleted(@PathVariable String id) {
        Optional<ToDo> toDo = repository.findById(id);

        if (!toDo.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ToDo result = toDo.get();
        result.setCompleted(true);
        repository.save(result);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .buildAndExpand(result.getId()).toUri();

        return ResponseEntity
            .ok()
            .header("Location", location.toString())
            .build();
    }

    @RequestMapping(value = "/todo", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> createTodo(@Valid @RequestBody ToDo todo, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity
                .badRequest()
                .body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }

        ToDo result = repository.save(todo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<ToDo> deleteTodo(@PathVariable String id) {
        repository.delete(ToDoBuilder.create().withId(id).build());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/todo")
    public ResponseEntity<ToDo> deleteTodo(@RequestBody ToDo todo) {
        repository.delete(todo);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ToDoValidationError handleException(Exception exception) {
        return new ToDoValidationError(exception.getMessage());
    }
}
