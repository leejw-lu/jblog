package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public List<CategoryVo> getContentsList(String id) {
		return categoryRepository.findAll(id);
	}

	public void addContents(CategoryVo vo) {
		categoryRepository.insert(vo);
	}

	public List<CategoryVo> getContentsListWithPostCount(String id) {
		return categoryRepository.findAllWithPostCount(id);
	}

}
