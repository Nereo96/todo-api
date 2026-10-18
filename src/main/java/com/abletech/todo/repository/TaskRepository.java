package com.abletech.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.abletech.todo.model.Task;
import com.abletech.todo.model.TaskPriority;
import com.abletech.todo.model.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {

	// Un parametro null significa "nessun filtro"
	@Query("""
			select t from Task t
			where (:status is null or t.status = :status)
			  and (:priority is null or t.priority = :priority)
			order by t.createdAt desc
			""")
	List<Task> search(TaskStatus status, TaskPriority priority);

	// Una sola DELETE sul database, senza caricare le entità in memoria
	@Modifying
	@Query("delete from Task t where t.status = :status")
	int deleteByStatus(TaskStatus status);

}
