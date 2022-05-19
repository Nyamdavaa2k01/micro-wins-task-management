package com.micro_wins.constant;

import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
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
 * @purpose Нийтлэг хэрэглэгдэх функцуудийг олон дахин хэрэглэхээс сэргийлэн үүсгэсэн класс болно.
 * @definition Програмын бусад хэсэгт ашиглахдаа Functions.LOCALDATE_TO_DATE.localDateToDate(LocalDate LocalDateToConvert)
 * байдлаар ашиглана.
 */

public enum Functions {
    IS_INT,
    LOCALDATE_TO_DATE,
    DATE_TO_LOCALDATE,
    DELETE_CONFIRM_ALERT,

    INFORMATION_ALERT,
    TODAY_DATE_TO_STRING,
    DATE_TO_STRING,
    HEX_TO_RGB;

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
        ButtonType yesBtn = new ButtonType(yesMsg, ButtonBar.ButtonData.OK_DONE);
        ButtonType noBtn = new ButtonType(noMsg, ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, yesBtn, noBtn);
        alert.setTitle(title);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.initModality(Modality.APPLICATION_MODAL);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/styles/deleteConfirmAlert.css").toExternalForm());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yesBtn)
            return true;
        return false;
    }

    public void informationAlert(String title, String msg, String yesMsg) {
        ButtonType yesBtn = new ButtonType(yesMsg, ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, yesBtn);
        alert.setTitle(title);
        alert.initStyle(StageStyle.TRANSPARENT);
        alert.initModality(Modality.APPLICATION_MODAL);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/styles/deleteConfirmAlert.css").toExternalForm());
        alert.showAndWait();
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
    public Color hexToRgb(String colorStr, double alpha) {
        return Color.rgb(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16),
                alpha);
    }
}
