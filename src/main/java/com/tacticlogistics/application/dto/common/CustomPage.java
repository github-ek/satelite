package com.tacticlogistics.application.dto.common;

import java.util.ArrayList;
import java.util.List;

public class CustomPage<T> {
    private long recordsTotal;
    private List<T> data;

    public CustomPage(long recordsTotal, List<T> data) {
        super();
        if (data == null) {
            data = new ArrayList<>();
        }
        
        if (recordsTotal < 0) {
            recordsTotal = 0;
        }

        this.recordsTotal = recordsTotal;
        this.data = data;
    }

    public int getDraw() {
        return 1;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public long getRecordsFiltered() {
        return getRecordsTotal();
    }

    public List<T> getData() {
        return data;
    }

}
