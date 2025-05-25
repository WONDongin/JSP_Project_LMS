package filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
// 원동인
public class UriFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();

        // contextPath 제거한 상대 URI
        String relativeURI = requestURI.substring(contextPath.length());

        // request attribute에 저장
        request.setAttribute("relativeURI", relativeURI);

        // 다음 필터/서블릿 실행
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }
}
