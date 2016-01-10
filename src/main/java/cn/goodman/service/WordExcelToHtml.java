package cn.goodman.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class WordExcelToHtml {

	/**
	 * 回车符ASCII码
	 */
	private static final short ENTER_ASCII = 13;

	/**
	 * 空格符ASCII码
	 */
	private static final short SPACE_ASCII = 32;

	/**
	 * 水平制表符ASCII码
	 */
	private static final short TABULATION_ASCII = 9;

	public static String htmlText = "";
	public static String htmlTextTbl = "";
	public static int counter=0;
	public static int beginPosi=0;
	public static int endPosi=0;
	public static int beginArray[];
	public static int endArray[];
	public static String htmlTextArray[];
	public static boolean tblExist=false;
	
	public static final String inputFile="f://temp/new.doc";
	public static void main(String argv[])
	{		
		try {
			String ss = getWordAndStyle(inputFile);
			System.out.println(ss);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取每个文字样式
	 * 
	 * @param fileName
	 * @throws Exception
	 */

	public static String getWordAndStyle(String fileName) throws Exception {
		if(fileName.endsWith("doc")) {
			return getWordAndStyle2003(fileName);
		}
		else if(fileName.endsWith("docx")) {
			return getWordAndStyle2007(fileName);
		}
		else {
			return "暂不支持的文件类型";
		}
	}
	
	@SuppressWarnings("resource")
	public static String getWordAndStyle2007(String fileName) throws Exception {
		//得到.docx文件提取器 
		XWPFWordExtractor docx = new XWPFWordExtractor(POIXMLDocument.openPackage(fileName)); 
		//提取.docx正文文本 
		String text = docx.getText(); 
		
		return text;
	}
	
	public static String getWordAndStyle2003(String fileName) throws Exception {
		FileInputStream in = new FileInputStream(new File(fileName));
		HWPFDocument doc = new HWPFDocument(in);
		
		Range rangetbl = doc.getRange();//得到文档的读取范围   
		TableIterator it = new TableIterator(rangetbl); 
		int num=100;		 

		beginArray=new int[num];
		endArray=new int[num];
		htmlTextArray=new String[num];

		// 取得文档中字符的总数
		int length = doc.characterLength();
		// 创建图片容器
		PicturesTable pTable = doc.getPicturesTable();
        String cssStyle = "<style>table{border-collapse:collapse;border-spacing:0;"
        		+ "border-left:1px solid #888;border-top:1px solid #888;background:#efefef;}"
        		+ "th,td{border-right:1px solid #888;border-bottom:1px solid #888;padding:5px 15px;}"
        		+ "th{font-weight:bold;background:#ccc;}</style>";
        		
		htmlText = "<!DOCTYPE html><html lang=\"zh-cn\">"
				+ "<head><meta charset=\"GBK\" /><title>" + doc.getSummaryInformation().getTitle() + "</title>"+cssStyle+"</head><body>";
		// 创建临时字符串,好加以判断一串字符是否存在相同格式
		
		if(it.hasNext())
		{
			readTable(it,rangetbl);
		}
		
		int cur=0;
		
		String tempString = "";
		for (int i = 0; i < length - 1; i++) {
			// 整篇文章的字符通过一个个字符的来判断,range为得到文档的范围
			Range range = new Range(i, i + 1, doc);
			
			CharacterRun cr = range.getCharacterRun(0); 
			if(tblExist)
			{
				if(i==beginArray[cur])
				{		 
					htmlText+=tempString+htmlTextArray[cur];
					tempString="";
					i=endArray[cur]-1;
					cur++;
					continue;
				}
			}
			if (pTable.hasPicture(cr)) {
				htmlText +=  tempString ;				
				// 读写图片
				// 屏蔽掉，暂不考虑处理图片
//				readPicture(pTable, cr);
				tempString = "";				
			} 
			else {
				Range range2 = new Range(i + 1, i + 2, doc);
				// 第二个字符
				CharacterRun cr2 = range2.getCharacterRun(0);
				char c = cr.text().charAt(0);
				
				// 判断是否为回车符
				if (c == ENTER_ASCII) {
					tempString += "<br/>";
				}
				// 判断是否为空格符
				else if (c == SPACE_ASCII) {
					tempString += "&nbsp;";
				}
				// 判断是否为水平制表符
				else if (c == TABULATION_ASCII) {
					tempString += " &nbsp;&nbsp;&nbsp;";
				}
				// 比较前后2个字符是否具有相同的格式
				boolean flag = compareCharStyle(cr, cr2);
				if (flag) {
					tempString += cr.text();
				}
				else {
					String fontStyle = "<span style=\"font-family:" + cr.getFontName() + ";font-size:" + cr.getFontSize() / 2 + "pt;";
					if (cr.isBold()) {
						fontStyle += "font-weight:bold;";
					}
					if (cr.isItalic()) {
						fontStyle += "font-style:italic;";
					}
					htmlText += fontStyle + "\" mce_style=\"font-family:" + cr.getFontName() + ";font-size:" + cr.getFontSize() / 2 + "pt;";
									
					if (cr.isBold()) {
						fontStyle += "font-weight:bold;";
					}
					if (cr.isItalic()) {
						fontStyle += "font-style:italic;";
					}
					
					htmlText += fontStyle + "\">" + tempString + cr.text() + "</span>";
					tempString = "";
				}
			}
		}

		htmlText += tempString+"</body></html>";
		return htmlText;
	}
	
	/**
	 * 读写文档中的表格
	 * 
	 * @param pTable
	 * @param cr
	 * @throws Exception
	 */
	public static void readTable(TableIterator it, Range rangetbl) throws Exception {

		htmlTextTbl="";
   	 	//迭代文档中的表格  
		
		counter=-1;
		while (it.hasNext()) { 
			Map<Integer,Integer> cellIndexMapping=new HashMap<Integer,Integer>();
			int columns=0;

			tblExist=true;
			htmlTextTbl="";
			Table tb = (Table) it.next();    
			beginPosi=tb.getStartOffset() ;
			endPosi=tb.getEndOffset();
			counter=counter+1;
			//迭代行，默认从0开始
			beginArray[counter]=beginPosi;
			endArray[counter]=endPosi;
			int maxNumCellIndex=0;//单元格最多的哪一行
	       	
			for (int i = 0; i < tb.numRows(); i++) {        
				TableRow tr = tb.getRow(i);
				if(columns<tr.numCells()){
					maxNumCellIndex=i;
					columns=tr.numCells();
				}
			}
            
			for (int j = 0; j < columns; j++) {        
				TableCell td = tb.getRow(maxNumCellIndex).getCell(j);//取得单元格  
				cellIndexMapping.put(td.getLeftEdge(),j+1);
			}
	       	 
			htmlTextTbl+="<table>";
			for (int i = 0; i < tb.numRows(); i++) {      
				TableRow tr = tb.getRow(i);   
			 
				htmlTextTbl+="<tr>";
				//迭代列，默认从0开始   
				for (int j = 0; j < tr.numCells(); j++) {        
					TableCell td = tr.getCell(j);//取得单元格  
					TableCell td2;//取得下一个单元格 
					td.getWidth();
					if(j== tr.numCells()-1){
					 td2 = tr.getCell(j);
					}else{
					 td2 = tr.getCell(j+1);
					}
		            //获取横跨colspan
					int td1_edge=td.getLeftEdge();
					int td2_edge=td2.getLeftEdge();
					int td1_index=cellIndexMapping.get(td1_edge).intValue();
					int td2_index=cellIndexMapping.get(td2_edge).intValue();
					int span=td2_index-td1_index;

					//取得单元格的内容   
					StringBuffer sb = new StringBuffer();
					for(int k=0;k<td.numParagraphs();k++){      
					     Paragraph para =td.getParagraph(k);      
					     String s = para.text().toString().trim();   
					     if(s=="")
					     {
					    	 s=" ";
					     }
					     sb.append(s);
					     sb.append("\n");
					}
		            
		            htmlTextTbl += "<td colspan="+span+">"+sb.toString()+"</td>";
		            //解决最后一个单元格跨行到最结尾
		            if(tr.numCells()<columns&&j==tr.numCells()-1){
		             span=columns-tr.numCells()+1;
		            }
			  }
	   		 }
	   		htmlTextTbl+="</table>" ;    
	   		htmlTextArray[counter]=htmlTextTbl;
  
        }
	}	

	/**
	 * 读写文档中的图片
	 * 
	 * @param pTable
	 * @param cr
	 * @throws Exception
	 */
	public static void readPicture(PicturesTable pTable, CharacterRun cr) throws Exception {
		// 提取图片
		Picture pic = pTable.extractPicture(cr, false);
		// 返回POI建议的图片文件名
		String afileName = pic.suggestFullFileName();
		OutputStream out = new FileOutputStream(new File("f://temp" + File.separator + afileName));
		pic.writeImageContent(out);
		htmlText += "<img src=\"f://temp//" + afileName + "\" mce_src=\"f://temp//" + afileName + "\"/>";
	}

	public static boolean compareCharStyle(CharacterRun cr1, CharacterRun cr2) 
	{
		boolean flag = false;
		if (cr1.isBold() == cr2.isBold() && cr1.isItalic() == cr2.isItalic() && cr1.getFontName().equals(cr2.getFontName()) && cr1.getFontSize() == cr2.getFontSize()) 
		{
			flag = true;
		}
		return flag;
	}
	

	/**
	 * 写文件
	 * 
	 * @param s
	 */
	public static void writeFile(String s) {
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			File file = new File("f://temp/abc.html");
//			fos = new FileOutputStream(file);
//			bw = new BufferedWriter(new OutputStreamWriter(fos));
//			
//			bw.write(s);
			
			PrintWriter writer = new PrintWriter(file, "GBK");
			writer.write(s);
			writer.flush();
			writer.close();
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fos != null)
					fos.close();
			} catch (IOException ie) {
			}
		}
	}


}
