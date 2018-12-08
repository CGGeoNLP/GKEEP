package me.xiaosheng.word2vec;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.ansj.vec.Learn;
import com.ansj.vec.Word2VEC;
import com.ansj.vec.domain.WordEntry;

public class Word2Vec {

	private Word2VEC vec;
	private boolean loadModel; //�Ƿ��Ѿ�����ģ��
	
	public Word2Vec() {
		vec = new Word2VEC();
		loadModel = false;
	}
	/**
	 * ����Google��Word2Vecģ��(C����ѵ��)
	 * @param modelPath ģ���ļ�·��
	 * @throws IOException
	 */
	public void loadGoogleModel(String modelPath) throws IOException {
		vec.loadGoogleModel(modelPath);
		loadModel = true;
	}
	/**
	 * ����Java��Word2Vecģ��(java����ѵ��)
	 * @param modelPath ģ���ļ�·��
	 * @throws IOException
	 */
	public void loadJavaModel(String modelPath) throws IOException {
		vec.loadJavaModel(modelPath);
		loadModel = false;
	}
	/**
	 * ѵ��Java��Word2Vecģ��
	 * @param trainFilePath ѵ���ļ�·��
	 * @param modelFilePath ģ���ļ�·��
	 * @throws IOException
	 */
	public static void trainJavaModel(String trainFilePath, String modelFilePath) throws IOException {
		Learn learn = new Learn();
	    long start = System.currentTimeMillis();
	    learn.learnFile(new File(trainFilePath));
	    System.out.println("use time " + (System.currentTimeMillis() - start));
	    learn.saveModel(new File(modelFilePath));
	}
	/**
	 * ��ô�����
	 * @param word
	 * @return
	 */
	public float[] getWordVector(String word) {
		if (loadModel == false) {
			return null;
		}
		return vec.getWordVector(word);
	}
	/**
	 * ���������ڻ�
	 * @param vec1
	 * @param vec2
	 * @return
	 */
	private float calDist(float[] vec1, float[] vec2) {
		float dist = 0;
		for (int i = 0; i < vec1.length; i++) {
			dist += vec1[i] * vec2[i];
		}
		return dist;
	}
	/**
	 * ��������ƶ�
	 * @param word1
	 * @param word2
	 * @return
	 */
	public float wordSimilarity(String word1, String word2) {
		if (loadModel == false) {
			return 0;
		}
		float[] word1Vec = getWordVector(word1);
		float[] word2Vec = getWordVector(word2);
		if(word1Vec == null || word2Vec == null) {
			return 0;
		}
		return calDist(word1Vec, word2Vec);
	}
	/**
	 * ��ȡ���ƴ���
	 * @param word
	 * @param maxReturnNum
	 * @return
	 */
	public Set<WordEntry> getSimilarWords(String word, int maxReturnNum) {
		if (loadModel == false)
			return null;
		float[] center = getWordVector(word);
		if (center == null) {
			return Collections.emptySet();
		}
		int resultSize = vec.getWords() < maxReturnNum ? vec.getWords() : maxReturnNum;
		TreeSet<WordEntry> result = new TreeSet<WordEntry>();
		double min = Double.MIN_VALUE;
		for (Map.Entry<String, float[]> entry : vec.getWordMap().entrySet()) {
			float[] vector = entry.getValue();
			float dist = calDist(center, vector);
			if (result.size() <= resultSize) {
				result.add(new WordEntry(entry.getKey(), dist));
				min = result.last().score;
			} else {
				if (dist > min) {
					result.add(new WordEntry(entry.getKey(), dist));
					result.pollLast();
					min = result.last().score;
				}
			}
		}
		result.pollFirst();
		return result;
	}
	/**
	 * �������������б������д����������ƶ�
	 * (��С����0)
	 * @param centerWord ����
	 * @param wordList �����б�
	 * @return
	 */
	private float calMaxSimilarity(String centerWord, List<String> wordList) {
		float max = -1;
		if (wordList.contains(centerWord)) {
			return 1;
		} else {
			for (String word : wordList) {
				float temp = wordSimilarity(centerWord, word);
				if (temp == 0) continue;
				if (temp > max) {
					max = temp;
				}
			}
		}
		if (max == -1) return 0;
		return max;
	}
	/**
	 * ����������ƶ�
	 * ���д���Ȩֵ��Ϊ1
	 * @param sentence1Words ����1�����б�
	 * @param sentence2Words ����2�����б�
	 * @return �������ӵ����ƶ�
	 */
	public float sentenceSimilarity(List<String> sentence1Words, List<String> sentence2Words) {
		if (loadModel == false) {
			return 0;
		}
		if (sentence1Words.isEmpty() || sentence2Words.isEmpty()) {
			return 0;
		}
		float[] vector1 = new float[sentence1Words.size()];
		float[] vector2 = new float[sentence2Words.size()];
		for (int i = 0; i < vector1.length; i++) {
			vector1[i] = calMaxSimilarity(sentence1Words.get(i), sentence2Words);
		}
		for (int i = 0; i < vector2.length; i++) {
			vector2[i] = calMaxSimilarity(sentence2Words.get(i), sentence1Words);
		}
		float sum1 = 0;
		for (int i = 0; i < vector1.length; i++) {
			sum1 += vector1[i];
		}
		float sum2 = 0;
		for (int i = 0; i < vector2.length; i++) {
			sum2 += vector2[i];
		}
		return (sum1 + sum2) / (sentence1Words.size() + sentence2Words.size());
	}
	/**
	 * ����������ƶ�(��Ȩֵ)
	 * ÿһ�����ﶼ��һ����Ӧ��Ȩֵ
	 * @param sentence1Words ����1�����б�
	 * @param sentence2Words ����2�����б�
	 * @param weightVector1 ����1Ȩֵ����
	 * @param weightVector2 ����2Ȩֵ����
	 * @return �������ӵ����ƶ�
	 * @throws Exception �����б��Ȩֵ�������Ȳ�ͬ
	 */
	public float sentenceSimilarity(List<String> sentence1Words, List<String> sentence2Words, float[] weightVector1, float[] weightVector2) throws Exception {
		if (loadModel == false) {
			return 0;
		}
		if (sentence1Words.isEmpty() || sentence2Words.isEmpty()) {
			return 0;
		}
		if (sentence1Words.size() != weightVector1.length || sentence2Words.size() != weightVector2.length) {
			throw new Exception("length of word list and weight vector is different");
		}
		float[] vector1 = new float[sentence1Words.size()];
		float[] vector2 = new float[sentence2Words.size()];
		for (int i = 0; i < vector1.length; i++) {
			vector1[i] = calMaxSimilarity(sentence1Words.get(i), sentence2Words);
		}
		for (int i = 0; i < vector2.length; i++) {
			vector2[i] = calMaxSimilarity(sentence2Words.get(i), sentence1Words);
		}
		float sum1 = 0;
		for (int i = 0; i < vector1.length; i++) {
			sum1 += vector1[i] * weightVector1[i];
		}
		float sum2 = 0;
		for (int i = 0; i < vector2.length; i++) {
			sum2 += vector2[i] * weightVector2[i];
		}
		float divide1 = 0;
		for (int i = 0; i < weightVector1.length; i++) {
			divide1 += weightVector1[i];
		}
		float divide2 = 0;
		for (int j = 0; j < weightVector2.length; j++) {
			divide2 += weightVector2[j];
		}
		return (sum1 + sum2) / (divide1 + divide2);
	}
}
