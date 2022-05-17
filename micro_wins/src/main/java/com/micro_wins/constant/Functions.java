package com.micro_wins.constant;

import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 14/05/2022 - 5:16 PM
 */
public enum Functions {
    IS_INT,
    LOCALDATE_TO_DATE,
    DATE_TO_LOCALDATE,
    DELETE_CONFIRM_ALERT,
    TODAY_DATE_TO_STRING,
    DATE_TO_STRING ;

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

    public LocalDate dateToLocalDate (Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate ;
    }

    public boolean deleteConfirmAlert(String title, String msg, String yesMsg, String noMsg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.initModality(Modality.APPLICATION_MODAL);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/styles/deleteConfirmAlert.css").toExternalForm());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            return true;
        return false;
    }

    public String todayDateToString () {
        Calendar calendar = Calendar.getInstance() ;
        calendar.setTime(Functions.LOCALDATE_TO_DATE.localDateToDate(LocalDate.now()));
        int month = calendar.get(Calendar.MONTH) ;
        int day = calendar.get(Calendar.DAY_OF_MONTH) ;
        int year = calendar.get(Calendar.YEAR) ;
        String monthLocalizedName = new DateFormatSymbols().getMonths()[month] ;
        return monthLocalizedName + " " + day +", " + year ;
    }

    public String dateToString (Date date) {
        Calendar calendar = Calendar.getInstance() ;
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) ;
        int day = calendar.get(Calendar.DAY_OF_MONTH) ;
        int year = calendar.get(Calendar.YEAR) ;
        String monthLocalizedName = new DateFormatSymbols().getMonths()[month] ;
        return monthLocalizedName + " " + day +", " + year ;
    }
}
