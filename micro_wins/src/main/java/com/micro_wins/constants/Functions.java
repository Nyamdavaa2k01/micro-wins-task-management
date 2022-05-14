package com.micro_wins.constants;

import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 14/05/2022 - 5:16 PM
 */
public enum Functions {
    IS_INT,
    LOCALDATE_TO_DATE;
    public boolean isInt(TextField f, String msg)
    {
            try
            {
                Integer.parseInt(f.getText());
                return true;
            }
            catch (NumberFormatException e)
            {
                System.out.println(msg);
                return false;
            }
    }
    public Date localDataToDate(LocalDate localDate)
    {
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    };
}
