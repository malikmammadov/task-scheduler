package taskscheduler.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class DataTableRequest implements Serializable {

    private static final long serialVersionUID = -6689139681591399872L;

    private int draw;
    private int start;
    private int length;
    private int sortColumn;
    private String sortDirection;
    private String filter;

    public DataTableRequest() {
        this(0, 0, 0, 0, "", "");
    }

    public DataTableRequest(int draw, int start, int length, int sortColumn, String sortDirection, String filter) {
        this.draw = draw;
        this.start = start;
        this.length = length;
        this.sortColumn = sortColumn;
        this.sortDirection = sortDirection;
        this.filter = filter;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(int sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DataTableRequest.class.getSimpleName() + "[", "]")
                .add("draw=" + draw)
                .add("start=" + start)
                .add("length=" + length)
                .add("sortColumn=" + sortColumn)
                .add("sortDirection='" + sortDirection + "'")
                .add("filter='" + filter + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataTableRequest that = (DataTableRequest) o;
        return getDraw() == that.getDraw() &&
                getStart() == that.getStart() &&
                getLength() == that.getLength() &&
                getSortColumn() == that.getSortColumn() &&
                getSortDirection().equals(that.getSortDirection()) &&
                getFilter().equals(that.getFilter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDraw(), getStart(), getLength(), getSortColumn(), getSortDirection(), getFilter());
    }
}
