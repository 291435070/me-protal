package com.protal.me.dao;

import com.protal.me.model.Blog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface BlogRepository extends ElasticsearchRepository<Blog, Long> {

}