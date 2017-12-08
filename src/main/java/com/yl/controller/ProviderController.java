package com.yl.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yl.entity.SmbmsProvider;
import com.yl.service.SmbmsProviderService;

@Controller
@RequestMapping("jsp")
public class ProviderController {
	@Autowired
	private SmbmsProviderService providerService;

	@RequestMapping("provider.do")
	public String getProviderList(String queryProCode, String queryProName,
			Map<String, Object> model) {
		List<SmbmsProvider> providers = providerService.getAllSmbmsProviders(
				queryProCode, queryProName);
		model.put("providerList", providers);
		model.put("queryProCode", queryProCode);
		model.put("queryProName", queryProName);
		return "jsp/providerlist";
	}

	@RequestMapping(value = "proById/{proid}", method = RequestMethod.GET)
	public String getProviderById(@PathVariable Integer proid,
			Map<String, Object> model) {
		SmbmsProvider provider = providerService.getSmbmsProviderById(proid);
		model.put("provider", provider);
		
		String str = provider.getPhoto();
		String[] strs=str.split(",");
		List<String> photos = new ArrayList<String>();
		for(int i=0,len=strs.length;i<len;i++){
			photos.add(strs[i].toString());
		}
		model.put("photos", photos);
		
		return "jsp/providerview";

	}

	@RequestMapping(value = "gotoUpdateProvider/{proid}", method = RequestMethod.GET)
	public String gotoUpdateProvider(@PathVariable Integer proid,
			Map<String, Object> model) {
		model.put("provider", providerService.getSmbmsProviderById(proid));
		return "jsp/providermodify";
	}

	@RequestMapping("updateProvider.do")
	public String updateProvider(SmbmsProvider provider,
			Map<String, Object> model) {
		if (providerService.updateSmbmsProvider(provider) > 0) {
			return "redirect:provider.do";
		}
		model.put("provider", provider);
		return "jsp/providermodify";

	}

	@RequestMapping("gotoAddProvider.do")
	public String gotoAddProvider(Map<String, Object> model) {
		model.put("provider", new SmbmsProvider());
		return "jsp/provideradd";
	}

	@RequestMapping("addAndUploadProvider.do")
	public String addAndUploadProvider(
			@ModelAttribute("provider") @Valid SmbmsProvider provider,
			BindingResult result,
			@RequestParam("photos") MultipartFile[] photos,
			HttpServletRequest req) {
		//文件是否为空
		if(!result.hasErrors()){
			// 创建文件夹
			String path = req.getSession().getServletContext().getRealPath("photos");
			// 错误信息数组
			List<String> errors = new ArrayList<String>();
			// 图片名数组
			List<String> fileNames = new ArrayList<String>();
			// 循环检测
			for (MultipartFile string : photos) {
				String err = getErrInfo(string);
				// 通过
				if (err == null) {
					// 上传
					String fileName = upload(string, path);
					// 添加到数组
					fileNames.add(fileName);
				}else{
					// 否则错误信息添加
					errors.add(err);
				}
			}
			// 至少有一条通过
			if(fileNames.size()>0){
				StringBuilder sb = new StringBuilder();
				sb.append("photos"+"/"+fileNames.get(0));
				sb.append(",photos"+"/"+fileNames.get(1));
				provider.setPhoto(sb.toString());
				// 添加成功
				if(providerService.addSmbmsProvider(provider) > 0){
					return "redirect:provider.do";
				}
			}
			
			req.setAttribute("errors", errors);
			req.setAttribute("filenames", fileNames);
		}
		
		return "jsp/provideradd";

	}
	
	/**
	 *  判断上传文件
	 * @param photo
	 * @return
	 */
	public String getErrInfo(MultipartFile photo){
		// 获取图片名字
		String fileName = photo.getOriginalFilename();
		// 图片格式
		String suf = FilenameUtils.getExtension(fileName);
		// 错误信息
		String err = null;
		// 判断
		if(photo.isEmpty()){
			err = String.format("%s：上传文件不能为空",fileName);
		}else if(photo.getSize()>50000000000L){
			err = String.format("%s：上传文件大小不能超过500KB",fileName);
		}else if(!formats.contains(suf.toLowerCase())){
			err = String.format("%s：上传文件格式不正确，支持的格式：%s",fileName,formats);
		}
		return err;
	}
	// 格式数组
	static List<String> formats = new ArrayList<String>();
	static{
		formats.add("png");
		formats.add("jpg");
		formats.add("jpeg");
		formats.add("pneg");
	}
	
	/**
	 * 上传
	 * @return
	 */
	public String upload(MultipartFile photo,String path){
		// 获取名字
		String fileName = photo.getOriginalFilename();
		// 获取当前时间
		long time = System.currentTimeMillis();
		String photoname = "_"+time+"_"+fileName;
		File dest = new File(path,photoname);
		try {
			photo.transferTo(dest);
			return photoname;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
