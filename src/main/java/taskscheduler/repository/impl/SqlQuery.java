package taskscheduler.repository.impl;

public final class SqlQuery {

    public static final String GET_TASK_LIST_PAGING = "select id, task_name, description, start_date, end_date, person, idate, udate from task " +
            "where concat(id, task_name, description, date_format(start_date, '%d.%m.%Y'), date_format(end_date, '%d.%m.%Y'), " +
            "person, date_format(idate, '%d.%m.%Y %H:%i;%S'), ifnull(date_format(udate, '%d.%m.%Y %H:%i;%S'), '')) " +
            "like :filter and status = 1 " +
            "order by {SORT_COLUMN} {SORT_DIRECTION} " +
            "limit :start, :length";

    public static final String GET_TASK_COUNT = "select count(id) as task_count from task " +
            "where status = 1";

    public static final String GET_FILTERED_TASK_COUNT = "select count(*) task_count from task " +
            "where concat(id, task_name, description, date_format(start_date, '%d.%m.%Y'), date_format(end_date, '%d.%m.%Y'), " +
            "person, date_format(idate, '%d.%m.%Y %H:%i;%S'), ifnull(date_format(udate, '%d.%m.%Y %H:%i;%S'), '')) " +
            "like :filter and status = 1";

    public static final String ADD_TASK = "insert into task(id, task_name, description, start_date, end_date, person) " +
            "values(null, :task_name, :description, :start_date, :end_date, :person)";

    public static final String DELETE_TASK_BY_ID = "update task set status = 0 where id = :id";

    public static final String UPDATE_TASK = "update task set task_name = :task_name, description = :description, " +
            "start_date = :start_date, end_date = :end_date, person = :person, udate = :udate " +
            "where id = :id and status = 1";

    public static final String GET_TASK_BY_ID = "select id, task_name, description, start_date, end_date, person, idate, udate " +
            "from task " +
            "where id = :id and status = 1";

    public static final String CHECK_TASK = "select count(id) as task_count from task where task_name = :task_name and status = 1";
}
