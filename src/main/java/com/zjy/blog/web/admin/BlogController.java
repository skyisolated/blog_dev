package com.zjy.blog.web.admin;

import com.zjy.blog.dao.BlogRepository;
import com.zjy.blog.entity.Blog;
import com.zjy.blog.entity.BlogQuery;
import com.zjy.blog.entity.Type;
import com.zjy.blog.service.BlogService;
import com.zjy.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    BlogService blogService;

    @Autowired
    TypeService typeService;

    @GetMapping("/blogs")
    public String list(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        Page<Blog> blogs = blogService.listBlog(pageable, blog);
        List<Type> types = typeService.listType();
        model.addAttribute("blogs",blogs);
        model.addAttribute("types",types);
        return "admin/blogs";
    }
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        Page<Blog> blogs = blogService.listBlog(pageable, blog);
        model.addAttribute("blogs",blogs);
        return "admin/blogs::bloglist"; //返回一个片段
    }
}
