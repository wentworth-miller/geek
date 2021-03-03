package org.geektimes.projects.user.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.geektimes.projects.user.sql.DataSourceManager;

@WebListener
public class DBConnectionInitializerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataSourceManager.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
