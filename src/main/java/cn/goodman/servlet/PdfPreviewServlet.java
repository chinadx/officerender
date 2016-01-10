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
public class PdfPreviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(PdfPreviewServlet.class);
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PdfPreviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String file = request.getParameter("file");
		log.info("----------------------file="+file);
//		file=new String(file.getBytes("ISO-8859-1"), "UTF-8");  
//		log.info("----------------------file="+file);
		String htmlStr =ConfigCache.getInstance().getPdf();

		htmlStr = htmlStr.replace("{{file}}", file);
		
		response.setCharacterEncoding("UTF-8");
		
		
		log.info(htmlStr);
		response.getWriter().write(htmlStr); 

	}

}
