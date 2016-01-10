package cn.goodman.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import cn.goodman.service.ConfigCache;

/**
 * Servlet implementation class RenderServlet
 */
public class LevelTwoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(LevelTwoServlet.class);
    

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LevelTwoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String titleEn = request.getParameter("title");
		String htmlStr;
		if(request.getHeader("User-Agent").indexOf("MSIE 7.0") > 0) {
			htmlStr = ConfigCache.getInstance().getHtmlTemplateLevel_2_IE7();
		}
		else {
			htmlStr =ConfigCache.getInstance().getHtmlTemplateLevel_2_IE8();
		}
		
		String jsonString = ConfigCache.getInstance().getUrlJson();
		
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		JSONObject level2Obj = (JSONObject) jsonObj.get(titleEn);
		String title = level2Obj.getString("title");
		
		
		JSONArray jsonArray = level2Obj.getJSONArray("list");
		
		htmlStr = htmlStr.replace("{{title}}", title);
		
		String liStr = "";
		for (int i = 0; i < jsonArray.size(); i++) {
			String level3Title = (String) jsonArray.getJSONObject(i).get("title");				
		
			liStr = liStr 
			+"					<li onclick=\"javascript:window.location.href='level3?title2=" +titleEn+ "&index=" +i+ "'\">"+ level3Title + "\n"
			+"					</li>" + "\n";
		}
		htmlStr = htmlStr.replace("{{li}}", liStr);		

		response.setCharacterEncoding("UTF-8");
		
		
		log.info(htmlStr);
		response.getWriter().write(htmlStr); 

	}

}
