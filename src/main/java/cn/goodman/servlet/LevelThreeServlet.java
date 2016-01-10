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
public class LevelThreeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Logger log = Logger.getLogger(LevelThreeServlet.class);
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LevelThreeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String titleEn = request.getParameter("title2");
		String index = request.getParameter("index");
		log.info(titleEn + "------" + index);
		String htmlStr;
		if(request.getHeader("User-Agent").indexOf("MSIE 7.0") > 0) {
			htmlStr = ConfigCache.getInstance().getHtmlTemplateLevel_3_IE7();
		}
		else {
			htmlStr =ConfigCache.getInstance().getHtmlTemplateLevel_3_IE8();
		}
		
		String jsonString = ConfigCache.getInstance().getUrlJson();
		
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		JSONObject level2Obj = (JSONObject) jsonObj.get(titleEn);
		
		JSONArray jsonArray = level2Obj.getJSONArray("list");
		JSONObject objtmp = (JSONObject) jsonArray.getJSONObject(Integer.parseInt(index)).get("extra");
		
		htmlStr = htmlStr.replace("{{title}}", jsonArray.getJSONObject(Integer.parseInt(index)).getString("title"));
		htmlStr = htmlStr.replace("{{extra.banshizhinan}}", objtmp.getString("banshizhinan"));
		htmlStr = htmlStr.replace("{{extra.wangshangshenqing}}", objtmp.getString("wangshangshenqing"));
		htmlStr = htmlStr.replace("{{extra.biaogexiazai}}", objtmp.getString("biaogexiazai"));
		htmlStr = htmlStr.replace("{{extra.jieguochaxun}}", objtmp.getString("jieguochaxun"));
		htmlStr = htmlStr.replace("{{extra.changjianwenti}}", objtmp.getString("changjianwenti"));
		
		String pdfFile = "";
		if(objtmp.containsKey("shifanyangbiao")) {
		pdfFile = objtmp.getString("shifanyangbiao");
		}
		if(pdfFile!=null && !pdfFile.equals("") && !pdfFile.equals("#")) {
			String tmp = "window.open('" 
					+ request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()
					+ request.getContextPath() + "/preview?file=" + objtmp.getString("shifanyangbiao") + "'"
					+ ", 'newwindow','toolbar=no,menubar=no,scrollbars=no,location=no,status=no')";
			htmlStr = htmlStr.replace("{{preview}}", tmp);
		}
		else {
			String tmp = "return false;";
			htmlStr = htmlStr.replace("{{preview}}", tmp);
		}

		response.setCharacterEncoding("UTF-8");
		
		
		log.info(htmlStr);
		response.getWriter().write(htmlStr); 

	}

	
	
	
	
	
	
	
	
	

}
