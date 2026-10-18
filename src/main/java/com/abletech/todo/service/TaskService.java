package com.abletech.todo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.abletech.todo.dto.CreateTaskRequest;
import com.abletech.todo.dto.TaskResponse;
import com.abletech.todo.dto.UpdateTaskRequest;
import com.abletech.todo.model.Task;
import com.abletech.todo.model.TaskPriority;
import com.abletech.todo.model.TaskStatus;
import com.abletech.todo.repository.TaskRepository;

@Service
@Transactional
public class TaskService {

	private final TaskRepository taskRepository;

	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public TaskResponse create(CreateTaskRequest request) {
		Task task = new Task();
		task.setTitle(request.getTitle());
		task.setDescription(request.getDescription());
		if (request.getPriority() != null) {
			task.setPriority(request.getPriority());
		}
		return TaskResponse.from(taskRepository.save(task));
	}

	@Transactional(readOnly = true)
	public TaskResponse findById(Long id) {
		return TaskResponse.from(getTask(id));
	}

	// TODO: paginazione (Pageable) e altri filtri (ricerca nel testo, data di scadenza)
	@Transactional(readOnly = true)
	public List<TaskResponse> search(TaskStatus status, TaskPriority priority) {
		return taskRepository.search(status, priority).stream().map(TaskResponse::from).toList();
	}

	public TaskResponse update(Long id, UpdateTaskRequest request) {
		Task task = getTask(id);
		task.setTitle(request.getTitle());
		task.setDescription(request.getDescription());
		task.setStatus(request.getStatus());
		task.setPriority(request.getPriority());
		return TaskResponse.from(task); // entità gestita da JPA: le modifiche vengono salvate al commit (dirty checking)
	}

	public void delete(Long id) {
		taskRepository.delete(getTask(id));
	}

	public int deleteCompletedTasks() {
		return taskRepository.deleteByStatus(TaskStatus.DONE);
	}

	// TODO: eccezioni specifiche (es. TaskNotFoundException) gestite in un @RestControllerAdvice
	private Task getTask(Long id) {
		return taskRepository.findById(id)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task " + id + " not found"));
	}

}
