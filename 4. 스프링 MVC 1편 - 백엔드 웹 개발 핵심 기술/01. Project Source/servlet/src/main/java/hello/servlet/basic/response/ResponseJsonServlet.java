package hello.servlet.basic.response;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.servlet.basic.HelloData;

@WebServlet(name="responseJsonServlet", urlPatterns="/response-json")
public class ResponseJsonServlet extends HttpServlet{

	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		HelloData data = new HelloData();
				  data.setUsername("kim");
				  data.setAge(20);
	    String result = objectMapper.writeValueAsString(data);
	    
	    response.getWriter().write(result);
	    // aa
	}

}
