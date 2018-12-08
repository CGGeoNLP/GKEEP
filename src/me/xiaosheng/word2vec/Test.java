package me.xiaosheng.word2vec;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.ansj.vec.domain.WordEntry;

import me.xiaosheng.util.Segment;

public class Test {

	public static void main(String[] args) throws Exception {
		Word2Vec vec = new Word2Vec();
		try {
			vec.loadGoogleModel("data/default_vectors.bin");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		//����������ƶ�
		System.out.println("-----�������ƶ�-----");
		System.out.println("����ʯ|б��ʯ: " + vec.wordSimilarity("����ʯ", "б��ʯ"));
		System.out.println("�����|����: " + vec.wordSimilarity("�����", "����"));
		System.out.println("�����|��: " + vec.wordSimilarity("�����", "��"));
		//��ȡ���ƵĴ���
		Set<WordEntry> similarWords = vec.getSimilarWords("����ʡ", 10);
		System.out.println("�� [����ʡ] �������ƵĴ���:");
		for(WordEntry word : similarWords) {
			System.out.println(word.name + " : " + word.score);
		}
		//����������ƶ�
		System.out.println("-----�������ƶ�-----");
		String s1 = "�����ж�����·����ʩ������ɾֲ�����������ʻ�ǳ�������";
		String s2 = "��������ж�����·��ʩ�������²��ֵ�����ͨӵ�£���������ͨ�С�";
		String s3 = "������һ�������ĳ��У��ļ��������������档";
		System.out.println("s1: " + s1);
		System.out.println("s2: " + s2);
		System.out.println("s3: " + s3);
		//�ִʣ���ȡ�����б�
		List<String> wordList1 = Segment.getWords(s1);
		List<String> wordList2 = Segment.getWords(s2);
		List<String> wordList3 = Segment.getWords(s3);
		//�������ƶ�(���д���Ȩֵ��Ϊ1)
		System.out.println("�������ƶ�:");
		System.out.println("s1|s1: " + vec.sentenceSimilarity(wordList1, wordList1));
		System.out.println("s1|s2: " + vec.sentenceSimilarity(wordList1, wordList2));
		System.out.println("s1|s3: " + vec.sentenceSimilarity(wordList1, wordList3));
		//�������ƶ�(���ʡ�����Ȩֵ��Ϊ1��������Ϊ0.8)
		System.out.println("�������ƶ�(���ʡ�����Ȩֵ��Ϊ1��������Ϊ0.8):");
		float[] weightArray1 = Segment.getPOSWeightArray(Segment.getPOS(s1));
		float[] weightArray2 = Segment.getPOSWeightArray(Segment.getPOS(s2));
		float[] weightArray3 = Segment.getPOSWeightArray(Segment.getPOS(s3));
		System.out.println("s1|s1: " + vec.sentenceSimilarity(wordList1, wordList1, weightArray1, weightArray1));
		System.out.println("s1|s2: " + vec.sentenceSimilarity(wordList1, wordList2, weightArray1, weightArray2));
		System.out.println("s1|s3: " + vec.sentenceSimilarity(wordList1, wordList3, weightArray1, weightArray3));
//		try {
//			Word2Vec.trainJavaModel("data/train.txt", "data/test.model");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
