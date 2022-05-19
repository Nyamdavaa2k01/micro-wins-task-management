package com.micro_wins.constant;
/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 11/05/2022 - 4:33 PM
 * @purpose Өргөн хэрэглэгдэх өнгөний утгуудыг тогтмол болгон ашигласан ба эдгээр утгуудыг
 * програмын бусад хэсгүүдээс дуудан ашиглах боломжтой.
 * @definition Классын бүх attribute нь тогтмол утгууд ба эдгээр утгуудыг зөвхөн get хийж авах боломжтой.
 */

public class ConstantColors {
    private final String PRIORITY1_COLOR = "#ec3838" ;
    private final String PRIORITY2_COLOR = "#ffca38" ;
    private final String PRIORITY3_COLOR = "#38a8f3" ;
    private final String PRIORITY4_COLOR = "#000000" ;
    private final String INBOX_PROJECT_COLOR = "183597" ;
    private final String WARNING_COLOR = "#ff00004d;";
    private final String WHITE = "#fff";
    private final String OPEN_TASK_COLOR =      "#8980ef";
    private final String WORKING_TASK_COLOR =   "#f25333";
    private final String POSTPONED_TASK_COLOR = "#ff13bf";
    private final String COMPLETED_TASK_COLOR = "#4caf50";

    public String getPRIORITY1_COLOR() {
        return PRIORITY1_COLOR;
    }

    public String getPRIORITY2_COLOR() {
        return PRIORITY2_COLOR;
    }

    public String getPRIORITY3_COLOR() {
        return PRIORITY3_COLOR;
    }

    public String getPRIORITY4_COLOR() {
        return PRIORITY4_COLOR;
    }

    public String getWARNING_COLOR(){ return WARNING_COLOR; }

    public String getWHITE(){ return WHITE; }

    public String getINBOX_PROJECT_COLOR() {
        return INBOX_PROJECT_COLOR;
    }

    public String getOPEN_TASK_COLOR() {
        return OPEN_TASK_COLOR;
    }

    public String getWORKING_TASK_COLOR() {
        return WORKING_TASK_COLOR;
    }

    public String getPOSTPONED_TASK_COLOR() {
        return POSTPONED_TASK_COLOR;
    }

    public String getCOMPLETED_TASK_COLOR() {
        return COMPLETED_TASK_COLOR;
    }

    public String getTaskColorByNumber(int number){
        switch (number){
            case 2:{
                return WORKING_TASK_COLOR;
            }
            case 3:{
                return POSTPONED_TASK_COLOR;
            }
            case 4:{
                return COMPLETED_TASK_COLOR;
            }
            default:{
                return OPEN_TASK_COLOR;
            }
        }
    }
}
