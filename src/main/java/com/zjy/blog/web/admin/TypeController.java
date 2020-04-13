package com.zjy.blog.web.admin;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.zjy.blog.entity.Type;
import com.zjy.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TypeController {
    @Autowired
    TypeService typeService;
    @GetMapping("/types")
    public String list(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable
                                , Model model){
        Page<Type> pages = typeService.listType(pageable);
        model.addAttribute("pages",pages);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String toInput(Model model){
        model.addAttribute("type",new Type());
        return "admin/type-input";
    }
    @PostMapping("/types")
    public String input(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type tt = typeService.findByName(type.getName());
        if(tt != null){
            result.rejectValue("name","NameError","类别已存在！");
        }
        if(result.hasErrors()){
            return "admin/type-input";
        }
        Type t = null;
        if (type.getId() == null){
            t = typeService.saveType(type);
        }else{
            t = typeService.updateType(type.getId(),type);
        }
        if(t == null){
            attributes.addFlashAttribute("msg","操作失败！");
        }else{

            attributes.addFlashAttribute("msg","操作成功！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/input/{id}")
    public String edit(@PathVariable("id") Long id,Model model){
        Type type = typeService.getType(id);
        model.addAttribute("type",type);
        return "admin/type-input";
    }

    @GetMapping("/types/delete/{id}")
    public String delete(@PathVariable("id") Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("msg","操作成功！");
        return "redirect:/admin/types";
    }
}
