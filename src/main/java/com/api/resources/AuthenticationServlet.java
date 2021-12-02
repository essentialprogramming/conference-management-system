package com.api.resources;


import org.springframework.web.context.support.SpringBeanAutowiringSupport;


import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("account/*")
public class AuthenticationServlet extends HttpServlet{

    private static final String LOGIN = "sign-in";
    private static final String REGISTER = "register";

    @Inject
    private ServletContext context;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }


    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        // WebApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        // final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        // beanFactory.autowireBean(this);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] path = request.getPathInfo().split("/");
        switch (path[path.length - 1]) {
            case LOGIN:
                context.getRequestDispatcher("/auth/account/login.html").forward(request, response);
                break;
            case REGISTER:
                context.getRequestDispatcher("/auth/account/register.html").forward(request, response);
                break;

            default:
               break;
        }
    }

}
