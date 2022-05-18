package com.micro_wins.holder;

import com.micro_wins.view.main.AddTaskPane;
import com.micro_wins.view.main.UpcomingPane;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 18/05/2022 - 4:13 AM
 */

public final class AddTaskHolder {
    private AddTaskPane addTaskPane ;
    private final static AddTaskHolder INSTANCE = new AddTaskHolder() ;

    private AddTaskHolder () {}

    public static AddTaskHolder getInstance() {return INSTANCE ;}

    public void setAddTaskPane (AddTaskPane addTaskPane) {
        this.addTaskPane = addTaskPane ;
    }

    public AddTaskPane getAddTaskPane () {
        return addTaskPane ;
    }
}
