package com.dataming.document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.dataming.utils.FileUtils;
import com.dataming.utils.WordUtils;

public class DocumentLoader {

	protected static Logger logger = Logger.getLogger(DocumentLoader.class);

	private static ExecutorService executorService = Executors
			.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	public static void main(String[] args) throws Exception {
		// long start = System.currentTimeMillis();

		// 加载文档的路径，本次中不用分词，已经分词好了
		String path = "D:\\geoscience documents\\";

		DocumentSet documentSet = DocumentLoader.loadDocumentSetByThread(path);
		List<Document> documents = documentSet.getDocuments();
		DocumentUtils.calculateTFIDF_0(documents);
		
		
		
		
		// long end = System.currentTimeMillis();
		// System.out.println("spend time: " + (end - start) / 1000);
		System.exit(0);
	}

	/*
	 * public static DocumentSet loadDocumentSet(String path) { DocumentSet
	 * documentSet = new DocumentSet();
	 * documentSet.setDocuments(loadDocumentList(path)); return documentSet; }
	 */

	public static DocumentSet loadDocumentSetByThread(String path) {
		DocumentSet documentSet = new DocumentSet();
		documentSet.setDocuments(loadDocumentListByThread(path));
		return documentSet;
	}

	/*
	 * public static List<Document> loadDocumentList(String path) {
	 * List<Document> docs = new ArrayList<Document>(); Seg seg =
	 * SegUtils.getComplexSeg(); File[] files = FileUtils.obtainFiles(path); for
	 * (File file : files) { Document document = new Document();
	 * document.setCategory(file.getParentFile().getName());
	 * document.setName(file.getName());
	 * document.setWords(WordUtils.splitFile(file, seg, 2)); docs.add(document);
	 * } return docs; }
	 */

	/**
	 * 
	 * @param path
	 *            ：代表的是路径信息
	 * @return
	 */
	public static List<Document> loadDocumentListByThread(String path) {
		List<Future<Document>> futures = new ArrayList<Future<Document>>();
		String[] filePaths = FileUtils.obtainFilePaths(path);
		for (String filePath : filePaths) {
			futures.add(executorService.submit(new FileToDocumentThread(
					filePath)));
		}
		List<Document> documents = new ArrayList<Document>();
		for (Future<Document> future : futures) {
			try {
				Document document = future.get(30, TimeUnit.SECONDS);
				System.out.println("document: " + document.getName()
						+ " finish!");
				System.out.println(document.getWords().length);
				documents.add(document);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return documents;
	}

	/*
	 * public static DocumentSet loadCrossDocumentList(String path) {
	 * DocumentSet documentSet = new DocumentSet(); List<Document> documents =
	 * loadDocumentList(path); Map<String, List<Document>> cateToDocs = new
	 * HashMap<String, List<Document>>(); for (Document document : documents) {
	 * String category = document.getCategory(); List<Document> docs =
	 * cateToDocs.get(category); if (null == docs) { docs = new
	 * ArrayList<Document>(); cateToDocs.put(category, docs); }
	 * docs.add(document); } for (Map.Entry<String, List<Document>> entry :
	 * cateToDocs.entrySet()) { List<Document> docs = entry.getValue(); for (int
	 * i = 0, len = docs.size(), limit = len / 5; i < limit; i++, len--) {
	 * documentSet
	 * .getTestDocuments().add(docs.remove(RandomUtils.nextInt(len))); }
	 * documentSet.getTrainDocuments().addAll(docs); } return documentSet; }
	 */
}

class FileToDocumentThread implements Callable<Document> {

	private String filePath = null;

	public FileToDocumentThread(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public Document call() throws Exception {
		File file = new File(filePath);
		Document document = new Document();
		document.setCategory(file.getParentFile().getName());
		document.setName(file.getName());
		document.setWords(WordUtils.splitFile(file));
		return document;
	}

}
