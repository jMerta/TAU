package io.jmerta.tau.repository;

import io.jmerta.tau.domain.taskManagment.entity.Task;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskRepository {
    void createNewTask(Task task);

    void deleteTask(Long id);

    void getAllTasksForUser(Long accountId);

    void getAllTasksForUserByCategory(Long accountId, Long categoryId);
}
