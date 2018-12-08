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
		//º∆À„¥ ”Ôœ‡À∆∂» æÓ‘∆ƒ∏ ∞Â—“ °¢ ±‰÷ ∑€…∞—“
		System.out.println("-----¥ ”Ôœ‡À∆∂»-----");
		System.out.println("Ω«…¡ Ø|–±≥§ Ø: " + vec.wordSimilarity("Ω«…¡ Ø", "–±≥§ Ø"));
		System.out.println("æÓ‘∆ƒ∏ ∞Â—“|±‰÷ ∑€…∞—“: " + vec.wordSimilarity("∞Â—“", "∑€…∞—“"));
		
		
		System.out.println("—“ Ø| ∞Â—“: " + vec.wordSimilarity("—“ Ø", " ∞Â—“"));
		
		System.out.println("…∞—“|∑€…∞—“: " + vec.wordSimilarity("∞Â—“", "∑€…∞—“"));
		
		//ª…Ω—“
		
		System.out.println("—“Ω¨—“|ª…Ω—“: " + vec.wordSimilarity("—“Ω¨—“", "ª…Ω—“"));




		
		
	}

}
