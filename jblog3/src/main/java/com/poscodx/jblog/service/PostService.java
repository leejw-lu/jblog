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
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	public PostVo getContents(String id, Optional<Long> categoryNo, Optional<Long> postNo) {
		Long cNo=null;
		Long pNo=null;

		/* category, post No값 Optional 확인 후 empty일 경우 deafult no값 세팅하기 */
		if (categoryNo.isEmpty()) {
			cNo=categoryRepository.findByDefaultNo(id);
		} else {
			cNo=categoryNo.get();
		}
		
		if (postNo.isEmpty()) {
			pNo=postRepository.findByDefaultNo(cNo);
		} else {
			pNo=postNo.get();
		}
		
		//Post 제목, 내용 가져오기
		PostVo vo= postRepository.findByNo(id, pNo);
		return vo;
	}

	public List<PostVo> getContentsList(String id, Optional<Long> categoryNo) {
		Long cNo=null;
		if (categoryNo.isEmpty()) {
			cNo=categoryRepository.findByDefaultNo(id);
		} else {
			cNo=categoryNo.get();
		}
		
		return postRepository.findAll(id, cNo);
	}
	
}
