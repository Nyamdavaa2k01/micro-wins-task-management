
/**
 * @author Nyamka
 * @project micro-wins-task-management
 */

package com.micro_wins;
import com.micro_wins.conn.ConnectionUtils;
import com.micro_wins.holder.InitUserHolder;
import com.micro_wins.holder.UserHolder;
import com.micro_wins.model.User;
import com.micro_wins.view.StageManager;
import com.micro_wins.view.login.LoginPane;
import com.micro_wins.view.main.TodayPane;
import javafx.application.Application;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApp implements ApplicationListener<StageReadyEvent> {

    private static StageManager STAGE_MANAGER;

    private static Locale LOCALE = Locale.ENGLISH;

    private static int userCount = 0;

    @Autowired
    ConfigurableApplicationContext springAppContext;

    /**
     * 1. runs Java application. Neither SpringContext nor JavaFx context is initialized in this stage
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Logger logger = LoggerFactory.getLogger(MainApp.class);
        logger.info("Program starting");

        Connection conn = ConnectionUtils.getConnection();

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
         * Тухайн төхөөрөмж хэрэглэгчээр бүртгэгдсэн эсэхийн шалгана. Хэрэв уг төхөөрөмж бүртгэлтэй бол дахин нэвтрэх шаардлагагүй бөгөөд өмнө нэвтэрснийг гаргах юм.
         */
        try{
            String sql = "SELECT COUNT(*) as user_count FROM mw_user WHERE user_name = ? AND device_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setInt(2, deviceId);
            ResultSet rs = stmt.executeQuery();

            //Extract result from ResultSet rs
            while(rs.next()){
                userCount = rs.getInt("user_count");
            }

            if(userCount == 1){
                String userSql = "SELECT * FROM mw_user WHERE user_name = ? AND device_id = ?";
                PreparedStatement userStmt = conn.prepareStatement(userSql);
                userStmt.setString(1, userName);
                userStmt.setInt(2, deviceId);
                ResultSet userRes = userStmt.executeQuery();

                //Extract result from ResultSet user
                User oldUser = new User();
                while (userRes.next()){
                    oldUser.setUserId(userRes.getInt("user_id"));
                    oldUser.setUserName(userRes.getString("user_name"));
                    oldUser.setDeviceId(userRes.getInt("device_id"));
                    oldUser.setDeviceName(userRes.getString("device_name"));
                    oldUser.setUserStatus(userRes.getInt("user_status"));
                }

                UserHolder.getInstance().setUser(oldUser);
            }

            // close ResultSet rs
            rs.close();
        } catch(SQLException s){
            s.printStackTrace();
        }

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
        if(userCount == 0){
            STAGE_MANAGER.rebuildStage(LoginPane.class);
        }else{
            STAGE_MANAGER.rebuildStage(TodayPane.class);
        }
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