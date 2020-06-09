package com.protal.me.service;

import com.protal.me.dao.BaseInterface;
import com.protal.me.model.Blog;

public interface BlogService extends BaseInterface<Blog> {

    Blog prev(long id);

    Blog next(long id);

}