package UniProject.web.filter;

import UniProject.utils.Paths;
import UniProject.model.Role;
import UniProject.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(Paths.URL_APPLICANT)
public class ApplicantFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute("user");
        if (!Role.APPLICANT.equals(user.getRole())){
            request.setAttribute("errorMsg","You have no access.\nLog in as user");
            request.getServletContext().getRequestDispatcher(Paths.PAGE_ERROR).forward(request,response);
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
