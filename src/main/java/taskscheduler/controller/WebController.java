package taskscheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import taskscheduler.domain.DataTableRequest;
import taskscheduler.domain.DataTableResult;
import taskscheduler.domain.Task;
import taskscheduler.service.TaskService;
import taskscheduler.service.ValidationService;
import taskscheduler.validator.TaskValidator;

import java.util.Optional;

@RequestMapping("/")
@Controller
public class WebController {

    @Autowired
    private TaskValidator taskValidator;

    @Autowired
    private TaskService taskService;

    @Autowired
    ValidationService validationService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        Object o = binder.getTarget();
        if (o != null) {
            if (o.getClass().equals(Task.class)) {
                binder.setValidator(taskValidator);
            }
        }
    }

    @GetMapping("/")
    public String index() {
        return "web/index";
    }

    @GetMapping("list")
    @ResponseBody
    public DataTableResult getTaskList(
            @RequestParam(name = "draw") int draw,
            @RequestParam(name = "start") int start,
            @RequestParam(name = "length") int length,
            @RequestParam(name = "order[0][column]") int sortColumn,
            @RequestParam(name = "order[0][dir]") String sortDirection,
            @RequestParam(name = "search[value]") String filter) {

        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setDraw(draw);
        dataTableRequest.setStart(start);
        dataTableRequest.setLength(length);
        dataTableRequest.setSortColumn(sortColumn);
        dataTableRequest.setSortDirection(sortDirection);
        dataTableRequest.setFilter(filter);

        return taskService.getTaskList(dataTableRequest);
    }

    @GetMapping("add")
    public ModelAndView insert() {

        ModelAndView modelAndView = new ModelAndView("web/add");
        modelAndView.addObject("task", new Task());

        return modelAndView;
    }

    @PostMapping("create")
    public ModelAndView insert(@ModelAttribute("task") @Validated Task task,
                               BindingResult errors) {

        ModelAndView modelAndView = new ModelAndView("redirect:/");
        if (errors.hasErrors()) {
            modelAndView.setViewName("web/add");
        } else {
            taskService.addTask(task);
        }

        return modelAndView;
    }

    @PostMapping("delete")
    @ResponseBody
    public Long delete(@RequestParam(name = "id") String id) {
        return taskService.deleteTaskById(Long.parseLong(id));
    }

    @GetMapping("edit")
    public ModelAndView edit(@RequestParam(name = "id") String id) {

        ModelAndView modelAndView = new ModelAndView("web/edit");
        Optional<Task> task = taskService.getTaskById(Long.parseLong(id));
        modelAndView.addObject("task", task.get());

        return modelAndView;
    }

    @PostMapping("update")
    public ModelAndView edit(@ModelAttribute("task") @Validated Task task,
                             BindingResult errors) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");

        if (errors.hasErrors()) {
            modelAndView.setViewName("web/edit");
        } else {
            taskService.updateTask(task);
        }

        return modelAndView;
    }

    @GetMapping("checkTaskName")
    @ResponseBody
    public boolean checkTaskName(@RequestParam(name = "taskName") String taskName) {
        return validationService.isDuplicateTask(taskName);
    }

    @GetMapping("view")
    @ResponseBody
    public Task view(@RequestParam(name = "id") long id) {
        return taskService.getTaskById(id).get();
    }
}