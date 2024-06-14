package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.security.Auth;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.FileUploadService;
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
	
	@Autowired
	private FileUploadService fileuploadService;
	
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
	@GetMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String id, Model model) {
		BlogVo vo= blogService.getContents(id);
		model.addAttribute("vo", vo);
		model.addAttribute("id", id);
		
		return "/blog/admin-basic";
	}
	
	@Auth
	@PostMapping("/admin/basic")
	public String adminPost (@PathVariable("id") String id, BlogVo blogVo, @RequestParam(value="file") MultipartFile file) {
		String logo= fileuploadService.restore(file);	//url
		if (logo !=null) {
			blogVo.setLogo(logo);
		}
		
		System.out.println("blogVo: " + blogVo);
		blogVo.setId(id);
		blogService.updateBlog(blogVo);
		
		return "redirect:/"+id+"/admin/basic";
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
