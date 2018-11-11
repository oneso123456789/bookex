package org.zerock.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.SearchCriteria;
import org.zerock.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {

  @Inject
  private BoardDAO dao;

 @Transactional
  @Override
  public void regist(BoardVO board) throws Exception {
    dao.create(board);
    
    String[] files = board.getFiles();
    
    if(files == null ) {
    	return;
    }
    
    for(String fileName : files) {
    	dao.addAttach(fileName);
    }
  }
  
  //Transaction으로 격리성 부여 격리성은 데이터베이스 기본 사용수준 다른연결이 커밋하지 않은 데이터는 볼수없음
  @Transactional(isolation=Isolation.READ_COMMITTED)
  @Override
  public BoardVO read(Integer bno) throws Exception {
	  dao.updateViewCnt(bno); //먼저 ViewCnt를 업데이트 한 후 읽어온다.
	  return dao.read(bno);
  }

  @Override
  public void modify(BoardVO board) throws Exception {
    dao.update(board);
  }

  @Override
  public void remove(Integer bno) throws Exception {
    dao.delete(bno);
  }

  @Override
  public List<BoardVO> listAll() throws Exception {
    return dao.listAll();
  }

  @Override
  public List<BoardVO> listCriteria(Criteria cri) throws Exception {

    return dao.listCriteria(cri);
  }

  @Override
  public int listCountCriteria(Criteria cri) throws Exception {

    return dao.countPaging(cri);
  }

  @Override
  public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception {

    return dao.listSearch(cri);
  }

  @Override
  public int listSearchCount(SearchCriteria cri) throws Exception {

    return dao.listSearchCount(cri);
  }

@Override
public List<String> getAttach(Integer bno) throws Exception {
	// TODO Auto-generated method stub
	return dao.getAttach(bno);
}

  
}
