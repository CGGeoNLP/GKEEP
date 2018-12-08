package com.dataming.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** �ĵ�����*/
public class Document {
	/** �ļ�����*/
	private String name = null;
	/** ������������*/
	private String category = null;
	/** ���·ִ�*/
	private String[] words = null;
	/** ���·ִ�����*/
	private double[] wordsVec = null;
	/** ���·ִʶ�λ*/
	private int[] wordsIndex = null;
	/** �������TF*/
	private Map<String, Double> tfWords = null;
	/** �������TFIDF*/
	private Map<String, Double> tfidfWords = null;
	/** �������CHI*/
	private Map<String, Double> chiWords = null;
	/** �������ƶ�*/
	private List<DocumentSimilarity> similarities = null;
	/** ��Ӧ��*/
	private double fit = 0;
	/** ѡ�����*/
	private double selectionP = 0;
	/** ���۸���*/
	private double accumulationP = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String[] getWords() {
		return words;
	}
	
	public Set<String> getWordSet() {
		Set<String> wordSet = new HashSet<String>();
		wordSet.addAll(Arrays.asList(words));
		return wordSet;
	}

	public void setWords(String[] words) {
		this.words = words;
	}
	
	public double[] getWordsVec() {
		return wordsVec;
	}

	public void setWordsVec(double[] wordsVec) {
		this.wordsVec = wordsVec;
	}
	
	public int[] getWordsIndex() {
		return wordsIndex;
	}

	public void setWordsIndex(int[] wordsIndex) {
		this.wordsIndex = wordsIndex;
	}

	public Map<String, Double> getTfWords() {
		if (null == tfWords) {
			tfWords = new HashMap<String, Double>();
		}
		return tfWords;
	}

	public void setTfWords(Map<String, Double> tfWords) {
		this.tfWords = tfWords;
	}

	public Map<String, Double> getTfidfWords() {
		if (null == tfidfWords) {
			tfidfWords = new HashMap<String, Double>();
		}
		return tfidfWords;
	}

	public void setTfidfWords(Map<String, Double> tfidfWords) {
		this.tfidfWords = tfidfWords;
	}
	
	public Map<String, Double> getChiWords() {
		if (null == chiWords) {
			chiWords = new HashMap<String, Double>();
		}
		return chiWords;
	}

	public void setChiWords(Map<String, Double> chiWords) {
		this.chiWords = chiWords;
	}
	
	public Map<String, Double> getSimilaritiesMap() {
		Map<String, Double> similaritiesMap = new HashMap<String, Double>();
		for (DocumentSimilarity similarity : getSimilarities()) {
			similaritiesMap.put(similarity.getDoc2().getName(), similarity.getDistance());
		}
		return similaritiesMap;
	}

	public List<DocumentSimilarity> getSimilarities() {
		if (null == similarities) {
			similarities = new ArrayList<DocumentSimilarity>();
		}
		return similarities;
	}

	public void setSimilarities(List<DocumentSimilarity> similarities) {
		this.similarities = similarities;
	}
	
	/**
	 * �����ĵ����ƶȾ�ֵ
	 * @return
	 */
	public double calculateSimilarityMean() {
		List<DocumentSimilarity> list = getSimilarities();
		if (list.size() == 0) {
			return 0;
		}
		double sum = 0;
		for (DocumentSimilarity similarity : list) {
			sum += similarity.getDistance();
		}
		return sum / list.size();
	}

	public double getFit() {
		return fit;
	}

	public void setFit(double fit) {
		this.fit = fit;
	}

	public double getSelectionP() {
		return selectionP;
	}

	public void setSelectionP(double selectionP) {
		this.selectionP = selectionP;
	}

	public double getAccumulationP() {
		return accumulationP;
	}

	public void setAccumulationP(double accumulationP) {
		this.accumulationP = accumulationP;
	}

	

}
