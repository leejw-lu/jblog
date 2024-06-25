package com.poscodx.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.PostVo;

@Repository
public class PostRepository {

private SqlSession sqlSession;
	
	public PostRepository(SqlSession sqlSession) {
		this.sqlSession=sqlSession;
	}
	
	public PostVo findByNo(Long pNo, Long cNo) {
		return sqlSession.selectOne("post.findByNo",  Map.of("pNo", pNo, "cNo", cNo));
	}

	public Long findByDefaultNo(Long cNo) {
		return sqlSession.selectOne("post.findByDefaultNo", cNo);
	}

	public List<PostVo> findAll(Long cNo) {
		return sqlSession.selectList("post.findAll", cNo);
	}
	
	public void insert(PostVo vo) {
		sqlSession.insert("post.insert", vo);
	}

	public void deleteByCategoryNo(Long no) {
		sqlSession.insert("post.deleteByCategoryNo", no);
	}
}
