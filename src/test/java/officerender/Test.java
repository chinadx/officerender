package officerender;

import cn.goodman.service.ConfigCache;

public class Test {
	public static void main(String args[]) {
		String urlJson = ConfigCache.getInstance().getUrlJson();
		String htmlTemplateLevel_1_IE7 = ConfigCache.getInstance().getHtmlTemplateLevel_1_IE7();
		System.out.println(urlJson);
		System.out.println(htmlTemplateLevel_1_IE7);
	}
}
