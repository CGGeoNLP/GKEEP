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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.projectReadExcel.Common;
import com.projectReadExcel.geodemo.GeoOntology;
import com.projectReadExcel.geodemo.ReadGeoExcel;

import me.xiaosheng.word2vec.Word2Vec;

/**
 * 采用本体作为监督，然后训练好的词向量作为相似性判断来进行主题或关键词提取 ontology 只是一个最基本的实验，还没有应用到LDA模型
 * 
 * @author Administrator
 * 
 */
public class GeoScienceOntoWord2Vec {

	static final String FILEPATH_STRING = "data/岩石本体.txt";

	// 停用词词表
	public static final String stopWordTable = "data/StopWordTable.txt";

	static final double threshold = 0.5;// 阈值

	// 处理地质本体
	public static void comnpareToOnto(String filePath, String str,
			BufferedWriter buffWriter) throws IOException {
		String encoding = "utf-8";

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

		Word2Vec vec = new Word2Vec();
		try {
			vec.loadGoogleModel("data/default_vectors.bin");
		} catch (IOException e) {
			e.printStackTrace();
		}
		File file = new File(filePath);

		if (file.isFile() && file.exists()) { // 判断文件是否存在
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), encoding);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);

			// lineTxt：每次获取的一行字符串
			String lineTxt = null;
			try {
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println("str = " + str);
					// 与本体中进行比较，阈值大于0.8就提取出来
					double tempThreshold = 0;
					tempThreshold = vec.wordSimilarity(str, lineTxt);

					buffWriter.write(lineTxt + "---->" + str + ":"
							+ tempThreshold + "\r\n");

					if (tempThreshold >= threshold) {
						System.out.println(".....当前词需要提取出来!....");
						System.out.println("提取主题信息:" + str);

					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		buffWriter.close();
		// w.close();
		// System.out.println("当前处理完毕....");

	}

	public static Map getTopicWords(String filePath, String str)
			throws IOException {

		
		
		Map<String, Double> keyWordsMap = new HashMap<String, Double>();
		Writer w = new FileWriter("E:/log4.txt");
		BufferedWriter buffWriter = new BufferedWriter(w);
		Writer wKeyWords = new FileWriter("E:/KeyWords4.txt");
		BufferedWriter buffWriterKeyWords = new BufferedWriter(wKeyWords);
		String encoding = "utf-8";
		
		// 读入停用词文件
		BufferedReader StopWordFileBr = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(stopWordTable)),encoding));
		// 用来存放停用词的集合
		Set<String> stopWordSet = new HashSet<String>();
		// 初如化停用词集
		String stopWord = null;
		for (; (stopWord = StopWordFileBr.readLine()) != null;) {
			//System.out.println(stopWord);
			stopWordSet.add(stopWord);
		}

		File file = new File(filePath);
		String[] arrStr = str.split("\\s+");// 将字符串按照空格进行分隔开

		for (int i = 0; i < arrStr.length; i++) {
			System.out.println(arrStr[i]);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

			Word2Vec vec = new Word2Vec();
			try {
				vec.loadGoogleModel("data/default_vectors.bin");
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);

				// lineTxt：每次获取的一行字符串
				String lineTxt = null;
				try {
					while ((lineTxt = bufferedReader.readLine()) != null) {
						// System.out.println("str = " + str);
						// 与本体中进行比较，阈值大于0.8就提取出来
						double tempThreshold = 0;
						
						if (!stopWordSet.contains(arrStr[i])) {
							tempThreshold = vec.wordSimilarity(arrStr[i], lineTxt);

							
							//将这些集合存档到keyWordsMap中
							
							
							
							buffWriter.write(lineTxt + "---->" + arrStr[i] + ":"
									+ tempThreshold + "\r\n");

							if (tempThreshold >= threshold) {
								//System.out.println(".....当前词需要提取出来!....");
								System.out.println("提取主题信息:" + arrStr[i]);
								
								keyWordsMap.put(arrStr[i], tempThreshold);
								
								buffWriterKeyWords.write(arrStr[i]+"\r\n");
							}
						}else {
							System.out.println("出现停用词，过滤掉...");
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

	public static void main(String[] args) throws IOException {

		System.out.println("-----提取地质报告中地质对象-----");
		String sent1 = "区域 成矿带 划分 ， 根据 内蒙古 成矿省 的 矿带 划分 ， 测区 成矿 背景 处于 内蒙古 大兴安岭 成矿省 Ⅱ 级 成矿带 ， 突泉 － 林西 华力西 、 燕山期 铁（锡） 、 铜 、 铅锌 、 银 、铌（钽）  Ⅲ 级 成矿带 ， 索仑镇 －黄岗 铁（锡） 、 铜 、 锌  成矿带 ，沙不楞山 铜 、 铅 、 锌 成矿带 。";
		String sent2 = "测区 已经 火山活动 较为 频繁 ， 侵入岩 、  火山岩 十分 发育 ，区域 成矿 地质 条件 较好 ，是 一个 很好 的 找矿 远景 区域 ， 现将 地层 、 岩浆岩 和 构造 等 成矿 条件 介绍 如下 。";
		String sent3 = "岩性 组合 为 深灰色 、 灰色 绢云母 板岩 、 变质  粉砂岩 、 变质 细纱岩 ， 呈 单斜 产出 ， 厚度 大于 2908.7 m ， 在 本套 地层 中 尚未 发现 化石 。 ";

		String sent4 ="以 流纹质 （ 含集块 、 含角砾 ） 熔结凝灰岩 及 流纹质 含 集块 角砾 凝灰岩 交替 出现 为主 ， 其次 有 流纹质 含 集块 火山角砾岩 、 流纹质 含 集块 含角砾 凝灰岩 等 。 其中 集块 、 角砾 成分 大多 为 同源 碎屑 ， 岩性 有 流纹质 熔岩 、 凝灰岩 、 熔结凝灰岩 等 。  ";
		


		Map<String, Double> maps = getTopicWords(FILEPATH_STRING, sent4);
		// getTopicWords(sent2);
		// getTopicWords(sent3);
		
		//List<Map.Entry<String, Integer>> list = new ArrayList<>();
		
		ByValueComparator bvc = new ByValueComparator(maps);
		ArrayList<String> keys = new ArrayList<String>(maps.keySet());
		Collections.sort(keys, bvc);
		
		System.out.println(keys);

	}

}
