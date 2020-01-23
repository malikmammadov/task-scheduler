package taskscheduler.repository;

import taskscheduler.domain.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Optional<Task> addTask(Task task);

    List<Task> getTaskList(String sql, int start, int length, String filter);

    long getTaskCount();

    long getFilteredTaskCount(String filter);

    long deleteTaskById(long id);

    Optional<Task> getTaskById(long id);

    Optional<Task> updateTask(Task task);
}
