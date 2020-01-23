package taskscheduler.service;

import taskscheduler.domain.DataTableRequest;
import taskscheduler.domain.DataTableResult;
import taskscheduler.domain.Task;

import java.util.Optional;

public interface TaskService {

    Optional<Task> addTask(Task task);

    DataTableResult getTaskList(DataTableRequest request);

    long deleteTaskById(long id);

    Optional<Task> getTaskById(long id);

    Optional<Task> updateTask(Task task);
}
