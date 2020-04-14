package com.zjy.blog.service;

import com.zjy.blog.entity.Blog;
import com.zjy.blog.entity.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    Blog getBlog(Long id);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);
//这里的blog是查询条件的封装
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
}
