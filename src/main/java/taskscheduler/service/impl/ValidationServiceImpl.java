package taskscheduler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taskscheduler.domain.Task;
import taskscheduler.repository.ValidationRepository;
import taskscheduler.service.ValidationService;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    ValidationRepository validationRepository;

    @Override
    public boolean isDuplicateTask(String taskName) {
        return validationRepository.isDuplicateTask(taskName);
    }
}
