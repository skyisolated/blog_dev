package com.zjy.blog.web.admin;

import com.zjy.blog.entity.Tag;
import com.zjy.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController{

    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable
                            , Model model){
        Page<Tag> tags = tagService.listTag(pageable);
        model.addAttribute("pages",tags);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String toInput(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tag-input";
    }

    @PostMapping("/tags")
    public String input(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tt = tagService.findByName(tag.getName());
        if(tt != null){
            result.rejectValue("name","NameError","标签已存在！");
        }
        if(result.hasErrors()){
            return "admin/tag-input";
        }
        Tag t = null;
        if(tag.getId() == null){
            t = tagService.saveTag(tag);
        }else{
            t = tagService.updateTag(tag.getId(),tag);
        }
        if(t == null){
            attributes.addFlashAttribute("msg","操作失败！");
        }else{

            attributes.addFlashAttribute("msg","操作成功！");
        }
        return "redirect:/admin/tags";
    }
    @GetMapping("/tags/input/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        Tag tag = tagService.getTag(id);
        model.addAttribute("tag",tag);
        return "admin/tag-input";
    }

    @GetMapping("/tags/delete/{id}")
    public String delete(@PathVariable("id") Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("msg","操作成功！");
        return "redirect:/admin/tags";
    }
}
