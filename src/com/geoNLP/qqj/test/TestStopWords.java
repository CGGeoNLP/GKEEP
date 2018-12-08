package com.geoNLP.qqj.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class TestStopWords {
	// 停用词词表
	public static final String stopWordTable = "data/StopWordTable.txt";

	public static void main(String[] args) throws IOException {

		String encoding = "utf-8";
		// 读入停用词文件
		BufferedReader StopWordFileBr = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(stopWordTable)),encoding));
		
		
		// 用来存放停用词的集合
		Set<String> stopWordSet = new HashSet<String>();
		// 初如化停用词集
		String stopWord = null;
		for (; (stopWord = StopWordFileBr.readLine()) != null;) {
			stopWordSet.add(stopWord);
		}
		// 测试文本
		String sent3 = "岩性 组合 为 深灰色 、 灰色 绢云母板岩 、 变质粉砂岩 、 变质细纱岩 ， 呈 单斜 产出 ， 厚度 大于 2908.7 m ， 在 本套 地层 中 尚未 发现 化石 。 ";

		String[] arrStr = sent3.split("\\s+");// 将字符串按照空格进行分隔开
		
		for (int i = 0; i < arrStr.length; i++) {
			if (stopWordSet.contains(arrStr[i])) {
				System.out.println("停用词--->"+arrStr[i]);
			}
		}

	}

}
