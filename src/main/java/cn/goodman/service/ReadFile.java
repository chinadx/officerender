package cn.goodman.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import cn.goodman.servlet.LevelTwoServlet;

public class ReadFile {
	public static String read(String path) {
		File file = new File(path);
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
			reader = new BufferedReader(isr);
			
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				//加下面这一段，对读取UTF8首行的第一个字符为问号的处理。
				//参考http://blog.csdn.net/zmc_happy_blog/article/details/19968155
				if(line == 1) {
				char s =tempString.trim().charAt(0);   
					//65279是空字符   
					if(s==65279){   
					  if(tempString.length()>1){   
						  tempString=tempString.substring(1);   
					  }   
					}  
				}
				// 显示行号
//				System.out.println("line " + line + ": " + tempString);
				sb.append(tempString);
				sb.append('\n');
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}
	
	public static void main(String args[]) {
		String filePath = LevelTwoServlet.class.getClassLoader().getResource("urls.json").getPath();
		String jsonString = ReadFile.read(filePath);
		System.out.println(jsonString);
	}
}
