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
    LOCALDATE_TO_DATE,
    DATE_TO_LOCALDATE;
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

    public Date localDateToDate(LocalDate localDate)
    {
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    };

    public LocalDate DateToLocalDate (Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate ;
    }
}
