package com.cydeo.controller;

import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final ProjectService projectService;
    private final UserService userService;
    private final TaskService taskService;

    public TaskController(ProjectService projectService, UserService userService, TaskService taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/create")
    public String createTask(Model model) {

        model.addAttribute("task", new TaskDTO());
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees", userService.findEmployees());
        model.addAttribute("tasks", taskService.findAll());


        return "/task/create";
    }

    @PostMapping("/create")
    public String insertTask(TaskDTO task) {

        taskService.save(task);

        return "redirect:/task/create";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {

        taskService.deleteById(id);
        return "redirect:/task/create";
    }

    @GetMapping("/update/{taskId}")
    public String editTask(@PathVariable("taskId") Long taskId, Model model) {

        model.addAttribute("task", taskService.findById(taskId));
        model.addAttribute("projects", projectService.findAll());
        model.addAttribute("employees", userService.findEmployees());
        model.addAttribute("tasks", taskService.findAll());

        return "task/update";
    }
//@PostMapping("/update/{taskId}")
//public String updateTask(@PathVariable("taskId") Long taskId,TaskDTO task){
//
//        task.setId(taskId);
//        taskService.update(task);
//
//        return "redirect:/task/create";
//}

// this and down same things spring enough smart to understand

    @PostMapping("/update/{id}")
    public String updateTask(TaskDTO task) {

        taskService.update(task);

        return "redirect:/task/create";
    }

    @GetMapping("/employee/pending-tasks")
    public String employeePendingTasks(Model model) {

        model.addAttribute("tasks", taskService.findAllTasksByStatusIsNot(Status.COMPLETE));

        return "/task/pending-tasks";
    }

    @GetMapping("/employee/archive")
    public String employeeArchievedTasks(Model model) {

        model.addAttribute("tasks", taskService.findAllTasksByStatus(Status.COMPLETE));

        return "/task/archive";
    }

    @GetMapping("/employee/edit/{id}")
    public String employeeEditTask(@PathVariable("id") Long id, Model model) {

        model.addAttribute("task",taskService.findById(id));
//        model.addAttribute("projects",projectService.findAll());
//        model.addAttribute("employees",userService.findEmployees());
        model.addAttribute("statuses",Status.values());
        model.addAttribute("tasks",taskService.findAllTasksByStatusIsNot(Status.COMPLETE));

        return "/task/status-update";
    }

    @PostMapping("/employee/update/{id}")
    public String employeeUpdateTask(TaskDTO task){

        taskService.updateStatus(task);

        return "redirect:/task/employee/pending-tasks";


    }


}