package org.zerock.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

// UploadFileUtils는 static으로 구성된 클래스 메소드들만을 가지기 때문에 그 자체로 테스트 가능함
public class UploadFileUtils {
	
	private static final Logger logger =
		LoggerFactory.getLogger(UploadFileUtils.class);
	
	public static String uploadFile(String uploadPath,
			String originalName, byte[] fileData)throws Exception{
		
		UUID uid = UUID.randomUUID();
		
		String savedName = uid.toString() + "_" + originalName;
//		저장될 경로를 계산함(code 28)		
		String savedPath = calcPath(uploadPath);
		
		File target = new File(uploadPath + savedPath, savedName);
//		원본 파일을 저장하는 부분(code 32)		
		FileCopyUtils.copy(fileData, target);
//		원본 파일의 확장자를 의미함(code 34) getMediaType() 메소드를 이용해서 이미지 파일인 경우와 그렇지 않은 경루를 나누어 처리함(code 38~42) 		
		String formatName = originalName.substring(originalName.lastIndexOf(".")+1);
		
		String uploadedFileName = null;
//		이미지 타입일 경우에는 Thumbnail을 생성하고 그렇지 않을 경우에는 makeIcon을 통해서 결과를 만들어냄		
		if(MediaUtils.getMediaType(formatName) != null) {
			uploadedFileName = makeThumbnail(uploadPath, savedPath, savedName);
		}else {
			uploadedFileName = makeIcon(uploadPath, savedPath, savedName);
		}
		
		return uploadedFileName;
	}
//	makeIcon은 경로 처리를 하는 문자열의 치환용도에 불가함	
	private static String makeIcon(String uploadPath, String path, String fileName)throws Exception{
		
		String iconName = uploadPath + path + File.separator + fileName;
		
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
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
