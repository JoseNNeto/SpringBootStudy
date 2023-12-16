package com.estudo.EstudoSpring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudo.EstudoSpring.models.Task;
import com.estudo.EstudoSpring.models.User;
import com.estudo.EstudoSpring.repositories.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskSevice {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException("Task not found! ID: " + id + ", Type: " + Task.class.getName()));
    }

    @Transactional
    public Task create(Task obj){
        User user = userService.findById(obj.getUser().getId());

        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj){
        Task newObj = this.findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id){
        this.findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Task can't be deleted!");
        }
    }
}
