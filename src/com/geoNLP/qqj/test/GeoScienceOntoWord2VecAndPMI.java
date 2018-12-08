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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.dataming.document.Document;
import com.dataming.document.DocumentLoader;
import com.dataming.document.DocumentSet;

import me.xiaosheng.word2vec.Word2Vec;

public class GeoScienceOntoWord2VecAndPMI {

	// ����·��
	static final String FILEPATH_STRING = "data/��ʯ����.txt";
	// ͣ�ôʴʱ�
	public static final String stopWordTable = "data/stopwords.txt";
	// ���Ͽ�·��
	public static String path = "D:\\geoscience documents\\";

	static final double threshold = 0.5;// ��ֵ

	static final double PMIAndWord2Vecthreshold = 0.8;// ��ֵ

	public static void main(String[] args) throws IOException {
		System.out.println("-----��ȡ���ʱ����е��ʶ���-----");
		String sent1 = "���� �ɿ�� ���� �� ���� ���ɹ� �ɿ�ʡ �� ��� ���� �� ���� �ɿ� ���� ���� ���ɹ� ���˰��� �ɿ�ʡ �� �� �ɿ�� �� ͻȪ �� ���� ������ �� ��ɽ�� �������� �� ͭ �� Ǧп �� �� ���꣨�㣩  �� �� �ɿ�� �� ������ ���Ƹ� �������� �� ͭ �� п  �ɿ�� ��ɳ����ɽ ͭ �� Ǧ �� п �ɿ�� ��";
		String sent2 = "���� �Ѿ� ��ɽ� ��Ϊ Ƶ��  �� ������ ��  ��ɽ�� ʮ�� ���� ������ �ɿ� ���� ���� �Ϻ�  �� �� һ�� �ܺ� �� �ҿ� Զ�� ����  ��  �ֽ� �ز� �� �ҽ��� �� ���� �� �ɿ� ���� ���� ����  ��";
		String sent3 = "���� ��� Ϊ ���ɫ �� ��ɫ ����ĸ ���� �� ����  ��ɰ�� �� ���� ϸɴ�� �� �� ��б ���� �� ��� ���� 2908.7 m �� �� ���� �ز� �� ��δ ���� ��ʯ �� ";

		// String sent4 =
		// "�� ������ �� ������ �� ������ �� �۽������� �� ������ �� ���� ���� ������ ���� ���� Ϊ�� �� ��� �� ������ �� ���� ��ɽ������ �� ������ �� ���� ������ ������ �� �� ���� ���� �� ���� �ɷ� ��� Ϊ ͬԴ ��м �� ���� �� ������ ���� �� ������ �� �۽������� �� ��  ";

		String sent4 = " ��ʯ ��� Ϊ �ֻ�ɫ ����״ ϸ�� ʯӢɰ�� �� ����ɫ ���� ���� ���� �� ǳ��ɫ �в� ������ ���� �� ����ɫ ��ɽ�� ��м ������ �� ����״ ���䰲ɽ�� �� ������м �ྦྷ���� �� �� ��ʯ�ſ� �ྦྷ���� �� �� ������ ��� �ྦྷ���� �� ���� ���� �ྦྷ���� �� δ �� ����ϵ �� �� ��� �� �� ���� �� �ٽ� ������ �� �� δ�� �� ����ͳ Ϊ �ϲ� �Ӵ� �� ��Ҫ ���� Ϊ �ƻ�ɫ ���״ �� ���� ���ྦྷ ���� �� �ƻ�ɫ ��� ״�ྦྷ ���� ���� �� ";
		
		
		 Map<String, Double> mapsbyPMIandWord2vec =getTopicWordsByPMI(sent2);
		 ByValueComparator bvcbyPMIandWord2vec = new
		 ByValueComparator(mapsbyPMIandWord2vec); ArrayList<String>
		 keysbyPMIandWord2vec = new ArrayList<String>(mapsbyPMIandWord2vec.keySet());
		 Collections.sort(keysbyPMIandWord2vec, bvcbyPMIandWord2vec);
		 System.out.println("ͨ��PMI���ƶ�:"+keysbyPMIandWord2vec);

	}
	
	
	

	/**
	 * ��ȡ��ʯ���壬��ŵ�HashMap��
	 * 
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public static Set initOnto() throws IOException {

		// ���뱾���ļ�
		BufferedReader rockOntoFileBr = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(
						FILEPATH_STRING)), "UTF-8"));
		// ������ű���ʵļ���
		Set<String> WordSet = new HashSet<String>();
		// ���绯ͣ�ôʼ�
		String stopWord = null;
		for (; (stopWord = rockOntoFileBr.readLine()) != null;) {
			WordSet.add(stopWord);
		}
		return WordSet;
	}

	// ��ʼ���ı�
	private static DocumentSet initData() {
		DocumentSet documentSet = null;
		try {
			documentSet = DocumentLoader.loadDocumentSetByThread(path);
			// reduceDimensionsByCHI(documentSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return documentSet;
	}

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

	/**
	 * �����以��Ϣ
	 * 
	 * @param word1
	 * @param word2
	 * @param documents
	 * @return
	 */
	private static double calculatePMI(String word1, String word2,
			List<Document> documents) {
		double w1w2Num = statisticsWordsInDocs(documents, word1, word2);
		if (w1w2Num == 0)
			return 0;
		double w1Num = statisticsWordsInDocs(documents, word1);
		double w2Num = statisticsWordsInDocs(documents, word2);
		return Math.log(documents.size() * w1w2Num / (w1Num * w2Num))
				/ Math.log(2);
	}

	/**
	 * ͳ�ƴʳ����ĵ���
	 * 
	 * @param documents
	 * @param words
	 * @return
	 */
	private static double statisticsWordsInDocs(List<Document> documents,
			String... words) {
		double sum = 0;
		for (Document document : documents) {
			Set<String> wordSet = document.getWordSet();
			int num = 0;
			for (String word : words) {
				if (wordSet.contains(word))
					num++;
			}
			if (num == words.length)
				sum++;
		}
		return sum;
	}

	/**
	 * ͨ��PMI����ȡ���ƶ���Ϣ
	 * 
	 * @param filePath
	 * @param str
	 * @return 2017��11��28��17:04:12
	 * @throws IOException
	 */
	public static Map getTopicWordsByPMI(String str) throws IOException {

		Map<String, Double> keyWordsMap = new HashMap<String, Double>();

		DocumentSet documentSet = initData();
		List<Document> documents = documentSet.getDocuments();
		double PMIValue = 0.0;
		String[] senArr = str.split("\\s+");

		double maxValue = 0;
		int length = senArr.length;

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

		for (int i = 0; i < senArr.length; i++) {
			if (!stopWordSet.contains(senArr[i])) {
				for (int j = 0; j < senArr.length; j++) {
					if (!stopWordSet.contains(senArr[j])) {

						PMIValue = PMIValue
								+ calculatePMI(senArr[i], senArr[j], documents);
					}
					// ==================���ÿһ���������������Խ��е��Ӽ�Ȩ
				}
				// maxValue = 1 - d + d / length * word2vecValue;
				maxValue = PMIValue / length;
				System.out.println(senArr[i] + "---->" + maxValue);
				keyWordsMap.put(senArr[i], maxValue);
				maxValue = 0;
				PMIValue = 0;
			}
		}
		
		return keyWordsMap;

	}

	/**
	 * ͨ�����������ƶ�����ȡ��Ϣ
	 * 
	 * @param filePath
	 * @param str
	 * @return 2017��11��28��17:06:08
	 * @throws IOException
	 */
	public static void getTopicWordsByWord2Vec(String str) throws IOException {
		Word2Vec vec = new Word2Vec();
		try {
			vec.loadGoogleModel("data/default_vectors.bin");
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] senArr = str.split("\\s+");
		double word2vecValue = 0;

		for (int i = 0; i < senArr.length; i++) {
			for (int j = i + 1; j < senArr.length; j++) {

				word2vecValue = vec.wordSimilarity(senArr[i], senArr[j]);

				if (word2vecValue >= 0.5) {
					System.out.println("senArr[i]:" + senArr[i]
							+ "senArr[j]--->" + senArr[j] + word2vecValue);
				}
			}
		}
	}

	/**
	 * word2vec and PMI ��Ϸ���
	 * 
	 * @param str
	 * @throws IOException
	 */
	public static void getTopicWordsByword2VecAndPMI(String str)
			throws IOException {

		// =========================
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

		// ==========================PMI
		DocumentSet documentSet = initData();
		List<Document> documents = documentSet.getDocuments();
		double PMIValue = 0.0;

		// ==========================word2vec
		Word2Vec vec = new Word2Vec();
		try {
			vec.loadGoogleModel("data/default_vectors.bin");
		} catch (IOException e) {
			e.printStackTrace();
		}
		double word2vecValue = 0;

		// =============================��Ϸ���
		String[] senArr = str.split("\\s+");
		double sumVaule = 0;

		for (int i = 0; i < senArr.length; i++) {

			if (!stopWordSet.contains(senArr[i])) {
				for (int j = i + 1; j < senArr.length; j++) {
					PMIValue = calculatePMI(senArr[i], senArr[j], documents);
					word2vecValue = vec.wordSimilarity(senArr[i], senArr[j]);
					sumVaule = PMIValue * word2vecValue;
					if (sumVaule >= 0.5) {
						System.out.println("senArr[i]:" + senArr[i]
								+ "senArr[j]--->" + senArr[j] + sumVaule);
					}
				}
			}

		}

		System.out.println("ִ�����====");

	}
}
