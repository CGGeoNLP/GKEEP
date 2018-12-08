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

	// 本体路径
	static final String FILEPATH_STRING = "data/岩石本体.txt";
	// 停用词词表
	public static final String stopWordTable = "data/stopwords.txt";
	// 语料库路径
	public static String path = "D:\\geoscience documents\\";

	static final double threshold = 0.5;// 阈值

	static final double PMIAndWord2Vecthreshold = 0.8;// 阈值

	public static void main(String[] args) throws IOException {
		System.out.println("-----提取地质报告中地质对象-----");
		String sent1 = "区域 成矿带 划分 ， 根据 内蒙古 成矿省 的 矿带 划分 ， 测区 成矿 背景 处于 内蒙古 大兴安岭 成矿省 Ⅱ 级 成矿带 ， 突泉 － 林西 华力西 、 燕山期 铁（锡） 、 铜 、 铅锌 、 银 、铌（钽）  Ⅲ 级 成矿带 ， 索仑镇 －黄岗 铁（锡） 、 铜 、 锌  成矿带 ，沙不楞山 铜 、 铅 、 锌 成矿带 。";
		String sent2 = "测区 已经 火山活动 较为 频繁  ， 侵入岩 、  火山岩 十分 发育 ，区域 成矿 地质 条件 较好  ， 是 一个 很好 的 找矿 远景 区域  ，  现将 地层 、 岩浆岩 和 构造 等 成矿 条件 介绍 如下  。";
		String sent3 = "岩性 组合 为 深灰色 、 灰色 绢云母 板岩 、 变质  粉砂岩 、 变质 细纱岩 ， 呈 单斜 产出 ， 厚度 大于 2908.7 m ， 在 本套 地层 中 尚未 发现 化石 。 ";

		// String sent4 =
		// "以 流纹质 （ 含集块 、 含角砾 ） 熔结凝灰岩 及 流纹质 含 集块 角砾 凝灰岩 交替 出现 为主 ， 其次 有 流纹质 含 集块 火山角砾岩 、 流纹质 含 集块 含角砾 凝灰岩 等 。 其中 集块 、 角砾 成分 大多 为 同源 碎屑 ， 岩性 有 流纹质 熔岩 、 凝灰岩 、 熔结凝灰岩 等 。  ";

		String sent4 = " 岩石 组合 为 褐灰色 薄层状 细粒 石英砂岩 与 灰绿色 泥质 板岩 互层 、 浅灰色 中层 白云质 灰岩 、 灰绿色 安山质 晶屑 凝灰岩 、 杏仁状 玄武安山岩 、 生物碎屑 泥晶灰岩 、 含 燧石团块 泥晶灰岩 、 含 黄铁矿 结核 泥晶灰岩 、 硅质 条带 泥晶灰岩 。 未 分 三叠系 ， 该 组分 布 于 西部 ， 临近 国境线 ， 与 未分 中 二叠统 为 断层 接触 ， 主要 岩性 为 黄灰色 厚层状 含 固着 蛤泥晶 灰岩 与 黄灰色 厚层 状泥晶 灰岩 互层 。 ";
		
		
		 Map<String, Double> mapsbyPMIandWord2vec =getTopicWordsByPMI(sent2);
		 ByValueComparator bvcbyPMIandWord2vec = new
		 ByValueComparator(mapsbyPMIandWord2vec); ArrayList<String>
		 keysbyPMIandWord2vec = new ArrayList<String>(mapsbyPMIandWord2vec.keySet());
		 Collections.sort(keysbyPMIandWord2vec, bvcbyPMIandWord2vec);
		 System.out.println("通过PMI相似度:"+keysbyPMIandWord2vec);

	}
	
	
	

	/**
	 * 读取岩石本体，存放到HashMap中
	 * 
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public static Set initOnto() throws IOException {

		// 读入本体文件
		BufferedReader rockOntoFileBr = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(
						FILEPATH_STRING)), "UTF-8"));
		// 用来存放本体词的集合
		Set<String> WordSet = new HashSet<String>();
		// 初如化停用词集
		String stopWord = null;
		for (; (stopWord = rockOntoFileBr.readLine()) != null;) {
			WordSet.add(stopWord);
		}
		return WordSet;
	}

	// 初始化文本
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
	 * 计算点间互信息
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
	 * 统计词出现文档数
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
	 * 通过PMI来提取相似度信息
	 * 
	 * @param filePath
	 * @param str
	 * @return 2017年11月28日17:04:12
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

		// ==============加载stopword
		// 读入停用词文件
		String encoding = "utf-8";
		BufferedReader StopWordFileBr = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(
						stopWordTable)), encoding));
		// 用来存放停用词的集合
		Set<String> stopWordSet = new HashSet<String>();
		// 初如化停用词集
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
					// ==================针对每一个词与后面的相似性进行叠加加权
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
	 * 通过词向量相似度来提取信息
	 * 
	 * @param filePath
	 * @param str
	 * @return 2017年11月28日17:06:08
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
	 * word2vec and PMI 混合方法
	 * 
	 * @param str
	 * @throws IOException
	 */
	public static void getTopicWordsByword2VecAndPMI(String str)
			throws IOException {

		// =========================
		// 读入停用词文件
		String encoding = "utf-8";
		BufferedReader StopWordFileBr = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(
						stopWordTable)), encoding));
		// 用来存放停用词的集合
		Set<String> stopWordSet = new HashSet<String>();
		// 初如化停用词集
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

		// =============================混合方法
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

		System.out.println("执行完毕====");

	}
}
