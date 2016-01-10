package cn.goodman.service;

import cn.goodman.servlet.LevelTwoServlet;

public class ConfigCache {
	
	private String urlJson = null;
	private String htmlTemplateLevel_1_IE7 = null;
	private String htmlTemplateLevel_1_IE8 = null;
	private String htmlTemplateLevel_2_IE7 = null;
	private String htmlTemplateLevel_2_IE8 = null;
	private String htmlTemplateLevel_3_IE7 = null;
	private String htmlTemplateLevel_3_IE8 = null;
	private String pdf = null;
	
	private static ConfigCache single = null;
	
	private ConfigCache() {
		urlJson = ReadFile.read(LevelTwoServlet.class.getClassLoader().getResource("urls.json").getPath());
		htmlTemplateLevel_1_IE7 = ReadFile.read(LevelTwoServlet.class.getClassLoader().getResource("htmlTemplateLevel_1_IE7.html").getPath());
		htmlTemplateLevel_1_IE8 = ReadFile.read(LevelTwoServlet.class.getClassLoader().getResource("htmlTemplateLevel_1_IE8.html").getPath());
		htmlTemplateLevel_2_IE7 = ReadFile.read(LevelTwoServlet.class.getClassLoader().getResource("htmlTemplateLevel_2_IE7.html").getPath());
		htmlTemplateLevel_2_IE8 = ReadFile.read(LevelTwoServlet.class.getClassLoader().getResource("htmlTemplateLevel_2_IE8.html").getPath());
		htmlTemplateLevel_3_IE7 = ReadFile.read(LevelTwoServlet.class.getClassLoader().getResource("htmlTemplateLevel_3_IE7.html").getPath());
		htmlTemplateLevel_3_IE8 = ReadFile.read(LevelTwoServlet.class.getClassLoader().getResource("htmlTemplateLevel_3_IE8.html").getPath());
		pdf = ReadFile.read(LevelTwoServlet.class.getClassLoader().getResource("pdf.html").getPath());
	}
	
	public static ConfigCache getInstance() {
		if(single == null) {
			single = new ConfigCache();
		}
		return single;
	}

	public String getUrlJson() {
		return urlJson;
	}

	public void setUrlJson(String urlJson) {
		this.urlJson = urlJson;
	}

	public String getHtmlTemplateLevel_1_IE7() {
		return htmlTemplateLevel_1_IE7;
	}

	public void setHtmlTemplateLevel_1_IE7(String htmlTemplateLevel_1_IE7) {
		this.htmlTemplateLevel_1_IE7 = htmlTemplateLevel_1_IE7;
	}

	public String getHtmlTemplateLevel_1_IE8() {
		return htmlTemplateLevel_1_IE8;
	}

	public void setHtmlTemplateLevel_1_IE8(String htmlTemplateLevel_1_IE8) {
		this.htmlTemplateLevel_1_IE8 = htmlTemplateLevel_1_IE8;
	}

	public String getHtmlTemplateLevel_2_IE7() {
		return htmlTemplateLevel_2_IE7;
	}

	public void setHtmlTemplateLevel_2_IE7(String htmlTemplateLevel_2_IE7) {
		this.htmlTemplateLevel_2_IE7 = htmlTemplateLevel_2_IE7;
	}

	public String getHtmlTemplateLevel_2_IE8() {
		return htmlTemplateLevel_2_IE8;
	}

	public void setHtmlTemplateLevel_2_IE8(String htmlTemplateLevel_2_IE8) {
		this.htmlTemplateLevel_2_IE8 = htmlTemplateLevel_2_IE8;
	}

	public String getHtmlTemplateLevel_3_IE7() {
		return htmlTemplateLevel_3_IE7;
	}

	public void setHtmlTemplateLevel_3_IE7(String htmlTemplateLevel_3_IE7) {
		this.htmlTemplateLevel_3_IE7 = htmlTemplateLevel_3_IE7;
	}

	public String getHtmlTemplateLevel_3_IE8() {
		return htmlTemplateLevel_3_IE8;
	}

	public void setHtmlTemplateLevel_3_IE8(String htmlTemplateLevel_3_IE8) {
		this.htmlTemplateLevel_3_IE8 = htmlTemplateLevel_3_IE8;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public static ConfigCache getSingle() {
		return single;
	}

	public static void setSingle(ConfigCache single) {
		ConfigCache.single = single;
	}
	
}
