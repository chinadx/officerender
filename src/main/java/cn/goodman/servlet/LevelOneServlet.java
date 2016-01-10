package cn.goodman.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.goodman.service.ConfigCache;

/**
 * Servlet implementation class RenderServlet
 */
public class LevelOneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(LevelOneServlet.class);
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LevelOneServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setCharacterEncoding("UTF-8");
		log.info(request.getHeader("User-Agent"));
		String htmlStr;
		if(request.getHeader("User-Agent").indexOf("MSIE 7.0") > 0) {
			htmlStr = ConfigCache.getInstance().getHtmlTemplateLevel_1_IE7();
		}
		else {
			htmlStr = ConfigCache.getInstance().getHtmlTemplateLevel_1_IE8();
		}
		log.info(htmlStr);
		response.getWriter().write(htmlStr); 

	}

}
