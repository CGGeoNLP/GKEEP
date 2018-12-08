package com.dataming.pmi;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import com.dataming.document.Document;
import com.dataming.document.DocumentLoader;
import com.dataming.document.DocumentSet;


public class PointMutualInformation {
	
	public static String path = "D:\\geoscience documents\\";
	
	public static void main(String[] args) {
		new PointMutualInformation().build();
	}
	
	
	//��ʼ���ı�
	private static DocumentSet initData() {
		DocumentSet documentSet = null;
		try {
			documentSet = DocumentLoader.loadDocumentSetByThread(path);
			//reduceDimensionsByCHI(documentSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return documentSet;
	}
	
	public List<Map.Entry<String, Double>> sortMap(Map<String, Double> map) {
		List<Map.Entry<String, Double>> list = 
				new ArrayList<Map.Entry<String, Double>>(map.entrySet());
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
	 * @param word1
	 * @param word2
	 * @param documents
	 * @return
	 */
	private double calculatePMI(String word1, String word2, List<Document> documents) {
		double w1w2Num = statisticsWordsInDocs(documents, word1, word2);
		if (w1w2Num == 0) return 0;
		double w1Num = statisticsWordsInDocs(documents, word1);
		double w2Num = statisticsWordsInDocs(documents, word2);
		return Math.log(documents.size() * w1w2Num / (w1Num * w2Num)) / Math.log(2);
	}
	
	/**
	 * ͳ�ƴʳ����ĵ���
	 * @param documents
	 * @param words
	 * @return
	 */
	private double statisticsWordsInDocs(List<Document> documents, String... words) {
		double sum = 0;
		for (Document document : documents) {
			Set<String> wordSet = document.getWordSet();
			int num = 0;
			for (String word : words) {
				if (wordSet.contains(word)) num++;
			}
			if (num == words.length) sum++; 
		}
		return sum;
	}
	
	
	public void build() {
		DocumentSet documentSet = initData();
		List<Document> documents = documentSet.getDocuments();
		
		int  count = 0;
		for (Document document : documents) {
			String[] words = document.getWords();
			for (String word : words) {
				if (word.equals("������")) {
					count++;
				}
			}
		}
		System.out.println("������count: " + count);
		
		double PMIValue = 0.0;
		
		String sentence ="�ֲ� �� Ӣ���� �� ��ɽ�� �� ������м ��м �۽��� �� ������ ɰ���� �� �������� �� ���� �� ֲ�� ��ʯ ��Ƭ �� �� �ڷ� �� �� ɳ����ӷ� �� ����ͷ������ �� ���� ��ʯ �� ��ֲ�� ��ʯ ��";
		
		String [] senArr = sentence.split("\\s+");
		
		for (int i = 0; i < senArr.length; i++) {
			for (int j = 1; j < senArr.length; j++) {
				

				if (senArr[i].equals(senArr[j])){
					continue;
				}else {
					PMIValue = calculatePMI(senArr[i], senArr[j], documents);
					if (PMIValue >= 1) {
						System.out.println("senArr[i]:"+senArr[i]+"senArr[j]--->"+senArr[j]+PMIValue);
					}
				}
				

				
				
			}
		}
		
/*		c = calculatePMI("����", "ɰ��", documents);
		
		System.out.println("c: " + c);*/
		
		/*for (Document document : documents) {
			String[] words = document.getWords();
			System.out.println(words);
			for (String word : words) {
				double c = 0.0;
				for (String commendatory : commendatories) {
					c += calculatePMI(word, commendatory, documents);
				}
				
				//������
				c = calculatePMI(word, "������", documents);
				
				if (c != 0) System.out.println("c: " + c);
			}
		}*/
		
		
	}
	
	

}
