package com.micro_wins.holder;

import com.micro_wins.view.main.InboxPane;
import com.micro_wins.view.main.UpcomingPane;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 18/05/2022 - 12:02 PM
 */


public final class InboxHolder {
    private InboxHolder inboxPane ;
    private final static InboxHolder INSTANCE = new InboxHolder() ;

    private InboxHolder () {}

    public static InboxHolder getInstance () {
        return INSTANCE ;
    }

    public void getInboxPane (InboxHolder inboxPane) {
        this.inboxPane = inboxPane ;
    }

    public InboxHolder getInboxPane () {
        return inboxPane ;
    }
}
