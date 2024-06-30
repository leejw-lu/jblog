package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

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
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import com.poscodx.jblog.vo.UserVo;

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
	
	@Autowired
	private UserService userService;
	
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(
			@PathVariable("id") String id,
			@PathVariable("categoryNo") Optional<Long> categoryNo,	//optional 써서 기본값 세팅
			@PathVariable("postNo") Optional<Long> postNo,  Model model) {
		
		/* 존재하지 않은 id 요청 확인 */
		if (userService.getUser(id) == null) {
			System.out.println("존재하지 않은 id");
			return "errors/nouser";
		}
		
		/* category, post No값 Optional 확인 후 empty일 경우 deafult no값 세팅하기 */
		Long cNo=0L;
		Long pNo=0L;
		
		if (categoryNo.isEmpty()) {
			cNo=categoryService.getDefaultNo(id);
		} else {
			cNo=categoryNo.get();
		}
		
		if (postNo.isEmpty()) {
			pNo=postService.getDefaultNo(cNo);
			if (pNo==null) pNo=0L;
		} else {
			pNo=postNo.get();
		}

		BlogVo blogvo= blogService.getContents(id);
		PostVo postVo= postService.getContents(cNo, pNo);
		List<CategoryVo> categoryList=categoryService.getContentsList(id);
		List<PostVo> postList=postService.getContentsList(cNo);
		
		model.addAttribute("id", id);
		model.addAttribute("blogVo", blogvo);
		model.addAttribute("postVo", postVo);
		model.addAttribute("clist", categoryList);
		model.addAttribute("plist", postList);
				
		return "blog/main";
	}
	
	@Auth
	@GetMapping("/admin/basic")
	public String adminBasic(HttpSession session, @PathVariable("id") String id, Model model) {
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		if(!id.equals(authUser.getId())) {
			return "redirect:/"+id;
		}
		BlogVo vo= blogService.getContents(id);
		model.addAttribute("vo", vo);
		
		return "/blog/admin-basic";
		
	}
	
	@Auth
	@PostMapping("/admin/basic")
	public String adminPost(@PathVariable("id") String id, BlogVo blogVo, @RequestParam(value="file") MultipartFile file) {
		String logo= fileuploadService.restore(file);	//url
		if (logo !=null) {
			blogVo.setLogo(logo);
		}
		blogService.updateBlog(blogVo);
		
		return "redirect:/"+id+"/admin/basic";
	}
	
	@Auth
	@GetMapping("/admin/category")
	public String adminCategory(HttpSession session, @PathVariable("id") String id, Model model) {
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		if(!id.equals(authUser.getId())) {
			return "redirect:/"+id;
		}
		
		BlogVo blogvo= blogService.getContents(id);
		List<CategoryVo> categoryWithPostCountList=categoryService.getContentsListWithPostCount(id);
		
		model.addAttribute("blogVo", blogvo);
		model.addAttribute("cwithplist", categoryWithPostCountList);
		
		return "/blog/admin-category";
	}
	
	@Auth
	@PostMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String id, CategoryVo categoryVo) {
		categoryVo.setId(id);
		categoryService.addContents(categoryVo);
		
		return "redirect:/"+id+"/admin/category";
	}
	
	@Auth
	@GetMapping("/admin/write")
	public String adminWrite(HttpSession session, @PathVariable("id") String id, Model model) {
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		if(!id.equals(authUser.getId())) {
			return "redirect:/"+id;
		}
		
		BlogVo blogvo= blogService.getContents(id);
		List<CategoryVo> list= categoryService.getContentsList(id);
		
		model.addAttribute("blogVo", blogvo);
		model.addAttribute("clist", list);

		return "/blog/admin-write";
	}
	
	@Auth
	@PostMapping("/admin/write")
	public String adminWrite(@PathVariable("id") String id, PostVo postVo) {
		postService.addContents(postVo);
		
		return "redirect:/"+id;
	}
	
	@Auth
	@RequestMapping("/admin/category/delete/{no}")
	public String adminWrite(HttpSession session, @PathVariable("id") String id, @PathVariable("no") Long no) {
		UserVo authUser= (UserVo) session.getAttribute("authUser");
		if(!id.equals(authUser.getId())) {
			return "redirect:/"+id;
		}
		categoryService.deleteContents(id, no);
		
		return "redirect:/"+id+"/admin/category";
	}
}
