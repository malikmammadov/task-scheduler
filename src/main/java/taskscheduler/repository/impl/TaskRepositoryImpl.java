package taskscheduler.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import taskscheduler.domain.Task;
import taskscheduler.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private TaskRowMapper taskRowMapper;

    @Override
    public Optional<Task> addTask(Task task) {

        Optional<Task> optionalTask = Optional.empty();

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("task_name", task.getTaskName())
                .addValue("description", task.getDescription())
                .addValue("start_date", task.getStartDate())
                .addValue("end_date", task.getEndDate())
                .addValue("person", task.getPerson());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int count = namedParameterJdbcTemplate.update(SqlQuery.ADD_TASK, params, keyHolder);
        if (count > 0) {
            task.setId(keyHolder.getKey().longValue());
            optionalTask = Optional.of(task);
        } else {
            throw new RuntimeException("task not added, task = " + task);
        }

        return optionalTask;
    }

    @Override
    public List<Task> getTaskList(String sql, int start, int length, String filter) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("filter", "%" + filter + "%")
                .addValue("start", start)
                .addValue("length", length);

        List<Task> tasks = namedParameterJdbcTemplate.query(sql, params, taskRowMapper);

        return tasks;
    }

    @Override
    public long getTaskCount() {

        Long count = namedParameterJdbcTemplate.getJdbcTemplate().queryForObject(SqlQuery.GET_TASK_COUNT, Long.class);

        return count;
    }

    @Override
    public long getFilteredTaskCount(String filter) {

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("filter", "%" + filter + "%");

        Long count = namedParameterJdbcTemplate.queryForObject(SqlQuery.GET_FILTERED_TASK_COUNT, params, Long.class);

        return count.longValue();
    }

    @Override
    public long deleteTaskById(long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        return namedParameterJdbcTemplate.update(SqlQuery.DELETE_TASK_BY_ID, params);
    }

    @Override
    public Optional<Task> getTaskById(long id) {
        Optional<Task> optionalTask = Optional.empty();

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        List<Task> tasks = namedParameterJdbcTemplate.query(SqlQuery.GET_TASK_BY_ID, params, taskRowMapper);

        if (!tasks.isEmpty()) {
            optionalTask = Optional.of(tasks.get(0));
        }

        return optionalTask;
    }

    @Override
    public Optional<Task> updateTask(Task task) {

        Optional<Task> optionalTask = Optional.empty();

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", task.getId())
                .addValue("task_name", task.getTaskName())
                .addValue("description", task.getDescription())
                .addValue("start_date", task.getStartDate())
                .addValue("end_date", task.getEndDate())
                .addValue("person", task.getPerson())
                .addValue("udate", LocalDateTime.now());

        int count = namedParameterJdbcTemplate.update(SqlQuery.UPDATE_TASK, params);
        if (count == 0) {
            throw new RuntimeException("task not added, task = " + task);
        }

        return optionalTask;
    }
}
