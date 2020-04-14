package com.zjy.blog.service;

import com.zjy.blog.dao.BlogRepository;
import com.zjy.blog.entity.Blog;
import com.zjy.blog.entity.BlogQuery;
import com.zjy.blog.entity.Type;
import com.zjy.blog.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogRepository blogRepository;
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }

    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Optional<Blog> blog1 = blogRepository.findById(id);
        Blog res = blog1.get();
        if(res == null){
            throw new NotFoundException("博客不存在");
        }
        Blog save = blogRepository.save(blog);
        return save;
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }


}
