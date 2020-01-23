package taskscheduler.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.StringJoiner;

public class DataTableResult implements Serializable {

   private static final long serialVersionUID = 7825645368532289922L;

    private long draw;
    private long recordsTotal;
    private long recordsFiltered;
    private Object[][] data;

    public DataTableResult() {
    }

    public long getDraw() {
        return draw;
    }

    public void setDraw(long draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DataTableResult.class.getSimpleName() + "[", "]")
                .add("draw=" + draw)
                .add("recordsTotal=" + recordsTotal)
                .add("recordsFiltered=" + recordsFiltered)
                .add("data=" + Arrays.toString(data))
                .toString();
    }
}
