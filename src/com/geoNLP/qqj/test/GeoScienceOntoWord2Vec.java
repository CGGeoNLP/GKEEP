package com.geoNLP.qqj.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.projectReadExcel.Common;
import com.projectReadExcel.geodemo.GeoOntology;
import com.projectReadExcel.geodemo.ReadGeoExcel;

import me.xiaosheng.word2vec.Word2Vec;

/**
 * ���ñ�����Ϊ�ල��Ȼ��ѵ���õĴ�������Ϊ�������ж������������ؼ�����ȡ ontology ֻ��һ���������ʵ�飬��û��Ӧ�õ�LDAģ��
 * 
 * @author Administrator
 * 
 */
public class GeoScienceOntoWord2Vec {

	static final String FILEPATH_STRING = "data/��ʯ����.txt";

	// ͣ�ôʴʱ�
	public static final String stopWordTable = "data/StopWordTable.txt";

	static final double threshold = 0.5;// ��ֵ

	// ������ʱ���
	public static void comnpareToOnto(String filePath, String str,
			BufferedWriter buffWriter) throws IOException {
		String encoding = "utf-8";

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ

		Word2Vec vec = new Word2Vec();
		try {
			vec.loadGoogleModel("data/default_vectors.bin");
		} catch (IOException e) {
			e.printStackTrace();
		}
		File file = new File(filePath);

		if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), encoding);// ���ǵ������ʽ
			BufferedReader bufferedReader = new BufferedReader(read);

			// lineTxt��ÿ�λ�ȡ��һ���ַ���
			String lineTxt = null;
			try {
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println("str = " + str);
					// �뱾���н��бȽϣ���ֵ����0.8����ȡ����
					double tempThreshold = 0;
					tempThreshold = vec.wordSimilarity(str, lineTxt);

					buffWriter.write(lineTxt + "---->" + str + ":"
							+ tempThreshold + "\r\n");

					if (tempThreshold >= threshold) {
						System.out.println(".....��ǰ����Ҫ��ȡ����!....");
						System.out.println("��ȡ������Ϣ:" + str);

					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		buffWriter.close();
		// w.close();
		// System.out.println("��ǰ�������....");

	}

	public static Map getTopicWords(String filePath, String str)
			throws IOException {

		
		
		Map<String, Double> keyWordsMap = new HashMap<String, Double>();
		Writer w = new FileWriter("E:/log4.txt");
		BufferedWriter buffWriter = new BufferedWriter(w);
		Writer wKeyWords = new FileWriter("E:/KeyWords4.txt");
		BufferedWriter buffWriterKeyWords = new BufferedWriter(wKeyWords);
		String encoding = "utf-8";
		
		// ����ͣ�ô��ļ�
		BufferedReader StopWordFileBr = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(stopWordTable)),encoding));
		// �������ͣ�ôʵļ���
		Set<String> stopWordSet = new HashSet<String>();
		// ���绯ͣ�ôʼ�
		String stopWord = null;
		for (; (stopWord = StopWordFileBr.readLine()) != null;) {
			//System.out.println(stopWord);
			stopWordSet.add(stopWord);
		}

		File file = new File(filePath);
		String[] arrStr = str.split("\\s+");// ���ַ������տո���зָ���

		for (int i = 0; i < arrStr.length; i++) {
			System.out.println(arrStr[i]);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ

			Word2Vec vec = new Word2Vec();
			try {
				vec.loadGoogleModel("data/default_vectors.bin");
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// ���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);

				// lineTxt��ÿ�λ�ȡ��һ���ַ���
				String lineTxt = null;
				try {
					while ((lineTxt = bufferedReader.readLine()) != null) {
						// System.out.println("str = " + str);
						// �뱾���н��бȽϣ���ֵ����0.8����ȡ����
						double tempThreshold = 0;
						
						if (!stopWordSet.contains(arrStr[i])) {
							tempThreshold = vec.wordSimilarity(arrStr[i], lineTxt);

							
							//����Щ���ϴ浵��keyWordsMap��
							
							
							
							buffWriter.write(lineTxt + "---->" + arrStr[i] + ":"
									+ tempThreshold + "\r\n");

							if (tempThreshold >= threshold) {
								//System.out.println(".....��ǰ����Ҫ��ȡ����!....");
								System.out.println("��ȡ������Ϣ:" + arrStr[i]);
								
								keyWordsMap.put(arrStr[i], tempThreshold);
								
								buffWriterKeyWords.write(arrStr[i]+"\r\n");
							}
						}else {
							System.out.println("����ͣ�ôʣ����˵�...");
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
		buffWriter.close();
		buffWriterKeyWords.close();
		w.close();
		wKeyWords.close();
		
		return keyWordsMap;
	}

	public static void main(String[] args) throws IOException {

		System.out.println("-----��ȡ���ʱ����е��ʶ���-----");
		String sent1 = "���� �ɿ�� ���� �� ���� ���ɹ� �ɿ�ʡ �� ��� ���� �� ���� �ɿ� ���� ���� ���ɹ� ���˰��� �ɿ�ʡ �� �� �ɿ�� �� ͻȪ �� ���� ������ �� ��ɽ�� �������� �� ͭ �� Ǧп �� �� ���꣨�㣩  �� �� �ɿ�� �� ������ ���Ƹ� �������� �� ͭ �� п  �ɿ�� ��ɳ����ɽ ͭ �� Ǧ �� п �ɿ�� ��";
		String sent2 = "���� �Ѿ� ��ɽ� ��Ϊ Ƶ�� �� ������ ��  ��ɽ�� ʮ�� ���� ������ �ɿ� ���� ���� �Ϻ� ���� һ�� �ܺ� �� �ҿ� Զ�� ���� �� �ֽ� �ز� �� �ҽ��� �� ���� �� �ɿ� ���� ���� ���� ��";
		String sent3 = "���� ��� Ϊ ���ɫ �� ��ɫ ����ĸ ���� �� ����  ��ɰ�� �� ���� ϸɴ�� �� �� ��б ���� �� ��� ���� 2908.7 m �� �� ���� �ز� �� ��δ ���� ��ʯ �� ";

		String sent4 ="�� ������ �� ������ �� ������ �� �۽������� �� ������ �� ���� ���� ������ ���� ���� Ϊ�� �� ��� �� ������ �� ���� ��ɽ������ �� ������ �� ���� ������ ������ �� �� ���� ���� �� ���� �ɷ� ��� Ϊ ͬԴ ��м �� ���� �� ������ ���� �� ������ �� �۽������� �� ��  ";
		


		Map<String, Double> maps = getTopicWords(FILEPATH_STRING, sent4);
		// getTopicWords(sent2);
		// getTopicWords(sent3);
		
		//List<Map.Entry<String, Integer>> list = new ArrayList<>();
		
		ByValueComparator bvc = new ByValueComparator(maps);
		ArrayList<String> keys = new ArrayList<String>(maps.keySet());
		Collections.sort(keys, bvc);
		
		System.out.println(keys);

	}

}
