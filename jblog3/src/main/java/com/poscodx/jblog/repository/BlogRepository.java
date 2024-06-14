package com.poscodx.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.BlogVo;

@Repository
public class BlogRepository {
	private SqlSession sqlSession;
	
	public BlogRepository(SqlSession sqlSession) {
		this.sqlSession=sqlSession;
	}
	
	public int insert(BlogVo vo) {
		return sqlSession.insert("blog.insert", vo);
	}

	public BlogVo findById(String id) {
		return sqlSession.selectOne("blog.findById", id);
	}
}
