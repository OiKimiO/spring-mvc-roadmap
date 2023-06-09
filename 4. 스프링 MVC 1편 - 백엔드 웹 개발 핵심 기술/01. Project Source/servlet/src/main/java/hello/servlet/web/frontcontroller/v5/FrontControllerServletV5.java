package hello.servlet.web.frontcontroller.v5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

	// 매핑 정보의 값을 ControllerV3, ControllerV4같은 인터페이스에서 아무 값이나 받을 수 있는 Object로 변경
	private Map<String,Object> handlerMappingMap   = new HashMap<>();
	private List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();
	
	public FrontControllerServletV5() {
		// 핸들러 매핑 초기화
		initHandlerMappingMap();
		
		// 어댑터 초기화
		initHandlerAdapters();
	}

	private void initHandlerMappingMap() {
		handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members/save", 	  new MemberSaveControllerV3());
		handlerMappingMap.put("/front-controller/v5/v3/members", 		  new MemberListControllerV3());
		
		handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
		handlerMappingMap.put("/front-controller/v5/v4/members/save", 	  new MemberSaveControllerV4());
		handlerMappingMap.put("/front-controller/v5/v4/members", 		  new MemberListControllerV4());
	}

	private void initHandlerAdapters() {
		handlerAdapters.add(new ControllerV3HandlerAdapter());
		handlerAdapters.add(new ControllerV4HandlerAdapter());
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Object handler = getHandler(request);
		
		if(handler == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		MyHandlerAdapter adapter = getHandlerAdapter(handler);
		ModelView mv = adapter.handle(request,response,handler);
		
		MyView view = viewResolver(mv.getViewName());
		view.render(mv.getModel(), request, response);
	}
	
	private Object getHandler(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		return handlerMappingMap.get(requestURI);
	}

	private MyHandlerAdapter getHandlerAdapter(Object handler) {
		for(MyHandlerAdapter adapter : handlerAdapters) {
			// handler를 처리할 수 있는 어댑터를 adapter.supports(handler)를 통해 찾는다.
			if(adapter.supports(handler)) {
				return adapter;
			}
		}
		
		throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler="+handler);
	}

	private Map<String,String> createParamMap(HttpServletRequest request){
		Map<String,String> paramMap = new HashMap<>();
		
		request.getParameterNames().asIterator()
			   .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
		return paramMap;
	}
	
	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp"); 
	}
}
