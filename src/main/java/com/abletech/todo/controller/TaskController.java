package com.abletech.todo.controller;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abletech.todo.dto.CreateTaskRequest;
import com.abletech.todo.dto.TaskResponse;
import com.abletech.todo.dto.UpdateTaskRequest;
import com.abletech.todo.model.TaskPriority;
import com.abletech.todo.model.TaskStatus;
import com.abletech.todo.service.TaskService;

// TODO: autenticazione (Spring Security) e task associati a un utente
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@PostMapping
	public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {
		TaskResponse task = taskService.create(request);
		return ResponseEntity.created(URI.create("/api/tasks/" + task.getId())).body(task);
	}

	@GetMapping
	public List<TaskResponse> list(@RequestParam(required = false) TaskStatus status,
			@RequestParam(required = false) TaskPriority priority) {
		return taskService.search(status, priority);
	}

	@GetMapping("/{id}")
	public TaskResponse get(@PathVariable Long id) {
		return taskService.findById(id);
	}

	@PutMapping("/{id}")
	public TaskResponse update(@PathVariable Long id, @Valid @RequestBody UpdateTaskRequest request) {
		return taskService.update(id, request);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		taskService.delete(id);
	}

}
