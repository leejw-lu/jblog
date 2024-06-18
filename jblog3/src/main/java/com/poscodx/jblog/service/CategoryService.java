package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private PostRepository postRepository;

	public List<CategoryVo> getContentsList(String id) {
		return categoryRepository.findAll(id);
	}

	public void addContents(CategoryVo vo) {
		categoryRepository.insert(vo);
	}

	public List<CategoryVo> getContentsListWithPostCount(String id) {
		return categoryRepository.findAllWithPostCount(id);
	}

	@Transactional
	public void deleteContents(String id, Long no) {
		postRepository.deleteByCategoryNo(no);
		categoryRepository.deleteByNo(id, no);
	}
}
