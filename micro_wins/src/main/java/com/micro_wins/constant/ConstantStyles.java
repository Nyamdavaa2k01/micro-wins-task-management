package com.micro_wins.constant;

/**
 * @author Nyamka
 * @project micro-wins-task-management
 * @created 14/05/2022 - 6:52 PM
 * @purpose Өргөн хэрэглэгдэх style-уудыг тодорхойлсон.
 * @definition Програм хувьд listView доторх элементүүдийн
 *  style өөрчлөхөд төвөгтэй байсан тул тухайн тохиолдолд шууд setStyle ашиглан style-ийг нь өөрчлөсөн.
 *  Бусад тохиолдолд css-ийн замыг зааж өгсөн болохыг анхаарна уу.
 */
public class ConstantStyles {
    private final String DEFAULT_BUTTON_STYLE = "-fx-background-color:transparent; -fx-border-color:gray; -fx-cursor:hand;" ;
    private final String DEFAULT_BORDERED_BUTTON_STYLE = "-fx-background-color:transparent; -fx-border-color:gray; -fx-cursor:hand;" +
            "-fx-border-radius:10; -fx-background-radius:10;" ;


    public String getDEFAULT_BUTTON_STYLE() {
        return DEFAULT_BUTTON_STYLE;
    }
}
