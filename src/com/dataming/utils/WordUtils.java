package com.dataming.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WordUtils {
	
	/**
	 * 文件分词
	 * @param path
	 * @param seg
	 * @return
	 * @throws IOException 
	 */
	public static String[] splitFile(File file) throws IOException {
		//String[] words = null;
		InputStream in = null;
		BufferedReader reader = null;
		
		List<String> wordsList = new ArrayList<String>();
		
		try {
			in = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			// lineTxt：每次获取的一行字符串
			String lineTxt = null;
			while ((lineTxt = reader.readLine()) != null) {
				String[] arrStr = lineTxt.split("\\s+");// 将字符串按照空格进行分隔开
				for (int i = 0; i < arrStr.length; i++) {
					wordsList.add(arrStr[i]);
					//System.out.println("arrStr[i]"+arrStr[i]);
				}
			}
			
		} catch (FileNotFoundException e) {
			//logger.error(e.getMessage());
			System.err.println(e.getMessage());
		} /*finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(reader);
		}*/
		//System.out.println("wordsList.toArray(new String[0]=="+wordsList.toArray(new String[0]);
		return wordsList.toArray(new String[0]);
	}
	

}
