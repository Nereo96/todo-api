package com.abletech.todo.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.abletech.todo.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CompletedTaskCleanupJob {

	private final TaskService taskService;

	public CompletedTaskCleanupJob(TaskService taskService) {
		this.taskService = taskService;
	}

	// Ogni 15 minuti (cron in application.properties)
	@Scheduled(cron = "${todo.cleanup.cron}")
	public void deleteCompletedTasks() {
		int deleted = taskService.deleteCompletedTasks();
		log.info("Deleted {} completed tasks", deleted);
	}

}
