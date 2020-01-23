package taskscheduler.validator;

import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import taskscheduler.domain.Task;
import taskscheduler.service.ValidationService;

import java.time.LocalDate;

import static taskscheduler.constants.ValidationConstants.*;

@Component
public class TaskValidator implements Validator {

    @Autowired
    ValidationService validationService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Task.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Task task = (Task) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "taskName", "task.taskName.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "task.description.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "task.startDate.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "task.endDate.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "person", "task.person.required");

        if (!errors.hasErrors()) {
            if (!GenericValidator.isInRange(task.getTaskName().length(), TASK_NAME_MIN_LENGTH, TASK_NAME_MAX_LENGTH)) {
                errors.rejectValue("taskName", "task.taskName.length", new Object[]{TASK_NAME_MIN_LENGTH, TASK_NAME_MAX_LENGTH}, null);
            }

            if (task.getId() == 0 && validationService.isDuplicateTask(task.getTaskName())) {
                errors.rejectValue("taskName", "task.taskName.duplicate");
            }

            if (!GenericValidator.isDate(task.getStartDate().toString(), TASK_DATE_FORMAT, true)) {
                errors.rejectValue("startDate", "task.startDate.format", new Object[]{TASK_DATE_FORMAT}, null);
            }

            if (!GenericValidator.isDate(task.getEndDate().toString(), TASK_DATE_FORMAT, true)) {
                errors.rejectValue("endDate", "task.endDate.format", new Object[]{TASK_DATE_FORMAT}, null);
            }

            if (task.getStartDate().isBefore(LocalDate.now())) {
                errors.rejectValue("startDate", "task.startDate.start", new Object[]{LocalDate.now()}, null);
            }

            if (task.getEndDate().isBefore(LocalDate.now())) {
                errors.rejectValue("endDate", "task.endDate.start", new Object[]{LocalDate.now()}, null);
            }

            if (task.getStartDate().isAfter(task.getEndDate())) {
                errors.rejectValue("startDate", "task.startDate.error", new Object[]{task.getEndDate()}, null);
            }

            if (task.getStartDate().isEqual(task.getEndDate())) {
                errors.rejectValue("endDate", "task.endDate.error", new Object[]{task.getStartDate()}, null);
            }

            if (!GenericValidator.isInRange(task.getPerson().length(), PERSON_NAME_MIN_LENGTH, PERSON_NAME_MAX_LENGTH)) {
                errors.rejectValue("person", "task.person.length", new Object[]{PERSON_NAME_MIN_LENGTH, PERSON_NAME_MAX_LENGTH}, null);
            }

            if (!GenericValidator.matchRegexp(task.getPerson(), "^[a-zöğəışçüA-ZÖĞƏIŞÇÜ]*$")) {
                errors.rejectValue("person", "task.person.alpha");
            }

            if (!GenericValidator.matchRegexp(task.getPerson(), "[A-Z][a-z]+")) {
                errors.rejectValue("person", "task.person.initcap");
            }

        }
    }
}
