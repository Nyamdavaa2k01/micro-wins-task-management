package com.micro_wins.holder;

import com.micro_wins.view.main.TodayPane;
import com.micro_wins.view.main.UpcomingPane;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 18/05/2022 - 12:04 PM
 */



public final class TodayHolder {
    private TodayPane todayPane ;
    private final static TodayHolder INSTANCE = new TodayHolder() ;

    private TodayHolder () {}

    public static TodayHolder getInstance () {
        return INSTANCE ;
    }

    public void setTodayPane (TodayPane todayPane) {
        this.todayPane = todayPane ;
    }

    public TodayPane getTodayPane () {
        return todayPane ;
    }
}

