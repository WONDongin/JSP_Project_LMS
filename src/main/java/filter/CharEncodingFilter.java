package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class CharEncodingFilter implements Filter {
	private String encoding;


	@Override // 메서드가 오버라이딩된 메서드이므로 오버라이딩 조건 맞는지 판단
	public void init(FilterConfig filterConfig) throws ServletException {
		encoding = filterConfig.getInitParameter("encoding");
		if (encoding == null) {
			encoding = "UTF-8";
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
