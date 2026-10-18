package com.abletech.todo.dto;

import java.time.Instant;

import com.abletech.todo.model.Task;
import com.abletech.todo.model.TaskPriority;
import com.abletech.todo.model.TaskStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponse {

	private Long id;
	private String title;
	private String description;
	private TaskStatus status;
	private TaskPriority priority;
	private Instant createdAt;

	public static TaskResponse from(Task task) {
		TaskResponse response = new TaskResponse();
		response.setId(task.getId());
		response.setTitle(task.getTitle());
		response.setDescription(task.getDescription());
		response.setStatus(task.getStatus());
		response.setPriority(task.getPriority());
		response.setCreatedAt(task.getCreatedAt());
		return response;
	}

}
