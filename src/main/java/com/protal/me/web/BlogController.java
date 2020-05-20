package com.protal.me.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.util.CollectionUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.Field;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.CharMatcher;
import com.protal.me.dao.BlogRepository;
import com.protal.me.model.Blog;

/**
 * <pre>1.ES下载，https://www.elastic.co/cn/downloads/elasticsearch，选择对应版本；</pre>
 * <pre>2.分词器下载，https://github.com/medcl/elasticsearch-analysis-ik/releases，版本与ES一致；</pre>
 * <pre>3.分词器解压后，复制到ES安装目录plugins下；</pre>
 * <pre>4.ES可视化界面下载，https://github.com/360EntSecGroup-Skylar/ElasticHD/releases；Windows版，cmd命令启动ElasticHD -p 127.0.0.1:9800；</pre>
 * <pre>5.引入依赖spring-boot-starter-data-elasticsearch，配置yml文件，代码实现；</pre>
 */
@RestController
@RequestMapping("blog")
public class BlogController {

	private static final Logger LOG = LoggerFactory.getLogger(BlogController.class);

	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@PostMapping("insert")
	public Object insert(@RequestBody Blog blog) {
		blog.setDate(LocalDateTime.now().toString());
		LOG.info(blog.toString());
		return blogRepository.save(blog);
	}

	@PostMapping("delete")
	public Object delete(@RequestBody Blog blog) {
		LOG.info(blog.toString());
		blogRepository.delete(blog);
		return blog;
	}

	@PostMapping("update")
	public Object update(@RequestBody Blog blog) {
		LOG.info(blog.toString());
		Assert.notNull(blog.getId(), "Cannot update 'null' id.");
		return blogRepository.save(blog);
	}

	@GetMapping("select")
	public Object select(@RequestBody Blog blog) {
		LOG.info(blog.toString());
		Assert.notNull(blog.getId(), "Cannot select 'null' id.");
		return blogRepository.findById(blog.getId()).get();
	}

	@GetMapping("get/all")
	public Object getAll() {
		// return blogRepository.search(QueryBuilders.matchAllQuery());
		return blogRepository.findAll();
	}

	@SuppressWarnings("deprecation")
	@PostMapping("save/batch")
	public Object saveBatch() throws Exception {
		Blog blog = null;
		for (int i = 1; i <= 650; i++) {
			LOG.info("第------{}------页", i);
			Document doc = Jsoup.connect("https://www.jqhtml.com/category/java/page/" + i).get();
			Element list = doc.getElementsByClass("content-list").first();
			Elements item = list.getElementsByTag("li");
			for (Element ele : item) {
				Element e = ele.getElementsByTag("a").first();
				LOG.info("{}->{}", e.attr("href"), e.html());
				blog = new Blog();
				blog.setId(Long.parseLong(CharMatcher.javaDigit().retainFrom(e.attr("href"))));
				blog.setTitle(e.html());
				blog.setContent(e.attr("href"));
				blog.setDate(LocalDateTime.now().toString());
				blogRepository.save(blog);
			}
		}
		return blogRepository.count();
	}

	// 精确查询，指定字段
	@GetMapping("query/term")
	public Object queryTerm(@RequestParam String title, @PageableDefault Pageable pageable) {
		LOG.info(title);

		NativeSearchQuery query = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.termQuery("title.keyword", title)).withPageable(pageable)
				.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC)).build();
		List<Blog> list = elasticsearchTemplate.queryForList(query, Blog.class);

		return list;
	}

	// 分词查询，指定字段
	@GetMapping("query/match")
	public Object queryMatch(@RequestParam String title, @PageableDefault Pageable pageable) {
		LOG.info(title);

		NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("title", title))
				.withPageable(pageable).withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC)).build();
		List<Blog> list = elasticsearchTemplate.queryForList(query, Blog.class);

		return list;
	}

	// 分词查询，不指定字段，即所有字段
	@GetMapping("query/string")
	public Object queryString(@RequestParam String title, @PageableDefault Pageable pageable) {
		LOG.info(title);

		NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.queryStringQuery(title))
				.withPageable(pageable).withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC)).build();
		List<Blog> list = elasticsearchTemplate.queryForList(query, Blog.class);

		return list;
	}

	// 分词查询，指定字段、高亮显示
	@GetMapping("query/highlight")
	public Object queryHighlight(@RequestParam String title, @PageableDefault Pageable pageable) {
		LOG.info(title);
		// 高亮设置
		Field highlight = new HighlightBuilder.Field("title").preTags("<pre style='color:red'>").postTags("</pre>");

		NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("title", title))
				.withPageable(pageable).withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
				.withHighlightFields(highlight).build();

		AggregatedPage<Blog> aggregatedPage = elasticsearchTemplate.queryForPage(query, Blog.class,
				new SearchResultMapper() {
					@Override
					public <T> T mapSearchHit(SearchHit searchHit, Class<T> type) {
						return null;
					}

					@SuppressWarnings("unchecked")
					@Override
					public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
							Pageable pageable) {
						if (CollectionUtils.isEmpty(response.getHits().getHits())) {
							return null;
						}
						List<Blog> blogs = new ArrayList<>();
						Blog blog = null;
						for (SearchHit hit : response.getHits()) {
							blog = new Blog();
							blog.setId(Long.parseLong(hit.getSourceAsMap().get("id").toString()));
							blog.setContent(hit.getSourceAsMap().get("content").toString());
							blog.setDate(hit.getSourceAsMap().get("date").toString());
							HighlightField title = hit.getHighlightFields().get("title");
							if (null != title) {
								blog.setTitle(title.fragments()[0].toString());
							}
							blogs.add(blog);
						}

						if (blogs.size() > 0) {
							return new AggregatedPageImpl<>((List<T>) blogs);
						}

						return null;
					}
				});

		return aggregatedPage.getContent();
	}

}