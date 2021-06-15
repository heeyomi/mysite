package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.SiteRepository;
import com.douzone.mysite.vo.SiteVo;

@Service
public class StieService {

	@Autowired
	private SiteRepository siteRepository;
	
	public int updateMain(SiteVo vo) {
		return siteRepository.update(vo);
	}

	public SiteVo find() {
		return siteRepository.find();
	}
}
