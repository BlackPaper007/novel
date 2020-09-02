package com.utlis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.io.StringWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public final class ChapterSpiderUtil {


	/**
	 * 处理具体元素的下标索引
	 */
	public static int Index(String str) {
		String substring = str.substring(str.lastIndexOf(",")+1);
		str = str.length() == substring.length() ? "0" : substring;
		return Integer.parseInt(str);
	}


	/**
	 * Example:顶点小说 > 玄幻小说 > 天地魂变最新章节
	 * @param str
	 * @return
	 */
	public static String getStr(String str) {
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
		File[] files = new File(path).listFiles((dir, name) -> name.endsWith(".txt"));
		Arrays.sort(files, (o1, o2) -> {
			int o1Index=Integer.parseInt(o1.getName().split("\\-")[0]);
			int o2Index=Integer.parseInt(o2.getName().split("\\-")[0]);
			if (o1Index>o2Index) return 1;
			else if(o1Index==o2Index) return 0;
			else return -1;
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
		if (status.contains("连载") || status.contains("连载中")) {
			return 1;
		} else if (status.contains("完结") || status.contains("完成")) {
			return 2;
		} else {
			throw new RuntimeException ("不支持的书籍状态：" + status);
		}
	}

	/**
	 * 字符转换时间戳
	 * @param strDate
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */

	public static Long getData(String strDate,String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = sdf.parse(strDate);
		return date.getTime();
	}

	public static String getStamp(Long timeStamp,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String sd2 = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
		return sd2;
	}

	/**
	 * 获得异常详细并打印出来
	 * @param e
	 * @return
	 */
	public static String getErrorMessage(Exception e) {
		if(null == e){
            return "";
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return  sw.toString();
	}

	/**
	 * 获取伪造的ip
	 */
	public static String getIPProxy(){
		StringBuffer sb = new StringBuffer();
		sb.append(getNum(2,254));
		sb.append(".");
		sb.append(getNum(2,254));
		sb.append(".");
		sb.append(getNum(2,254));
		sb.append(".");
		sb.append(getNum(2,254));
		return sb.toString();
	}

	/**
	 * 获取随机范围内的数字
	 */
	public static int getNum(int start, int end) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		return random.nextInt( (end - start + 1) + start);
	}
}

