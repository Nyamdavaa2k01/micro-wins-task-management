package com.micro_wins.constants;

import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.time.ZoneId;
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
    DELETE_CONFIRM_ALERT;

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
//        dialogPane.getStyleClass().add("myDialog");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            return true;
        return false;
    }
}
