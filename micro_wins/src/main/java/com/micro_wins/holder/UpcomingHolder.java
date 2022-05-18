package com.micro_wins.holder;

import com.micro_wins.model.Project;
import com.micro_wins.view.main.UpcomingPane;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 18/05/2022 - 12:41 AM
 * @purpose UpcomingPane-ийн Instance-ийг бусад классаас авах зорилгоор энэхүү классыг үүсгэсэн.
 * @definition UpcomingHolder().getInstance.getUpcomingPane гэснээр бусад классаас UpcomingPane-ийн
 * Instance-г авах боломжтой болно.
 */

public final class UpcomingHolder {
    private UpcomingPane upcomingPane ;
    private final static UpcomingHolder INSTANCE = new UpcomingHolder() ;

    private UpcomingHolder () {}

    public static UpcomingHolder getInstance () {
        return INSTANCE ;
    }

    public void setUpcomingPane (UpcomingPane upcomingPane) {
        this.upcomingPane = upcomingPane ;
    }

    public UpcomingPane getUpcomingPane () {
        return upcomingPane ;
    }
}
