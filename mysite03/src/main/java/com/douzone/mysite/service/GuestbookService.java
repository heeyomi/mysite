package com.douzone.mysite.service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
		return list;
	}
	
	public void addMessage(GuestbookVo vo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vo.setRegDate(sdf.format(new Date()));
		guestbookRepository.insert(vo);
	}

	public void deleteMessage(Long no, String password) {
		GuestbookVo vo = guestbookRepository.findByNo(no);
		if (password.equals(vo.getPassword())) {
			guestbookRepository.delete(vo);
		}
	}
	
}
