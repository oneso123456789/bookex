package org.zerock.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadFileUtils {
	
	private static final Logger logger =
		LoggerFactory.getLogger(UploadFileUtils.class);
	
	public static String uploadFile(String uploadPath,
			String originalName, byte[] fileData)throws Exception{
		
		return null;
	}
	
	private static String calcPath(String uploadPath) {
		
		Calendar cal = Calendar.getInstance();
		
		String yearPath = File.separator+cal.get(Calendar.YEAR);
		
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
		
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		makeDir(uploadPath, yearPath, monthPath, datePath);
		
		logger.info(datePath);
		
		return datePath;
	}
	
	private static void makeDir(String uploadPath, String... paths) {
		  System.out.println(paths.length);
		if(new File(paths[paths.length-1]).exists()) {
			return;
		}
		
		for (String path : paths) {
			
			File dirPath = new File(uploadPath + path);
			
			if(! dirPath.exists()) {
				dirPath.mkdir();
			}
		}
	}
	
	private static String makeThumbnail(String uploadPath,String path,String fileName)throws Exception {
		
//		BufferedImage는 실제 이미지가 아닌 메모리상의 이미지를 의미하는 객체임 원본파일을 메모리상으로 로딩하고 정해진 크기에 맞게 작은 이미지 파일로 복사함	(61,63)	
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));
//		FIT_TO_HEIGHT를 이용하여 썸네일 이미지의 높이를 100px로 동일하게 만들어줌
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT,100);
		
//		thumbnail 파일에는 원본파일과 다르게 (66) 코드를 적용해서 "s_" 로 시작하게 만듬
		String thumbnailName = uploadPath + path + File.separator + "s_" + fileName;
		
		File newFile = new File(thumbnailName);
		
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
//		리턴해줄때 	File.separator(분리기호)를 /로 치환하는 이유는 브라우저에서 윈도우의 경로 \ 문자가 정상적인 경로로 인식이 불가능함 (/)로 꼭 치환
		return thumbnailName.substring(uploadPath.length()).replace(File.separator, "/");
	}
}
