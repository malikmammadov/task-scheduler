package taskscheduler.service;

import taskscheduler.domain.Task;

public interface ValidationService {

    boolean isDuplicateTask(String taskName);
}
