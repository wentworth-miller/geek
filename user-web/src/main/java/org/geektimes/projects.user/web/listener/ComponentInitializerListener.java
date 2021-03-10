package org.geektimes.projects.user.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.geektimes.context.ComponentContext;
import org.geektimes.projects.user.sql.DataSourceManager;

@WebListener
public class ComponentInitializerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataSourceManager.init();
        ComponentContext componentContext = new ComponentContext();
        componentContext.init(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
