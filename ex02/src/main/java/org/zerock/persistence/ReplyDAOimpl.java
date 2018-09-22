package org.zerock.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.zerock.domain.ReplyVO;

@Repository
public class ReplyDAOimpl implements ReplyDAO{
	
	
	@Inject
	private SqlSession session;
	
	private static String namespace = "org.zerock.mapper.ReplyMapper";
	
	@Override
	public List<ReplyVO> list(Integer bno) throws Exception {
		
		// TODO Auto-generated method stub
		return session.selectList(namespace + ".list", bno);
	}

	@Override
	public void create(ReplyVO vo) throws Exception {
		
		session.insert(namespace + ".create", vo);
		
	}

	@Override
	public void update(ReplyVO vo) throws Exception {
		
		session.update(namespace + ".update", vo);
		
	}

	@Override
	public void delete(Integer rno) throws Exception {
		// TODO Auto-generated method stub
		
		session.delete(namespace + ".delete", rno);
	}
	
	

}
