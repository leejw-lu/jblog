package com.poscodx.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Repository
public class PostRepository {

private SqlSession sqlSession;
	
	public PostRepository(SqlSession sqlSession) {
		this.sqlSession=sqlSession;
	}
	
	public PostVo findByNo(String id, Long pNo) {
		return sqlSession.selectOne("post.findByNo", pNo);
		//return sqlSession.selectOne("post.findByNo", Map.of("id", id, "pNo", pNo));
	}

	public Long findByDefaultNo(Long cNo) {
		return sqlSession.selectOne("post.findByDefaultNo", cNo);
	}

	public List<PostVo> findAll(String id, Long cNo) {
		return sqlSession.selectList("post.findAll", cNo);
		//return sqlSession.selectList("post.findAll", Map.of("id", id, "cNo", cNo));
	}
	
	public void insert(PostVo vo) {
		sqlSession.insert("post.insert", vo);
	}

	public void deleteByCategoryNo(Long no) {
		sqlSession.insert("post.deleteByCategoryNo", no);
	}
}
