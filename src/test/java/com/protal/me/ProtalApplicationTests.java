package com.protal.me;

import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringBootTest
public class ProtalApplicationTests {

	private static final Logger LOG = LoggerFactory.getLogger(ProtalApplicationTests.class);

	@Test
	public void gsonTest() {
		System.out.println(LocalDateTime.now());
		Gson gson = new Gson();
		String json = gson.toJson(new Protal(1001L, "zy", "google转换json包", new Date()));
		LOG.info(json);

		Protal protal = gson.fromJson(json, Protal.class);
		LOG.info(protal.toString());

		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson1 = builder.create();
		System.out.println(gson1.toJson(protal));
	}

	@Test
	public void jsoupTest() throws Exception {
		for (int i = 1; i <= 5; i++) {
			LOG.info("第------{}------页", i);
			Connection connect = Jsoup.connect("https://www.jqhtml.com/category/java/page/" + i);
			// connect.cookie("JSESSIONID", "FBA7E4C28AD2F500DAE79A3D0BFDFA46");
			Document doc = connect.get();
			Element list = doc.getElementsByClass("content-list").first();
			Elements item = list.getElementsByTag("li");
			for (Element ele : item) {
				Element e = ele.getElementsByTag("a").first();
				LOG.info("{}->{}", e.attr("href"), e.html());
			}
			Thread.sleep(500);
		}
	}

}

class Protal {

	private Long id;
	private String platform;
	private String content;
	private Date date;

	public Protal() {
	}

	public Protal(Long id, String platform, String content, Date date) {
		this.id = id;
		this.platform = platform;
		this.content = content;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}