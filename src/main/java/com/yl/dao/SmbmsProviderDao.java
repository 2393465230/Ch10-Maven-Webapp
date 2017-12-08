package com.yl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yl.entity.SmbmsProvider;

public interface SmbmsProviderDao {
	/**
	 * ���
	 * @param smbmsProvider
	 * @return
	 */
	Integer addSmbmsProvider(SmbmsProvider smbmsProvider);
	
	/**
	 * ����
	 * @param smbmsProvider
	 * @return
	 */
	Integer updateSmbmsProvider(SmbmsProvider smbmsProvider);
	/**
	 * ɾ��
	 * @param id
	 * @return
	 */
	Integer deleteSmbmsProvider(Integer id);
	/**
	 * �鿴
	 * @param id
	 * @return
	 */
	SmbmsProvider getSmbmsProviderById(Integer id);
	/**
	 * ȫ����Ӧ��
	 * @param proCode
	 * @param proName
	 * @return
	 */
	List<SmbmsProvider> getAllSmbmsProviders(@Param("proCode") String proCode,
			@Param("proName") String proName);
}
