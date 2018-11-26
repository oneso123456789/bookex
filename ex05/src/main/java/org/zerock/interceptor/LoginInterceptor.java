package org.zerock.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// LoginInterceptor는 '/loginPost'로 접근하도록 설정하는것을 목적으로 작성
// preHandle()에서는 기존 HttpSession에 남아있는 정보가 있는 경우에는 정보를 삭제
// postHandle()에서는 UserController에서 'userVo'라는 이름으로 객체를 담아둔 상태이므로, 이 상태를 체크해서 HttpSession에 저장
public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	
	private static final String LOGIN = "login";
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		ModelMap modelMap = modelAndView.getModelMap();
		Object userVO = modelMap.get("userVO");
		
		if(userVO != null) {
			
			logger.info("new login success");
			session.setAttribute(LOGIN, userVO);
//			response.sendRedirect("/");
			
			Object dest = session.getAttribute("dest");
			
			response.sendRedirect(dest != null ? (String) dest : "/");
		}
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute(LOGIN) != null) {
			logger.info("clear login data before");
			session.removeAttribute(LOGIN);
		}
		
		return true;
	}
	
	
}
