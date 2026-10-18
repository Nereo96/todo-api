package com.abletech.todo.dto;

import com.abletech.todo.model.TaskPriority;
import com.abletech.todo.model.TaskStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskRequest {

	@NotNull
	private String title;
	private String description;
	@NotNull
	private TaskStatus status;
	@NotNull
	private TaskPriority priority;

}
