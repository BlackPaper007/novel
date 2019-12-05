package com.utlis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;

import com.novelEnum.Site;

public final class ChapterSpiderUtil {

	/**
	 * 过滤不需要的标签
	 * @param url
	 * @param doc
	 * @throws Exception
	 */
	public static void selectorFiter(String url,Document doc) throws Exception {
		List<String> fiter = Config.getFiter(Site.getEnumByUrl(url));
		for(int i=0;i<fiter.size();i++)
		if(fiter.get(i)!=null) doc.select(fiter.get(i)).remove();
	}
	
	/**
	 * 处理具体元素的下标索引
	 */
	public static int Index(String str) {
		String substring = str.substring(str.lastIndexOf(",")+1);
		str = str.length() == substring.length() ? "0" : substring;
		return Integer.parseInt(str);
	}
	
	public static String getMiddleStr(String str) {
		return str.substring(str.indexOf(">")+1,str.lastIndexOf(">")).trim();
	}
	
	/**
	 * 多个文件合并为一个文件，合并规则：按文件名分割排序
	 * 
	 * @param path        基础目录，该根目录下的所有文本文件都会被合并到 mergeToFile
	 * @param mergeToFile 被合并的文本文件，这个参数可以为null,合并后的文件保存在制定文件夹
	 * @param title       小说标题
	 * @param delFile     合并后是否删除碎片文件
	 */
	public static void multiFileMerge(String path, String title, String mergeToFile, boolean delFile) {
		mergeToFile = mergeToFile == null ? path + "/merge/" + title + ".txt" : mergeToFile;
		File[] files = new File(path).listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				int o1Index=Integer.parseInt(o1.getName().split("\\-")[0]);
				int o2Index=Integer.parseInt(o2.getName().split("\\-")[0]);
				if (o1Index>o2Index) return 1;
				else if(o1Index==o2Index) return 0;
				else return -1;
			}
		});
		PrintWriter out = null;
		try {
			out = new PrintWriter(new File(mergeToFile), "UTF-8");
			for (File file : files) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
				String line = null;
				//写入文件
				while ((line = reader.readLine()) != null) {
					out.println(line);
				}
				reader.close();
				if (delFile) {
					file.delete();
				}
			}
		} catch (IOException e) {
			throw new RuntimeException();
		} finally {
			out.close();
		}
	}
	
	/**
	 * 获取书籍的状态
	 * @param status
	 * @return
	 */
	public static int getNovelStatus(String status) {
		if (status.contains("连载")) {
			return 1;
		} else if (status.contains("完结") || status.contains("完成")) {
			return 2;
		} else {
			throw new RuntimeException ("不支持的书籍状态：" + status);
		}
	}
	
	public static Date getData(String strDate,String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = sdf.parse(strDate);
		return date;
	}
	
}