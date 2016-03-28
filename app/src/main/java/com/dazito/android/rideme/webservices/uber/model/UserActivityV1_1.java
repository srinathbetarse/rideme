package com.dazito.android.rideme.webservices.uber.model;

import java.util.List;

/**
 * Created by Pedro on 19-01-2015.
 */
public class UserActivityV1_1 {

    public final int offset;
    public final int limit;
    public final int count;
    public final List<HistoryV1_1> history;

    public UserActivityV1_1(int offset, int limit, int count, List<HistoryV1_1> history) {
        this.offset = offset;
        this.limit = limit;
        this.count = count;
        this.history = history;
    }
}
