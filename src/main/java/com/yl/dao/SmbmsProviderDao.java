package com.yl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yl.entity.SmbmsProvider;

public interface SmbmsProviderDao {
	/**
	 * 添加
	 * @param smbmsProvider
	 * @return
	 */
	Integer addSmbmsProvider(SmbmsProvider smbmsProvider);
	
	/**
	 * 更新
	 * @param smbmsProvider
	 * @return
	 */
	Integer updateSmbmsProvider(SmbmsProvider smbmsProvider);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	Integer deleteSmbmsProvider(Integer id);
	/**
	 * 查看
	 * @param id
	 * @return
	 */
	SmbmsProvider getSmbmsProviderById(Integer id);
	/**
	 * 全部供应商
	 * @param proCode
	 * @param proName
	 * @return
	 */
	List<SmbmsProvider> getAllSmbmsProviders(@Param("proCode") String proCode,
			@Param("proName") String proName);
}
