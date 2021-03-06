package UniProject.web.filter;


import UniProject.model.Role;
import UniProject.model.User;
import UniProject.utils.Paths;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * authorization filter
 * check if user log in
 **/

@WebFilter(value = {Paths.URL_SIGN_UP, Paths.URL_LOG_IN})
public class LogInSignUpFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user= (User) session.getAttribute("user");
        if (session.getAttribute("user") != null) {
            if (Role.ADMIN.equals(user.getRole())){
                response.sendRedirect(Paths.URL_ADMIN_HOME);
            } else {
                response.sendRedirect(Paths.URL_APPLICANT_HOME);
            }
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
