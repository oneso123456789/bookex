package org.zerock.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.util.MediaUtils;
import org.zerock.util.UploadFileUtils;
/*
 파일 업로드에서 주의해야할점은 동일한 경로에 동일한 이름의 파일을 업로드 하는것임
 java.util.UUID; 을 사용해서 거의 고유한 겂을 생성해서 함께 처리하면 이 문제 해결 가능
  또한 servlet-context.xml에 설정한 	
    <beans:bean id="uploadPath" class="java.lang.String">
		<beans:constructor-arg value="C:\\zzz\\upload"></beans:constructor-arg>
	</beans:bean>
	
	의 경로에 폴더를 만들어 줘야했음
	
 */
@Controller
public class UploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Resource(name = "uploadPath")
	private String uploadPath;
	
	@RequestMapping(value = "/uploadForm", method = RequestMethod.GET)
	public void uploadForm() {
		
	}
	
	@RequestMapping(value = "/uploadForm", method = RequestMethod.POST)
	public String uploadForm(MultipartFile file, Model model)throws Exception {
		
		logger.info("originalName: " + file.getOriginalFilename());
		logger.info("size: " + file.getSize());
		logger.info("contentType: " + file.getContentType());
		
		String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
		
		model.addAttribute("savedName", savedName);
		
//     UUID로 	6574ace7-a724-471c-945b-a39e21501cb6_ 같은 문자열을 삽입하기때문에 이름이 너무 길면 alert(msg)에 안나올때가 있음	
		return "uploadResult";
		
	}
	
	private String uploadFile(String originalName, byte[] fileData) throws Exception{
		
		UUID uid = UUID.randomUUID();
		
		String savedName = uid.toString() + "_" + originalName;
		
		File target = new File(uploadPath, savedName);
		
		FileCopyUtils.copy(fileData, target);
		
		return savedName;
	}
	
// 최종적인 리턴 값은 내부적으로 UploadFileUtils의 uploadFile()을 사용하도록 수정했음	
	@RequestMapping(value = "uploadAjax", method = RequestMethod.GET)
	public void uploadAjax() {
		
	}
	
// Mapping 에서 오타 안나게 조심할것 "text/plain;chaarset=UTF-8" 이라고 해서 한글 깨졌음	
	@ResponseBody
	@RequestMapping(value = "/uploadAjax",
					method = RequestMethod.POST,
					produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception{
		
		logger.info("originalName: " + file.getOriginalFilename());

		
		return
			new ResponseEntity<>(
				UploadFileUtils.uploadFile(uploadPath,
						file.getOriginalFilename(),
						file.getBytes()),
				HttpStatus.CREATED);
	}

//	displayFile()은 파라미터로 브라우저에서 전송받기를 원하는 파일의 이름을 받음
//	파일 이름은 '/년/월/일/파일명' 형태로 입력받음	displayFile의 리턴 타입은 ResponseEntity<byte[]>로 작성
//	결과는 실제로 파일의 데이터가 됨 메소드 선언부의 @ResponseBody를 이용해서 byte[] 데이터가 그래도 전송된다는걸 명시
//	동작 방식은 브라우저에서 'displayFile?fileName=/년/월/일/파일명'을 호출해서 확인	
//	TODO	
	@ResponseBody
	@RequestMapping("/displayFile")	
	public ResponseEntity<byte[]> displayFile(String fileName)throws Exception{
		
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		
		logger.info("FILE NAME : " + fileName);
		
		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
//			code 117로  작업하는 파일 이름에서 확장자를 추출함	 이미지 타입의 경우는 적절한 MIME 타입을지정함
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			HttpHeaders headers = new HttpHeaders();
			
			in = new FileInputStream(uploadPath + fileName);
			
			if(mType != null) {
				headers.setContentType(mType);
			}else {
				
				fileName = fileName.substring(fileName.lastIndexOf("_")+1);
//			이미지가 아닌 경우는 MIME 타입을 다운로드 용으로 샤용되는 'application/octet-stream'으로 지정
//			 브라우저는 이 MIME 타입을 보고 사용자에게 자동으로 다운로드 창을 열어줌	MIME= MultiPurpose Internet Mail Extensions 다목적 인터넷 메일 확장			
//			code 134를 통해서 다운로드 할때 사용자에게 보이는 파일의 이름을 한글처리해서 전송 한글파일은 반드시 인코딩 처리해야함(그냥 다운로드시 깨짐)			
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\""+
				new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\"");
			}
//			실제로 데이터를 읽는 부분은 commons 라이브러리 기능을 활용해서 대상 파일에서 데이터를 읽어내는 IOUtils.toByteArray() 부분임			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),
					headers,
					HttpStatus.CREATED);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}finally {
			in.close();
		}
		
		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteFile", method=RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName){
		
		logger.info("delete file: " + fileName);
		
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		MediaType mType = MediaUtils.getMediaType(formatName);
		
		if(mType != null) {
			
			String front = fileName.substring(0, 12);
			String end = fileName.substring(14);
			new File(uploadPath + (front+end).replace('/', File.separatorChar)).delete();
		}
		
		new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
		
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
}
