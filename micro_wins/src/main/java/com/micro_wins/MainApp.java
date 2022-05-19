
/**
 * @author Nyamka
 * @project micro-wins-task-management
 */

package com.micro_wins;
import com.micro_wins.conn.ConnectionUtils;
import com.micro_wins.constant.Functions;
import com.micro_wins.holder.InitUserHolder;
import com.micro_wins.holder.UserHolder;
import com.micro_wins.model.User;
import com.micro_wins.view.StageManager;
import com.micro_wins.view.login.LoginPane;
import javafx.application.Application;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApp implements ApplicationListener<StageReadyEvent> {

    private static StageManager STAGE_MANAGER;

    private static Locale LOCALE = Locale.ENGLISH;

    @Autowired
    ConfigurableApplicationContext springAppContext;

    /**
     * 1. runs Java application. Neither SpringContext nor JavaFx context is initialized in this stage
     */
    public static void main(String[] args){

        /**
         * Программыг анх run хийхэд тухайн программыг ажиллуулж байгаа төхөөрөмжийг бүртгэж, хэрэглэгч үүсгэх бөгөөд цааш тухайн хэрэглэгчийн мэдээллээр программыг ашиглах юм.
         */
        final String userName = System.getProperty("user.name");
        String deviceName = "Unknown";

        try {
            InetAddress inetAddress;
            inetAddress = InetAddress.getLocalHost();
            deviceName = inetAddress.getHostName();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }

        final int deviceId = Math.abs(deviceName.hashCode())/10000;

        User initUser = new User();
        initUser.setUserName(userName);
        initUser.setDeviceName(deviceName);
        initUser.setDeviceId(deviceId);
        initUser.setUserStatus(1);

        /**
         * Анх програм ажиллахад төхөөрөмжийн мэдээллийг авч шинэ хэрэглэгч үүсгэх тул төхөөрөмжийн мэдээллийг holder - т хадгалав.
         * Уг хэрэглэгчийн өмнө үүссэн эсэхийг шалгаж, үүсээгүй бол үүсгэж, үүссэн байгаа бол програм цааш үргэлжлэн ажиллана.
         */
        InitUserHolder.getInstance().setUser(initUser);

        /**
         * 2. Start JavaFX application in another Thread (by calling com.sun.javafx.application.LauncherImpl#run())
         */
        Application.launch(SpringBootJavaFxApplication.class, args);
    }

    /**
     * 6. callback method. Catching event produced by SpringBootJavaFxApplication#start() method, once the initialization of Spring context, JavaFx context and FxWeaver context is done
     * @param event
     */
    @Override
    public void onApplicationEvent(StageReadyEvent event)
    {
        STAGE_MANAGER = springAppContext.getBean(StageManager.class, event.stage);
        STAGE_MANAGER.rebuildStage(LoginPane.class);
    }

    public static StageManager getStageManager()
    {
        return STAGE_MANAGER;
    }

    public static void setLOCALE(Locale locale)
    {
        MainApp.LOCALE = locale;
    }

    public static Locale getLOCALE()
    {
        return LOCALE;
    }

}