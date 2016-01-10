package cn.goodman.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.goodman.service.WordExcelToHtml;

/**
 * Servlet implementation class RenderServlet
 */
public class RenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(RenderServlet.class);
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RenderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * 测试方便使用的GET请求。如果想URL中不暴漏文档的目录位置，可以改成使用POST请求
	 * 修改方法为实现doPost方法
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("GBK");
		response.setCharacterEncoding("GBK");
		String fileName = request.getParameter("file");
		log.debug(fileName);
		
//		BufferedInputStream bis = null;   
//		URL url = null; 
//		HttpURLConnection httpUrl = null; // 建立链接   
//		url = new URL(docUrl); 
//		httpUrl = (HttpURLConnection) url.openConnection();// 连接指定的资源   
//		httpUrl.connect();// 获取网络输入流 
//		bis = new BufferedInputStream(httpUrl.getInputStream());   
//		String bodyText = null; 
//		WordExtractor ex = new WordExtractor(bis);   
//		bodyText = ex.getText(); 
		
		String htmlStr = new String();
		try {
			String filePath = LevelThreeServlet.class.getClassLoader().getResource(fileName).getPath();
			htmlStr = WordExcelToHtml.getWordAndStyle(filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().write(e.getMessage()); 
		}
		response.getWriter().write(htmlStr); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
