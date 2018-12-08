package com.dataming.document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class DocumentUtils {
	
	/**
	 * ����TF
	 * @param documents
	 */
	public static void calculateTF(List<Document> documents) {
		for (Document document : documents) {
			Map<String, Double> tfWords = document.getTfWords();
			for (String word : document.getWords()) {
				Double count = tfWords.get(word);
				tfWords.put(word, null == count ? 1 : count + 1);
			}
			System.out.println("doc " + document.getName() + " calculate tf finish");
		}
	}
	
	/**
	 * ����TFIDF
	 * TF�����Ǵ�Ƶ�����ܴ���
	 * @param documents
	 */
	public static void calculateTFIDF_0(List<Document> documents) {
		int docTotalCount = documents.size();
		for (Document document : documents) {
			Map<String, Double> tfidfWords = document.getTfidfWords();
			int wordTotalCount = document.getWords().length;
			Map<String, Integer> docWords = DocumentHelper.wordsInDocStatistics(document);
			for (String word : docWords.keySet()) {
				double wordCount = docWords.get(word);
				double tf = wordCount / wordTotalCount;
				double docCount = DocumentHelper.wordInDocsStatistics(word, documents) + 1;
				double idf = Math.log(docTotalCount / docCount);
				double tfidf = tf * idf;
				tfidfWords.put(word, tfidf);
			}
			System.out.println("doc " + document.getName() + " calculate tfidf finish");
		}
	}
	
	/**
	 * ����TFIDF
	 * TF�����Ǵ�Ƶ���Դ�Ƶ�����
	 * @param documents
	 */
	public static void calculateTFIDF_1(List<Document> documents) {
		int docTotalCount = documents.size();
		for (Document document : documents) {
			Map<String, Double> tfidfWords = document.getTfidfWords();
			List<Map.Entry<String, Double>> list = 
					new ArrayList<Map.Entry<String, Double>>(tfidfWords.entrySet());
			Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
				@Override
				public int compare(Entry<String, Double> o1,
						Entry<String, Double> o2) {
					return -o1.getValue().compareTo(o2.getValue());
				}
			});
			if (list.size() == 0) continue; 
			double wordTotalCount = list.get(0).getValue();
			Map<String, Integer> docWords = DocumentHelper.wordsInDocStatistics(document);
			for (String word : docWords.keySet()) {
				double wordCount = docWords.get(word);
				double tf = wordCount / wordTotalCount;
				double docCount = DocumentHelper.wordInDocsStatistics(word, documents) + 1;
				double idf = Math.log(docTotalCount / docCount);
				double tfidf = tf * idf;
				tfidfWords.put(word, tfidf);
			}
			System.out.println("doc " + document.getName() + " calculate tfidf finish");
		}
	}
	
	/**
	 * �����ĵ�������ƶ�
	 * @param documents
	 * @param idistance
	 *//*
	public static void calculateSimilarity(List<Document> documents, IDistance idistance) {
		for (Document document : documents) {
			for (Document odocument : documents) {
				double distance = idistance.distance(document.getTfidfWords(), odocument.getTfidfWords());
				DocumentSimilarity docSimilarity = new DocumentSimilarity();
				docSimilarity.setDoc1(document);
				docSimilarity.setDoc2(odocument);
				docSimilarity.setDistance(distance);
				document.getSimilarities().add(docSimilarity);
			}
			System.out.println("doc " + document.getName() + " calculate similar finish");
		}
	}*/
	
	/**
	 * ת��Ϊ���� ��Ϊ�ĵ� ��Ϊ��
	 * @param documents
	 * @return
	 */
	public static double[][] convertToDocTermMatrix(List<Document> documents) {
		Collection<String> allWords = DocumentHelper.wordsInDocs(documents, true);
		int docSize = documents.size(), allWordsSize = allWords.size();
		double[][] matrix = new double[docSize][allWordsSize];
		for (int i = 0; i < docSize; i++) {
			Map<String, Double> tfidfWords = documents.get(i).getTfidfWords();
			int j = 0;
			for (String word : allWords) {
				if (tfidfWords.containsKey(word)) {
					matrix[i][j] = tfidfWords.get(word).doubleValue();
				} else {
					matrix[i][j] = 0;
				}
				j++;
			}
		}
		return matrix;
	}
	
	/**
	 * ת��Ϊ���� ��Ϊ�� ��Ϊ�ĵ�
	 * @param documents
	 * @return
	 */
	public static double[][] convertToTermDocMatrix(List<Document> documents) {
		Collection<String> allWords = DocumentHelper.wordsInDocs(documents, true);
		int docSize = documents.size(), allWordsSize = allWords.size();
		double[][] matrix = new double[allWordsSize][docSize];
		for (int i = 0; i < docSize; i++) {
			Map<String, Double> tfidfWords = documents.get(i).getTfidfWords();
			int j = 0;
			for (String word : allWords) {
				if (tfidfWords.containsKey(word)) {
					matrix[j][i] = tfidfWords.get(word).doubleValue();
				} else {
					matrix[j][i] = 0;
				}
				j++;
			}
		}
		return matrix;
	}
	
	/**
	 * �������ƾ���
	 * A * A' = D * S * S' * D'
	 * A' * A = D * S' * S * D'
	 * �Ƚ���������  ��"����"�˷�����i�е�j�б���������i��j�����Ƴ̶�
	 * �Ƚ������ļ���"����"�˷�����i�е�j�б������ļ�i��j�����Ƴ̶�
	 * svd����ֻ�ʺ��������������ľ����������С���������ɶ���ת�þ�����SVD�ֽ� 
	 * @param matrix
	 * @param dimension ά��
	 * @return
	 */
	/*public static double[][] calculateSimilarityMatrix(double[][] matrix, int dimension) {
		Matrix sMatrix = new Matrix(matrix);  
		boolean flag = matrix.length >= matrix[0].length ? true : false;
        SingularValueDecomposition svd = flag ? sMatrix.svd() : sMatrix.transpose().svd();  
        Matrix D = svd.getU();  
        Matrix S = svd.getS();  
        for(int i = dimension; i < S.getRowDimension(); i++){//����dimensionά  
            S.set(i, i, 0);  
        }  
        return flag ? D.times(S.times(S.transpose().times(D.transpose()))).getArray() 
        		: D.times(S.transpose().times(S.times(D.transpose()))).getArray();  
	}*/
	
	public static void main(String[] args) throws Exception {
		String path = DocumentUtils.class.getClassLoader().getResource("΢��").toURI().getPath();
		DocumentSet documentSet = DocumentLoader.loadDocumentSetByThread(path);
		calculateTFIDF_0(documentSet.getDocuments());
//		calculateSimilarity(documentSet.getDocuments(), new CosineDistance());
//		double[][] tdmatrix = convertToDocTermMatrix(documentSet.getDocuments());
		double[][] tdmatrix = convertToTermDocMatrix(documentSet.getDocuments());
		System.out.println("matrix row: " + tdmatrix.length + " col: " + tdmatrix[0].length);
		//double[][] matrix = calculateSimilarityMatrix(tdmatrix, 100);
		//System.out.println("matrix row: " + matrix.length + " col: " + matrix[0].length);
//		for (int i = 0, row = matrix.length; i < row; i++) {
//			for (int j = 0, col = matrix[0].length; j < col; j++) {
//				System.out.print(matrix[i][j] + ",");
//			}
//			System.out.println();
//		}
	}
	
}
