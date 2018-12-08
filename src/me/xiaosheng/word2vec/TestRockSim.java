package me.xiaosheng.word2vec;

import java.io.IOException;

public class TestRockSim {
	
	public static void main(String[] args) {
		
		Word2Vec vec = new Word2Vec();
		try {
			vec.loadGoogleModel("data/default_vectors.bin");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//����������ƶ� ����ĸ ���� �� ���ʷ�ɰ��
		System.out.println("-----�������ƶ�-----");
		System.out.println("����ʯ|б��ʯ: " + vec.wordSimilarity("����ʯ", "б��ʯ"));
		System.out.println("����ĸ ����|���ʷ�ɰ��: " + vec.wordSimilarity("����", "��ɰ��"));
		
		
		System.out.println("��ʯ| ����: " + vec.wordSimilarity("��ʯ", " ����"));
		
		System.out.println("ɰ��|��ɰ��: " + vec.wordSimilarity("����", "��ɰ��"));
		
		//��ɽ��
		
		System.out.println("�ҽ���|��ɽ��: " + vec.wordSimilarity("�ҽ���", "��ɽ��"));




		
		
	}

}
