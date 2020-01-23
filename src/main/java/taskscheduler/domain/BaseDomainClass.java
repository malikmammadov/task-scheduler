package taskscheduler.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class BaseDomainClass implements Serializable {
    private static final long serialVersionUID = -1708644896847114020L;

    protected long id;
    protected LocalDateTime insertDate;
    protected LocalDateTime lastUpdate;
    protected int status;

    public BaseDomainClass() {
        this(0, null, null, 1);
    }

    public BaseDomainClass(long id, LocalDateTime insertDate, LocalDateTime lastUpdate, int status) {
        this.id = id;
        this.insertDate = insertDate;
        this.lastUpdate = lastUpdate;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BaseDomainClass.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("insertDate=" + insertDate)
                .add("lastUpdate=" + lastUpdate)
                .add("status=" + status)
                .toString();
    }
}
