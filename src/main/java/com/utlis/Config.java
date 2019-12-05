package com.utlis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.impl.abs.AbstractSpider;
import com.novelEnum.Site;

/**
 * 提供系统初始化
 * @author smile
 *
 */
public final class Config {

	private static Logger logger = LoggerFactory.getLogger(AbstractSpider.class);
	private static final Map<Site, Map<String, String>> CONTEXT_MAP = new HashMap<>();
	private static final Map<Site, List<String>> FITER_MAP = new HashMap<>();
	static {init();	}
	
	private Config() {}

	@SuppressWarnings("unchecked")
	private static void init() {
		logger.info("------初始化读取配置-------");
		SAXReader read = new SAXReader();
		try {
			//得到classpath,不能用getFile打包后Spring无法使用resource.getFile()访问JAR中的路径的文件
			ClassPathResource pathResource = new ClassPathResource("rule.xml");
			//解析xml文档
			Document doc = read.read(pathResource.getInputStream());
			//得到文档根节点
			Element root = doc.getRootElement();
			
			List<Element> list = root.elements("site");
			for (Element e : list) {
				List<Element> subs = e.elements();
				Map<String, String> subMap = new HashMap<>();
				List<String> fiterList = new ArrayList<>();
				for (Element sub : subs) { 
					String name = sub.getName();
					String text = sub.getTextTrim();
					if(name=="selector-fiter") {
						//遍历selector-fiter节点
						List<Element> fiters = sub.elements();
						for (Element fiter : fiters) {
							String f = fiter.getTextTrim();
							fiterList.add(f);
						}
					}
					subMap.put(name, text);
				}
				FITER_MAP.put(Site.getEnumByUrl(subMap.get("url")), fiterList);
				CONTEXT_MAP.put(Site.getEnumByUrl(subMap.get("url")), subMap);
			}
			
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拿到对应网站的解析规则
	 */
	public static Map<String, String> getContext(Site site) {
		return CONTEXT_MAP.get(site);
	}
	public static List<String> getFiter(Site site) {
		return FITER_MAP.get(site);
	}
	
}
