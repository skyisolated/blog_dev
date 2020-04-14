package com.zjy.blog.web.admin;

import com.zjy.blog.dao.BlogRepository;
import com.zjy.blog.entity.*;
import com.zjy.blog.service.BlogService;
import com.zjy.blog.service.TagService;
import com.zjy.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    BlogService blogService;

    @Autowired
    TypeService typeService;

    @Autowired
    TagService tagService;

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

    @GetMapping("/blogs/input")
    public String toInputPage(Model model){
        List<Type> types = typeService.listType();
        List<Tag> tags = tagService.listTag();
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        model.addAttribute("blog",new Blog());
        return "admin/blog-input";
    }
    @GetMapping("/blogs/input/{id}")
    public String toEditPage(@PathVariable("id") Long id, Model model){
        List<Type> types = typeService.listType();
        List<Tag> tags = tagService.listTag();
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        model.addAttribute("blog",blogService.getBlog(id));
        return "admin/blog-input";
    }

    @PostMapping("/blogs")
    public String add(Blog blog, RedirectAttributes attributes, HttpSession session){
        Blog blog1 = null;
        if(blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setUser((User) session.getAttribute("user"));
            blog.setType(typeService.getType(blog.getType().getId()));
            blog.setViews(0);
            blog1 = blogService.saveBlog(blog);
        }else{
            blog.setUpdateTime(new Date());
            blog1=blogService.updateBlog(blog.getId(),blog);
        }
        if (blog1 != null){
            attributes.addFlashAttribute("msg","操作成功！");
        }else{
            attributes.addFlashAttribute("msg","操作失败！");
        }
        return "redirect:/admin/blogs";
    }
    @GetMapping("/blogs/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        blogService.deleteBlog(id);
        return "redirect:/admin/blogs";
    }
}
