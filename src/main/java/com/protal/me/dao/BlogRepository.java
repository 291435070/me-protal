package com.protal.me.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import com.protal.me.model.Blog;

@Component
public interface BlogRepository extends ElasticsearchRepository<Blog, Long> {

}