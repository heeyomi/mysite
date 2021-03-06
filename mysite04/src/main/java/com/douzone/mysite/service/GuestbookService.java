package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	GuestbookRepository guestbookRepository;

	public List<GuestbookVo> getMessageList() {
		List<GuestbookVo> list = guestbookRepository.find();
		for (GuestbookVo guestbookVo : list) {
			guestbookVo.setRegDate(guestbookVo.getRegDate().substring(0, 19));
		}
		return list;
	}
	
	public void addMessage(GuestbookVo vo) {
		guestbookRepository.insert(vo);
	}

	public void deleteMessage(Long no, String password) {
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		guestbookRepository.delete(vo);
	}
	
}
