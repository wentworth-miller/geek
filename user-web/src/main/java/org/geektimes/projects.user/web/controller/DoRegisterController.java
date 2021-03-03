package org.geektimes.projects.user.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.service.UserServiceFactory;
import org.geektimes.web.mvc.controller.RestController;

/**
 * @author xubin
 * @date 2021/3/3 21:24
 */
@Path("/do")
public class DoRegisterController implements RestController {

    @Path("/register")
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        UserService userService = UserServiceFactory.getInstance();
        User user = new User();
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("pwd"));
        boolean register = userService.register(user);
        if (register) {
            request.getServletContext().getRequestDispatcher("/show.jsp").forward(request, response);
        } else {
            response.sendRedirect("/register/index");
        }
    }
}
