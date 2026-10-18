package com.abletech.todo.dto;

import com.abletech.todo.model.TaskPriority;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaskRequest {

	@NotNull
	private String title;
	private String description;
	private TaskPriority priority;

}
