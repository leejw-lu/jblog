package com.poscodx.jblog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.PostVo;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	
	public PostVo getContents(Long cNo, Long pNo) {
		//Post 제목, 내용 가져오기
		PostVo vo= postRepository.findByNo(pNo, cNo);
		return vo;
	}

	public List<PostVo> getContentsList(Long cNo) {
		return postRepository.findAll(cNo);
	}
	
	public Long getDefaultNo(Long cNo) {
		return postRepository.findByDefaultNo(cNo);
	}

	public void addContents(PostVo vo) {
		postRepository.insert(vo);
	}
	
}
