package com.springboot.todojpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.springboot.todojpa.domain.ToDo;

public interface ToDoRepository extends CrudRepository<ToDo, String> {

}
