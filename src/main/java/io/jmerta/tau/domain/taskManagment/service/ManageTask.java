package io.jmerta.tau.domain.taskManagment.service;

import io.jmerta.tau.domain.taskManagment.entity.Task;
import io.jmerta.tau.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManageTask {

    private TaskRepository taskRepository;
    private ManageCategory manageCategory;

    @Autowired
    public ManageTask(TaskRepository taskRepository,@Lazy ManageCategory manageCategory) {
        this.taskRepository = taskRepository;
        this.manageCategory = manageCategory;
    }

    public void createNewTask(Task task){


        taskRepository.createNewTask(task);
    }

    public void deleteTask(Long id, Long accountId){

        taskRepository.deleteTask(id);
    }

    public List<Task> getAllTasksForUser(Long accountId){

        taskRepository.getAllTasksForUser(accountId);

        return new ArrayList<>();
    }


    public List<Task> getAllTasksForUserByCategory(Long accountId, Long categoryId){

        taskRepository.getAllTasksForUserByCategory(accountId, categoryId);

        return new ArrayList<>();
    }
}
