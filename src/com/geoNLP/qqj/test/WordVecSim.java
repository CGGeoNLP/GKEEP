package com.geoNLP.qqj.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import me.xiaosheng.word2vec.Word2Vec;

/**
 * ����ѵ���õĴ������������������ж� �����Ƚ�
 * 
 * @author Administrator
 * 
 */
public class WordVecSim {

	static final String FILEPATH_STRING = "data/��ʯ����.txt";
	static final double threshold = 0.5;// ��ֵ
	public static final String stopWordTable = "data/stopwords.txt";

	/**
	 * ����ϵ����Damping Factor)��һ��ȡֵΪ0.85
	 */
	static final float d = 0.85f;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static Map word2VecSimCompare(String sentence) throws IOException {

		Map<String, Double> keyWordsMap = new HashMap<String, Double>();
		// =============���ش�����
		Word2Vec vec = new Word2Vec();
		try {
			vec.loadGoogleModel("data/default_vectors.bin");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ==============����stopword
		// ����ͣ�ô��ļ�
		String encoding = "utf-8";
		BufferedReader StopWordFileBr = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(
						stopWordTable)), encoding));
		// �������ͣ�ôʵļ���
		Set<String> stopWordSet = new HashSet<String>();
		// ���绯ͣ�ôʼ�
		String stopWord = null;
		for (; (stopWord = StopWordFileBr.readLine()) != null;) {
			stopWordSet.add(stopWord);
		}
		System.out.println(stopWordSet);

		String[] senArr = sentence.split("\\s+");
		double word2vecValue = 0;
		double maxValue = 0;
		int length = senArr.length;

		for (int i = 0; i < senArr.length; i++) {

			if (!stopWordSet.contains(senArr[i])) {

				for (int j = 0; j < senArr.length; j++) {
					if (!stopWordSet.contains(senArr[j])) {

						word2vecValue = word2vecValue
								+ vec.wordSimilarity(senArr[i], senArr[j]);
					}
					// ==================���ÿһ���������������Խ��е��Ӽ�Ȩ
				}
				//maxValue = 1 - d + d / length * word2vecValue;
				maxValue = word2vecValue/ length;
				System.out.println(senArr[i] + "---->" + maxValue);
				keyWordsMap.put(senArr[i], maxValue);
				maxValue = 0;
				word2vecValue = 0;
			}
		}
		
		return keyWordsMap;

	}
	
	/**
	 * ��������
	 * @param map
	 * @return
	 */
	public List<Map.Entry<String, Double>> sortMap(Map<String, Double> map) {
		List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			@Override
			public int compare(Entry<String, Double> o1,
					Entry<String, Double> o2) {
				if (o1.getValue().isNaN()) {
					o1.setValue(0.0);
				}
				if (o2.getValue().isNaN()) {
					o2.setValue(0.0);
				}
				return -o1.getValue().compareTo(o2.getValue());
			}
		});
		return list;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String sentence = "�ֲ� �� Ӣ���� �� ��ɽ�� �� ������м ��м �۽��� �� ������ ɰ���� �� �������� �� ���� �� ֲ�� ��ʯ ��Ƭ �� �� �ڷ� �� �� ɳ����ӷ� �� ����ͷ������ �� ���� ��ʯ �� ��ֲ�� ��ʯ ��";
		
		String sentence1 ="���� �Ѿ� ��ɽ� ��Ϊ Ƶ�� �� ������ ��  ��ɽ�� ʮ�� ���� ������ �ɿ� ���� ���� �Ϻ� ���� һ�� �ܺ� �� �ҿ� Զ�� ���� �� �ֽ� �ز� �� �ҽ��� �� ���� �� �ɿ� ���� ���� ���� ��";
		
		String sentence2 ="�� ������ �� ������ �� ������ �� �۽������� �� ������ �� ���� ���� ������ ���� ���� Ϊ�� �� ��� �� ������ �� ���� ��ɽ������ �� ������ �� ���� ������ ������ �� �� ���� ���� �� ���� �ɷ� ��� Ϊ ͬԴ ��м �� ���� �� ������ ���� �� ������ �� �۽������� �� ��  ";
		
		String sentence3="�� �� ����� ���� �� ̫�Ž� ��̨��Ⱥ ʯ����Ⱥ ��ڿ��� Ƭ���� �� �� �� �ز� ���� �� ǿ�� �� �㷺 �� ������� ���� �� ��� �һ� ���� �� �ֲ� �� ���� ���� ���� �� �γ� �� �������� �� ���ƽ���б��Ƭ���� �� ���� ʯӢ�� �� ��";
		
		String sentence4="���� ��� Ϊ ���ɫ �� ��ɫ ����ĸ ���� �� ����  ��ɰ�� �� ���� ϸɴ�� �� �� ��б ���� �� ��� ���� 2908.7 m �� �� ���� �ز� �� ��δ ���� ��ʯ ��";
		
		String sentence5="��ʯ ��� Ϊ �ֻ�ɫ ����״ ϸ�� ʯӢɰ�� �� ����ɫ ���� ���� ���� �� ǳ��ɫ �в� ������ ���� �� ����ɫ ��ɽ�� ��м ������ �� ����״ ���䰲ɽ�� �� ������м �ྦྷ���� �� �� ��ʯ�ſ� �ྦྷ���� �� �� ������ ��� �ྦྷ���� �� ���� ���� �ྦྷ���� �� ";
		
		String sentence6="δ �� ����ϵ �� �� ��� �� �� ���� �� �ٽ� ������ �� �� δ�� �� ����ͳ Ϊ �ϲ� �Ӵ� �� ��Ҫ ���� Ϊ �ƻ�ɫ ���״ �� ���� ���ྦྷ ���� �� �ƻ�ɫ ��� ״�ྦྷ ���� ���� ��";
		
		String sentence7="®�� ������ ��ɽ��� λ�� ���� ������ ���ݴ� �� , �ش� ���Ӱ�� �� ��Ե �� " ;		
		
		 Map<String, Double> mapsbyPMIandWord2vec = word2VecSimCompare(sentence7);
		 ByValueComparator bvcbyPMIandWord2vec = new
		 ByValueComparator(mapsbyPMIandWord2vec); ArrayList<String>
		 keysbyPMIandWord2vec = new ArrayList<String>(mapsbyPMIandWord2vec.keySet());
		 Collections.sort(keysbyPMIandWord2vec, bvcbyPMIandWord2vec);
		 System.out.println("ͨ����Ϸ������ƶ�:"+keysbyPMIandWord2vec);
		

		
	}

}
