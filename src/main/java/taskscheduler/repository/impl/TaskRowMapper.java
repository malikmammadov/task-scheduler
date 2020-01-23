package taskscheduler.repository.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import taskscheduler.domain.Task;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int i) throws SQLException {
        Task task = new Task();
        task.setId(rs.getLong("id"));
        task.setTaskName(rs.getString("task_name"));
        task.setDescription(rs.getString("description"));
        task.setStartDate(rs.getDate("start_date").toLocalDate());
        task.setEndDate(rs.getDate("end_date").toLocalDate());
        task.setPerson(rs.getString("person"));
        if (rs.getTimestamp("idate") != null) {
            task.setInsertDate(rs.getTimestamp("idate").toLocalDateTime());
        }
        if (rs.getTimestamp("udate") != null) {
            task.setLastUpdate(rs.getTimestamp("udate").toLocalDateTime());
        }

        return task;
    }
}
