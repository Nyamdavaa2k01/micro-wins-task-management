package com.micro_wins.constant;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 15/05/2022 - 6:50 PM
 * @purpose Dictionary утгуудыг дахин дахин шалгахаас зайлсхийх болон Dictionary утга өөрчлөгдөхөд
 * хялбар байдлаар зохицуулахын тулд ашигласан.
 * @definition Програмд ашиглагдах утгууд нь өгөгдлийн сангийн dictionary утгуудыг тусгах ёстой тул
 * тэдгээрийг тогтмол болгон ашигласан.
 */

public class ConstantDictionaryValues {
    private final int TASK_STATUS_OPEN = 1 ;
    private final int TASK_STATUS_WORKING = 2 ;
    private final int TASK_STATUS_POSTPONED = 3 ;
    private final int TASK_STATUS_COMPLETED = 4 ;

    private final int TASK_USER_ACTIVE = 1 ;
    private final int TASK_USER_INACTIVE = 0 ;

    private final int TASK_PRIORITY_URGENT = 1 ;
    private final int TASK_PRIORITY_HIGH = 2 ;
    private final int TASK_PRIORITY_MEDIUM = 3 ;
    private final int TASK_PRIORITY_LOW = 4 ;

    public int getTASK_STATUS_OPEN() {
        return TASK_STATUS_OPEN;
    }

    public int getTASK_STATUS_WORKING() {
        return TASK_STATUS_WORKING;
    }

    public int getTASK_STATUS_POSTPONED() {
        return TASK_STATUS_POSTPONED;
    }

    public int getTASK_STATUS_COMPLETED() {
        return TASK_STATUS_COMPLETED;
    }

    public int getTASK_USER_ACTIVE() {
        return TASK_USER_ACTIVE;
    }

    public int getTASK_USER_INACTIVE() {
        return TASK_USER_INACTIVE;
    }

    public int getTASK_PRIORITY_URGENT() {
        return TASK_PRIORITY_URGENT;
    }

    public int getTASK_PRIORITY_HIGH() {
        return TASK_PRIORITY_HIGH;
    }

    public int getTASK_PRIORITY_MEDIUM() {
        return TASK_PRIORITY_MEDIUM;
    }

    public int getTASK_PRIORITY_LOW() {
        return TASK_PRIORITY_LOW;
    }
}
