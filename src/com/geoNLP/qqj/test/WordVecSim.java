package com.geoNLP.qqj.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import me.xiaosheng.word2vec.Word2Vec;

/**
 * 加载训练好的词向量来进行相似性判断 两两比较
 * 
 * @author Administrator
 * 
 */
public class WordVecSim {

	static final String FILEPATH_STRING = "data/岩石本体.txt";
	static final double threshold = 0.5;// 阈值
	public static final String stopWordTable = "data/stopwords.txt";

	/**
	 * 阻尼系数（Damping Factor)，一般取值为0.85
	 */
	static final float d = 0.85f;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static Map word2VecSimCompare(String sentence) throws IOException {

		Map<String, Double> keyWordsMap = new HashMap<String, Double>();
		// =============加载词向量
		Word2Vec vec = new Word2Vec();
		try {
			vec.loadGoogleModel("data/default_vectors.bin");
		} catch (IOException e) {
			e.printStackTrace();
		}

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

		String[] senArr = sentence.split("\\s+");
		double word2vecValue = 0;
		double maxValue = 0;
		int length = senArr.length;

		for (int i = 0; i < senArr.length; i++) {

			if (!stopWordSet.contains(senArr[i])) {

				for (int j = 0; j < senArr.length; j++) {
					if (!stopWordSet.contains(senArr[j])) {

						word2vecValue = word2vecValue
								+ vec.wordSimilarity(senArr[i], senArr[j]);
					}
					// ==================针对每一个词与后面的相似性进行叠加加权
				}
				//maxValue = 1 - d + d / length * word2vecValue;
				maxValue = word2vecValue/ length;
				System.out.println(senArr[i] + "---->" + maxValue);
				keyWordsMap.put(senArr[i], maxValue);
				maxValue = 0;
				word2vecValue = 0;
			}
		}
		
		return keyWordsMap;

	}
	
	/**
	 * 為了排序
	 * @param map
	 * @return
	 */
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

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String sentence = "局部 夹 英安质 、 安山质 含 角砾岩屑 晶屑 熔结岩 ， 凝灰质 砂砾岩 夹 沉凝灰岩 ， 其内 见 植物 化石 碎片 ， 在 邻幅 的 北 沙拉大队幅 的 满克头鄂博组 中 发现 化石 等 动植物 化石 。";
		
		String sentence1 ="测区 已经 火山活动 较为 频繁 ， 侵入岩 、  火山岩 十分 发育 ，区域 成矿 地质 条件 较好 ，是 一个 很好 的 找矿 远景 区域 ， 现将 地层 、 岩浆岩 和 构造 等 成矿 条件 介绍 如下 。";
		
		String sentence2 ="以 流纹质 （ 含集块 、 含角砾 ） 熔结凝灰岩 及 流纹质 含 集块 角砾 凝灰岩 交替 出现 为主 ， 其次 有 流纹质 含 集块 火山角砾岩 、 流纹质 含 集块 含角砾 凝灰岩 等 。 其中 集块 、 角砾 成分 大多 为 同源 碎屑 ， 岩性 有 流纹质 熔岩 、 凝灰岩 、 熔结凝灰岩 等 。  ";
		
		String sentence3="区 含 铁矿层 产于 上 太古界 五台超群 石咀亚群 金岗库组 片麻岩 中 ， 该 地层 经受 了 强烈 而 广泛 的 区域变质 作用 、 混合 岩化 作用 及 局部 的 构造 变质 作用 ， 形成 了 角闪岩相 的 黑云角闪斜长片麻岩 、 磁铁 石英岩 等 。";
		
		String sentence4="岩性 组合 为 深灰色 、 灰色 绢云母 板岩 、 变质  粉砂岩 、 变质 细纱岩 ， 呈 单斜 产出 ， 厚度 大于 2908.7 m ， 在 本套 地层 中 尚未 发现 化石 。";
		
		String sentence5="岩石 组合 为 褐灰色 薄层状 细粒 石英砂岩 与 灰绿色 泥质 板岩 互层 、 浅灰色 中层 白云质 灰岩 、 灰绿色 安山质 晶屑 凝灰岩 、 杏仁状 玄武安山岩 、 生物碎屑 泥晶灰岩 、 含 燧石团块 泥晶灰岩 、 含 黄铁矿 结核 泥晶灰岩 、 硅质 条带 泥晶灰岩 。 ";
		
		String sentence6="未 分 三叠系 ， 该 组分 布 于 西部 ， 临近 国境线 ， 与 未分 中 二叠统 为 断层 接触 ， 主要 岩性 为 黄灰色 厚层状 含 固着 蛤泥晶 灰岩 与 黄灰色 厚层 状泥晶 灰岩 互层 。";
		
		String sentence7="庐枞 中生代 火山盆地 位于 长江 中下游 断陷带 内 , 地处 扬子板块 的 北缘 。 " ;		
		
		 Map<String, Double> mapsbyPMIandWord2vec = word2VecSimCompare(sentence7);
		 ByValueComparator bvcbyPMIandWord2vec = new
		 ByValueComparator(mapsbyPMIandWord2vec); ArrayList<String>
		 keysbyPMIandWord2vec = new ArrayList<String>(mapsbyPMIandWord2vec.keySet());
		 Collections.sort(keysbyPMIandWord2vec, bvcbyPMIandWord2vec);
		 System.out.println("通过混合方法相似度:"+keysbyPMIandWord2vec);
		

		
	}

}
