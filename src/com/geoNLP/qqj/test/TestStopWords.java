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
	// ͣ�ôʴʱ�
	public static final String stopWordTable = "data/StopWordTable.txt";

	public static void main(String[] args) throws IOException {

		String encoding = "utf-8";
		// ����ͣ�ô��ļ�
		BufferedReader StopWordFileBr = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(stopWordTable)),encoding));
		
		
		// �������ͣ�ôʵļ���
		Set<String> stopWordSet = new HashSet<String>();
		// ���绯ͣ�ôʼ�
		String stopWord = null;
		for (; (stopWord = StopWordFileBr.readLine()) != null;) {
			stopWordSet.add(stopWord);
		}
		// �����ı�
		String sent3 = "���� ��� Ϊ ���ɫ �� ��ɫ ����ĸ���� �� ���ʷ�ɰ�� �� ����ϸɴ�� �� �� ��б ���� �� ��� ���� 2908.7 m �� �� ���� �ز� �� ��δ ���� ��ʯ �� ";

		String[] arrStr = sent3.split("\\s+");// ���ַ������տո���зָ���
		
		for (int i = 0; i < arrStr.length; i++) {
			if (stopWordSet.contains(arrStr[i])) {
				System.out.println("ͣ�ô�--->"+arrStr[i]);
			}
		}

	}

}
