package taskscheduler.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import taskscheduler.domain.Task;
import taskscheduler.repository.ValidationRepository;
import taskscheduler.service.ValidationService;

@Repository
public class ValidationRepositoryJdbcImpl implements ValidationRepository {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public boolean isDuplicateTask(String taskName) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("task_name", taskName);
        Integer count = namedParameterJdbcTemplate.queryForObject(SqlQuery.CHECK_TASK, params, Integer.class);
        return count > 0;
    }
}
