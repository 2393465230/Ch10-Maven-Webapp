package com.yl.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yl.dao.SmbmsProviderDao;
import com.yl.entity.SmbmsProvider;
import com.yl.service.SmbmsProviderService;
@Service
public class SmbmsProviderServiceImpl implements SmbmsProviderService {
	@Autowired
	private SmbmsProviderDao dao;
	
	public Integer addSmbmsProvider(SmbmsProvider smbmsProvider) {
		// TODO Auto-generated method stub
		return dao.addSmbmsProvider(smbmsProvider);
	}

	public Integer updateSmbmsProvider(SmbmsProvider smbmsProvider) {
		// TODO Auto-generated method stub
		return dao.updateSmbmsProvider(smbmsProvider);
	}

	public Integer deleteSmbmsProvider(Integer id) {
		// TODO Auto-generated method stub
		return dao.deleteSmbmsProvider(id);
	}

	public SmbmsProvider getSmbmsProviderById(Integer id) {
		// TODO Auto-generated method stub
		return dao.getSmbmsProviderById(id);
	}

	public List<SmbmsProvider> getAllSmbmsProviders(
			@Param("proCode") String proCode, @Param("proName") String proName) {
		// TODO Auto-generated method stub
		return dao.getAllSmbmsProviders(proCode, proName);
	}

}
