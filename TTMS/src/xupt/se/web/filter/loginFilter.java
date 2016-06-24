package xupt.se.web.filter;

import xupt.se.ttms.model.Employee;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by lc on 2016/6/14.
 */
public class loginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        // 获得在下面代码中要用的request,response,session对象
        HttpServletRequest sRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse sResponse = (HttpServletResponse) servletResponse;
        HttpSession session = sRequest.getSession();
        //设置编码
        sResponse.setContentType("text/html;charset=utf-8");
        // 获得用户请求的URI
        //String path = sRequest.getRequestURI();
//        System.out.println(path);

      /*  // 从session里取员工工号信息
        String emp = null;
        try {
            emp = ((Employee)(session.getAttribute("user"))).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        创建类Constants.java，里面写的是无需过滤的页面
        for (int i = 0; i < Constants.NoFilter_Pages.length; i++) {

            if (path.indexOf(Constants.NoFilter_Pages[i]) > -1) {
                chain.doFilter(servletRequest, servletResponse);
                return;
            }
        }*/

        // 登陆页面无需过滤
        /*if(path.indexOf("/login") > -1) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }*/

//        // 判断如果没有取到员工信息,就跳转到登陆页面
//        if (emp == null || "".equals(emp)) {
//            // 跳转到登陆页面
//            sResponse.sendRedirect("login");
//        } else {
//            // 已经登陆,继续此次请求
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
      filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}
