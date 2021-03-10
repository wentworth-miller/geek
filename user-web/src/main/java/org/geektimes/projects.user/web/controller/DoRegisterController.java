package org.geektimes.projects.user.web.controller;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.Path;

import org.apache.commons.lang.StringUtils;
import org.geektimes.context.ComponentContext;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.domain.dto.Const;
import org.geektimes.projects.user.service.UserService;
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

        // UserService userService = UserServiceFactory.getInstance();
        UserService userService = ComponentContext.getInstance().getComponent(Const.USER_SERVICE);
        User user = new User();
        user.setName("test");
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("pwd"));
        user.setPhoneNumber(request.getParameter("iphoneNum"));
        boolean register = userService.register(user);
        if (register) {
            request.getServletContext().getRequestDispatcher("/show.jsp").forward(request, response);
        } else {
            response.sendRedirect("/register/index");
        }
    }
}
