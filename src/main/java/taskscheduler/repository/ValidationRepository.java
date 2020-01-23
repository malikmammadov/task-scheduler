package taskscheduler.repository;

import taskscheduler.domain.Task;

public interface ValidationRepository {

    boolean isDuplicateTask(String taskName);
}
