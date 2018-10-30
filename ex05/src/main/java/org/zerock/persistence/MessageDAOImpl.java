package org.zerock.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.zerock.domain.MessageVO;

@Repository
public class MessageDAOImpl implements MessageDAO{
	
	@Inject
	private SqlSession session;
	
	private static String Namespace = "org.zerock.mapper.MessageMapper";
	

	@Override
	public void create(MessageVO vo) throws Exception {
		
		session.insert(Namespace+".create", vo);
	}

	@Override
	public MessageVO readMessage(Integer mid) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(Namespace+".readMessage", mid);
	}

	@Override
	public void updateState(Integer mid) throws Exception {
		
		session.update(Namespace+".readMessage", mid);
	}
	

}
