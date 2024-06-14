package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.jblog.security.Auth;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PostService postService;
	
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(
			@PathVariable("id") String id,
			@PathVariable("categoryNo") Optional<Long> categoryNo,	//optional 써서 기본값 세팅
			@PathVariable("postNo") Optional<Long> postNo,  Model model) {
	
		BlogVo blogvo= blogService.getContents(id);
		PostVo postVo= postService.getContents(id, categoryNo, postNo);
		List<CategoryVo> categoryList=categoryService.getContentsList(id);
		List<PostVo> postList=postService.getContentsList(id, categoryNo);
		
		model.addAttribute("id", id);
		model.addAttribute("blogVo", blogvo);
		model.addAttribute("postVo", postVo);
		model.addAttribute("clist", categoryList);
		model.addAttribute("plist", postList);
				
		return "blog/main";
	}
	
	@Auth
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String id) {
		
		return "/blog/admin-basic";
	}
	
	@Auth
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String id) {
		return "/blog/admin-category";
	}
	
	@Auth
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String id) {
		//if(!id.equals(authUser.getId()))
		return "/blog/admin-write";
	}
}
