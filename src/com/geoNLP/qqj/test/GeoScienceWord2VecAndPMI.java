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

public class GeoScienceWord2VecAndPMI {

	// ����·��
	static final String FILEPATH_STRING = "data/��ʯ����.txt";
	// ͣ�ôʴʱ�
	public static final String stopWordTable = "data/StopWordTable.txt";
	// ���Ͽ�·��
	public static String path = "D:\\geoscience documents\\";

	static final double threshold = 0.5;// ��ֵ
	
	static final double PMIAndWord2Vecthreshold = 0.8;// ��ֵ

	public static void main(String[] args) throws IOException {
		System.out.println("-----��ȡ���ʱ����е��ʶ���-----");
		String sent1 = "���� �ɿ�� ���� �� ���� ���ɹ� �ɿ�ʡ �� ��� ���� �� ���� �ɿ� ���� ���� ���ɹ� ���˰��� �ɿ�ʡ �� �� �ɿ�� �� ͻȪ �� ���� ������ �� ��ɽ�� �������� �� ͭ �� Ǧп �� �� ���꣨�㣩  �� �� �ɿ�� �� ������ ���Ƹ� �������� �� ͭ �� п  �ɿ�� ��ɳ����ɽ ͭ �� Ǧ �� п �ɿ�� ��";
		String sent2 = "���� �Ѿ� ��ɽ� ��Ϊ Ƶ�� �� ������ ��  ��ɽ�� ʮ�� ���� ������ �ɿ� ���� ���� �Ϻ� ���� һ�� �ܺ� �� �ҿ� Զ�� ���� �� �ֽ� �ز� �� �ҽ��� �� ���� �� �ɿ� ���� ���� ���� ��";
		String sent3 = "���� ��� Ϊ ���ɫ �� ��ɫ ����ĸ ���� �� ����  ��ɰ�� �� ���� ϸɴ�� �� �� ��б ���� �� ��� ���� 2908.7 m �� �� ���� �ز� �� ��δ ���� ��ʯ �� ";

		//String sent4 = "�� ������ �� ������ �� ������ �� �۽������� �� ������ �� ���� ���� ������ ���� ���� Ϊ�� �� ��� �� ������ �� ���� ��ɽ������ �� ������ �� ���� ������ ������ �� �� ���� ���� �� ���� �ɷ� ��� Ϊ ͬԴ ��м �� ���� �� ������ ���� �� ������ �� �۽������� �� ��  ";

		

		String sent4 = " ��ʯ ��� Ϊ �ֻ�ɫ ����״ ϸ�� ʯӢɰ�� �� ����ɫ ���� ���� ���� �� ǳ��ɫ �в� ������ ���� �� ����ɫ ��ɽ�� ��м ������ �� ����״ ���䰲ɽ�� �� ������м �ྦྷ���� �� �� ��ʯ�ſ� �ྦྷ���� �� �� ������ ��� �ྦྷ���� �� ���� ���� �ྦྷ���� �� δ �� ����ϵ �� �� ��� �� �� ���� �� �ٽ� ������ �� �� δ�� �� ����ͳ Ϊ �ϲ� �Ӵ� �� ��Ҫ ���� Ϊ �ƻ�ɫ ���״ �� ���� ���ྦྷ ���� �� �ƻ�ɫ ��� ״�ྦྷ ���� ���� �� ";
		// ����ͨ�����������ƶ���ȡ������Ϣ
		/*
		 * Map<String, Double> mapsbyWord2Vec =
		 * getTopicWordsByWord2Vec(FILEPATH_STRING, sent3); ByValueComparator
		 * bvcbyWord2Vec = new ByValueComparator(mapsbyWord2Vec);
		 * ArrayList<String> keysbyWord2Vec = new
		 * ArrayList<String>(mapsbyWord2Vec.keySet());
		 * Collections.sort(keysbyWord2Vec, bvcbyWord2Vec);
		 * System.out.println("����ͨ�����������ƶ�:"+keysbyWord2Vec);
		 */

		// ����ͨ��PMI���ƶ���ȡ��Ϣ

/*		Map<String, Double> mapsbyPMI = getTopicWordsByPMI(sent3);
		ByValueComparator byPMI = new ByValueComparator(mapsbyPMI);
		ArrayList<String> keysbyPMI = new ArrayList<String>(mapsbyPMI.keySet());
		Collections.sort(keysbyPMI, byPMI);
		System.out.println("����ͨ��PMI���ƶ�:" + keysbyPMI);*/

		// ͨ����Ϸ������������ƶ���ȡ
		
		 Map<String, Double> mapsbyPMIandWord2vec =
		 getTopicWordsByWord2Vec(FILEPATH_STRING, sent4);
		 ByValueComparator bvcbyPMIandWord2vec = new
		 ByValueComparator(mapsbyPMIandWord2vec); ArrayList<String>
		 keysbyPMIandWord2vec = new ArrayList<String>(mapsbyPMIandWord2vec.keySet());
		 Collections.sort(keysbyPMIandWord2vec, bvcbyPMIandWord2vec);
		 System.out.println("ͨ����Ϸ������ƶ�:"+keysbyPMIandWord2vec);

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
	 * @return
	 * @throws IOException
	 */
	public static Map getTopicWordsByPMI(String str) throws IOException {
		Map<String, Double> keyWordsMap = new HashMap<String, Double>();
		Writer w = new FileWriter("E:/logByPMI4.txt");
		BufferedWriter buffWriter = new BufferedWriter(w);
		Writer wKeyWords = new FileWriter("E:/KeyWordsByPMI4.txt");
		BufferedWriter buffWriterKeyWords = new BufferedWriter(wKeyWords);
		String encoding = "utf-8";

		// ����ͣ�ô��ļ�
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
		DocumentSet documentSet = initData();
		List<Document> documents = documentSet.getDocuments();
		Set<String> rockOntoSet = initOnto();// ��ȡ���е���ʯ����

		String[] arrStr = str.split("\\s+");// ���ַ������տո���зָ���

		for (int i = 0; i < arrStr.length; i++) {
			double tempPMIThreshold = 0;// ����Ĵ��������ƶ�
			if (!stopWordSet.contains(arrStr[i])) {
				for (String word : rockOntoSet) {
					tempPMIThreshold = calculatePMI(arrStr[i], word, documents);
					if (tempPMIThreshold >= threshold) {
						keyWordsMap.put(arrStr[i], tempPMIThreshold);
						buffWriterKeyWords.write(arrStr[i] +"<--->"+word+":PMIΪ"+tempPMIThreshold+ "\r\n");
					}
				}
			}
		}
		return keyWordsMap;
	}

	/**
	 * ͨ�����������ƶ�����ȡ��Ϣ
	 * 
	 * @param filePath
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static Map getTopicWordsByWord2Vec(String filePath, String str)
			throws IOException {
		Map<String, Double> keyWordsMap = new HashMap<String, Double>();
		Writer w = new FileWriter("E:/logByWord2Vec4.txt");
		BufferedWriter buffWriter = new BufferedWriter(w);
		Writer wKeyWords = new FileWriter("E:/KeyWordsByWord2Vec4.txt");
		BufferedWriter buffWriterKeyWords = new BufferedWriter(wKeyWords);
		String encoding = "utf-8";

		// ����ͣ�ô��ļ�
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

		File file = new File(filePath);
		String[] arrStr = str.split("\\s+");// ���ַ������տո���зָ���

		for (int i = 0; i < arrStr.length; i++) {
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
						// �뱾���н��бȽϣ���ֵ����0.8����ȡ����
						double tempWord2VecThreshold = 0;// ����Ĵ��������ƶ�

						if (!stopWordSet.contains(arrStr[i])) {
							tempWord2VecThreshold = vec.wordSimilarity(
									arrStr[i], lineTxt);
							// tempPMIThreshold = calculatePMI(arrStr[i],
							// lineTxt,documents);
							// temSumThreshold = tempWord2VecThreshold *
							// tempPMIThreshold;

							// ����Щ���ϴ浵��keyWordsMap��

							buffWriter.write(lineTxt + "---->" + arrStr[i]
									+ ":" + tempWord2VecThreshold + "\r\n");

							if (tempWord2VecThreshold >= threshold) {
								// System.out.println(".....��ǰ����Ҫ��ȡ����!....");
								// System.out.println("��ȡ������Ϣ:" + arrStr[i]);
								keyWordsMap.put(arrStr[i],
										tempWord2VecThreshold);
								buffWriterKeyWords.write(arrStr[i] + "\r\n");
							}
						} else {
							// System.out.println("����ͣ�ôʣ����˵�...");
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

	public static Map getTopicWordsByword2VecAndPMI(String filePath, String str)
			throws IOException {
		Map<String, Double> keyWordsMap = new HashMap<String, Double>();
		Writer w = new FileWriter("E:/logByword2VecAndPMI4.txt");
		BufferedWriter buffWriter = new BufferedWriter(w);
		Writer wKeyWords = new FileWriter("E:/KeyWordsByword2VecAndPMI4.txt");
		BufferedWriter buffWriterKeyWords = new BufferedWriter(wKeyWords);
		String encoding = "utf-8";

		// ����ͣ�ô��ļ�
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

		File file = new File(filePath);
		String[] arrStr = str.split("\\s+");// ���ַ������տո���зָ���

		DocumentSet documentSet = initData();
		List<Document> documents = documentSet.getDocuments();

		for (int i = 0; i < arrStr.length; i++) {
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
						// �뱾���н��бȽϣ���ֵ����0.8����ȡ����
						double tempWord2VecThreshold = 0;// ����Ĵ��������ƶ�
						double tempPMIThreshold = 0; // ����PMI���ƶ�
						double temSumThreshold = 0;

						if (!stopWordSet.contains(arrStr[i])) {
							tempWord2VecThreshold = vec.wordSimilarity(
									arrStr[i], lineTxt);
							tempPMIThreshold = calculatePMI(arrStr[i], lineTxt,
									documents);
							temSumThreshold = tempWord2VecThreshold
									* tempPMIThreshold;

							if (temSumThreshold > 0) {
								buffWriter.write(lineTxt + "---->" + arrStr[i]
										+ ":" + temSumThreshold + "\r\n");//������е���־��Ϣ
							}
							
							if (temSumThreshold >= PMIAndWord2Vecthreshold) {
								
								keyWordsMap.put(arrStr[i], temSumThreshold);
								buffWriterKeyWords.write(arrStr[i] + "\r\n");//��Źؼ�����Ϣ
							}
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

}
