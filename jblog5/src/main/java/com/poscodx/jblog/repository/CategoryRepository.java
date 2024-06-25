package com.poscodx.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	private SqlSession sqlSession;
	
	public CategoryRepository(SqlSession sqlSession) {
		this.sqlSession=sqlSession;
	}
	
	public int insert(CategoryVo vo) {
		return sqlSession.insert("category.insert", vo);
	}

	public List<CategoryVo> findAll(String id) {
		return sqlSession.selectList("category.findAll", id);
	}

	public Long findByDefaultNo(String id) {
		return sqlSession.selectOne("category.findByDefaultNo", id);
	}

	public List<CategoryVo> findAllWithPostCount(String id) {
		return sqlSession.selectList("category.findAllWithPostCount", id);
	}

	public void deleteByNo(String id, Long no) {
		sqlSession.delete("category.deleteByNo", Map.of("id", id , "no", no));
		
	}
}
