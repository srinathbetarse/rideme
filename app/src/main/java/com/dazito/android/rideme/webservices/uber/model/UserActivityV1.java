package com.dazito.android.rideme.webservices.uber.model;

import java.util.List;

/**
 * Created by Pedro on 20-01-2015.
 */
public class UserActivityV1 {

    public final int offset;
    public final int limit;
    public final int count;
    public final List<HistoryV1> history;

    public UserActivityV1(int offset, int limit, int count, List<HistoryV1> history) {
        this.offset = offset;
        this.limit = limit;
        this.count = count;
        this.history = history;
    }
}
