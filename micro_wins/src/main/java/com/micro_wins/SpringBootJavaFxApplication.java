package com.micro_wins;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author Bagaa
 * @project micro-wins-task-management
 * @created 11/05/2022 - 11:10 AM
 */
public class SpringBootJavaFxApplication extends Application {

    private ConfigurableApplicationContext context;

    /**
     * 3. JavaFx application thread. Started in MainApp#main method
     * @throws Exception
     */
    @Override
    public void init() throws Exception
    {

        ApplicationContextInitializer<GenericApplicationContext> initializer =
                context ->
                {
                    /**
                     * register this class as a Spring bean
                     */
                    context.registerBean(Application.class, () -> SpringBootJavaFxApplication.this);
                    /**
                     * for demonstration, not really needed
                     */
                    context.registerBean(Parameters.class, this::getParameters);
                };

        /**
         * 4. initialize Spring application and run it. In the next step:
         * all @SpringBootApplication and @Configuration classes are going to be initialized
         * all @Bean classes are going to be initialized. Both inside our application and libraries (most importantly @Component SpringFxWeaver)
         */
        this.context = new SpringApplicationBuilder()
                /**
                 * register @SpringBootApplication class
                 */
                .sources(MainApp.class)
                .initializers(initializer)
                /**
                 * run with application parameters (from command line args of JavaFx parameters)
                 */
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        /**
         *  5. we are done with spring initialization in #init method. Now, throw Spring's ApplicationEvent which contains the primary stage. This stage
         *  is meant to be used by any registered event listener to build JavaFX UI
         */
        context.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() throws Exception
    {
        this.context.close();
        Platform.exit();
    }
}
