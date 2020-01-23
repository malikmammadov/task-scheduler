package taskscheduler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taskscheduler.domain.DataTableRequest;
import taskscheduler.domain.DataTableResult;
import taskscheduler.domain.Task;
import taskscheduler.repository.TaskRepository;
import taskscheduler.repository.impl.SqlQuery;
import taskscheduler.service.TaskService;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Optional<Task> addTask(Task task) {
        return taskRepository.addTask(task);
    }

    @Override
    public DataTableResult getTaskList(DataTableRequest request) {

        Map<Integer, String> columnMapping = new HashMap<>();
        columnMapping.put(0, "id");
        columnMapping.put(1, "description");
        columnMapping.put(2, "task_name");
        columnMapping.put(3, "start_date");
        columnMapping.put(4, "end_date");
        columnMapping.put(5, "person");
        columnMapping.put(6, "idate");
        columnMapping.put(7, "udate");

        String sql = SqlQuery.GET_TASK_LIST_PAGING
                .replace("{SORT_COLUMN}", columnMapping.get(request.getSortColumn()))
                .replace("{SORT_DIRECTION}", request.getSortDirection());

        DataTableResult result = new DataTableResult();
        result.setDraw(request.getDraw());
        result.setRecordsTotal(taskRepository.getTaskCount());
        result.setRecordsFiltered(taskRepository.getFilteredTaskCount(request.getFilter()));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");
        List<Task> tasks = taskRepository.getTaskList(sql, request.getStart(), request.getLength(), request.getFilter());

        result.setData(new Object[tasks.size()][9]);
        for (int i = 0; i < tasks.size(); i++) {
            result.getData()[i][0] = tasks.get(i).getId();
            result.getData()[i][1] = tasks.get(i).getTaskName();
            result.getData()[i][2] = tasks.get(i).getDescription();
            result.getData()[i][3] = dateFormatter.format(tasks.get(i).getStartDate());
            result.getData()[i][4] = dateFormatter.format(tasks.get(i).getEndDate());
            result.getData()[i][5] = tasks.get(i).getPerson();
            result.getData()[i][6] = dateTimeFormatter.format(tasks.get(i).getInsertDate());
            if (tasks.get(i).getLastUpdate() != null) {
                result.getData()[i][7] = dateTimeFormatter.format(tasks.get(i).getLastUpdate());
            } else {
                result.getData()[i][7] = "";
            }
            String delete = "<button type=\"button\" name=\"delete\" class=\"btn btn-danger btn-xs delete btn-block\" id=%d> Delete </button>";
            String edit = "<a href=\"edit?id=%d\" class=\"btn btn-basic\">Edit</a>";
            String view = "<button type=\"button\" class=\"btn btn-primary btn-block\" data-toggle=\"modal\"" +
                    " data-target=\"#popupview\" id=%d onclick=\"showdetail(this)\">View</button>";
            result.getData()[i][8] = String.format(delete + " " + edit + " " + view, tasks.get(i).getId(), tasks.get(i).getId(), tasks.get(i).getId());
        }

        return result;
    }

    @Override
    public long deleteTaskById(long id) {
        return taskRepository.deleteTaskById(id);
    }

    @Override
    public Optional<Task> getTaskById(long id) {
        return taskRepository.getTaskById(id);
    }

    @Override
    public Optional<Task> updateTask(Task task) {
        return taskRepository.updateTask(task);
    }
}
