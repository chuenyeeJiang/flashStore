package com.chuenyee.service;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.chuenyee.service.api.HeadPortrait;


@Service
public class HeadPortraitImpl implements HeadPortrait {
	//日志文件
	Logger logger = LoggerFactory.getLogger(HeadPortraitImpl.class);
	
	// 预览图缓存
	BufferedImage Preview;

	// 静态资源路径
	static String staticPath  ;
	
	static{
		try {
			/*
			 * 原模式
			 */
			staticPath =	new File(ResourceUtils.getURL("classpath:").getPath()).getAbsolutePath();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 上传预览图
	public void uploadPreviewDiagram(HttpServletRequest request,
			HttpServletResponse response, MultipartFile file, String fileType)
			throws Exception {

		logger.info("uploadPreviewDiagram()================run");
		
		// 定义时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		logger.info("SimpleDateFormat sdf = new SimpleDateFormat('yyyy.MM.dd.HH.mm.ss');================run");
		
		// 获取当前时间
		String date = sdf.format(new Date());
		

		// 获取用户名
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		// 上传存储路径
		String path = request.getSession().getServletContext()
				.getRealPath("/upload/");
		
		
	/*	String path = new File(ResourceUtils.getURL("classpath:").getPath()).getAbsolutePath();
		path=path.replaceAll("\\\\", "//");
		path = path+"/static/upload/";
		try {
			path = URLDecoder.decode(path, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		// 清理用户所有存储在项目中的预览图片
		File[] filesDelete = new File(path + "/" + username + "/" + fileType
				+ "/preview/").listFiles(); // 获取
		System.out.println("filesDelete:" + filesDelete);
		if (filesDelete != null) {
			for (File f : filesDelete) {
				System.out.println("f.delete();");
				f.delete();
			}
		}

		// 获取原文件名
		String filename = file.getOriginalFilename();

		// 获取头像目录
		File HeadPortraitDir = new File(path + "/" + username + "/" + fileType
				+ "/", "newPreview_time.txt");
		logger.info("File HeadPortraitDir = new File(path + '/' + username + '/' + fileType+ '/', 'newPreview_time.txt'); ================run");
		
		// 当目录不存在
		if (!HeadPortraitDir.getParentFile().exists()) {
			// 生成头像目录及文件
			HeadPortraitDir.getParentFile().mkdirs();
			logger.info("HeadPortraitDir.getParentFile().mkdirs(); ================run");
			HeadPortraitDir.createNewFile();
			logger.info("HeadPortraitDir.createNewFile(); ================run");
			
			// 当文件不存在
		} else if (!HeadPortraitDir.exists()) {
			HeadPortraitDir.createNewFile();
			logger.info("HeadPortraitDir.createNewFile(); ================run");
		}

		/*
		 * 存储预览图
		 */
		FileWriter fw = new FileWriter(HeadPortraitDir);
		logger.info("	FileWriter fw = new FileWriter(HeadPortraitDir); ================run");
		fw.write(date.toString());
		logger.info("	fw.write(date.toString());================run");
		File dir = new File(path + "/" + username + "/" + fileType
				+ "/preview/", date + ".jpg");
		logger.info("File dir = new File(path + '/' + username + '/' + fileType+ '/preview/', date + '.jpg');================run");
		dir.delete();
		logger.info("	dir.delete();");
		if (!dir.getParentFile().exists()) {
			// 生成头像目录及文件
			dir.getParentFile().mkdirs();
			logger.info("dir.getParentFile().mkdirs(); ================run");
			dir.createNewFile();
			logger.info("dir.createNewFile(); ================run");
			
			// 当文件不存在
		} else if (!HeadPortraitDir.exists()) {
			dir.createNewFile();
			logger.info("dir.createNewFile(); ================run");
		}
		logger.info("dir.mkdirs();");
		// 拷贝
		file.transferTo(dir);
		logger.info("file.transferTo(dir);");
		fw.close();

	}

	// 输出预览图
	public void Preview(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		// 获取用户名
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		// 获取目标路径
		String path = request.getSession().getServletContext()
				.getRealPath("/upload/" + username + "/headPortrait/");

		// 获取存储最新预览图文件的位置的文本
		File newPreview_time_dir = new File(path, "newPreview_time.txt");

		// 读取最新预览图文件名文本
		FileReader fr = new FileReader(newPreview_time_dir);
		BufferedReader br = new BufferedReader(fr);
		String newPreview_time = br.readLine();

		/*
		 * 读取最新预览图文件
		 */
		String imgPath = request
				.getSession()
				.getServletContext()
				.getRealPath(
						"/upload/" + username + "/headPortrait/preview/"
								+ newPreview_time + ".jpg");
		System.out.println("imgPath:" + imgPath);
		ImageIcon imgeIcon = new ImageIcon(imgPath);

		Preview = new BufferedImage(imgeIcon.getIconWidth(),
				imgeIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		System.out.println("宽:" + imgeIcon.getIconWidth() + "  高:"
				+ imgeIcon.getIconHeight());
		Graphics g2 = Preview.getGraphics();
		g2.drawImage(imgeIcon.getImage(), 0, 0, null);

		// 输出文件
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream sos = response.getOutputStream();

		ImageIO.write(Preview, "png", sos);

		System.out.println("ImageIO.write(image, 'jpg', os);");

		fr.close();
		sos.flush();
		sos.close();

	}

	// 上传头像
	public void uploadHeadPortrait(HttpServletRequest request,
			HttpServletResponse response, MultipartFile file, String str_x,
			String str_y, String str_w, String str_h, String str_previewW,
			String str_previewH, String reView) throws Exception {

		// 视图
		/* ModelAndView mv = new ModelAndView("forward:/"+reView); */

		System.out.println(file.getName());
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();

		// 定义时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

		// 获取当前时间
		String date = sdf.format(new Date());

		// 参数转整
		int x = Integer.parseInt(str_x);
		int y = Integer.parseInt(str_y);
		int w = Integer.parseInt(str_w);
		int h = Integer.parseInt(str_h);
		int previewW = Integer.parseInt(str_previewW);
		int previewH = Integer.parseInt(str_previewH);

		System.out.println("Preview.getWidth()" + Preview.getWidth());
		System.out.println("Preview.getHeight()" + Preview.getHeight());
		// 原图放大
		x = x * Preview.getWidth() / previewW;

		y = y * Preview.getHeight() / previewH;

		w = w * Preview.getWidth() / previewW;

		h = h * Preview.getHeight() / previewH;

		// 获取用户名
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		// 上传存储路径
		String path = request.getSession().getServletContext()
				.getRealPath("/upload/");

		// 获取原文件名
		String filename = file.getOriginalFilename();

		// 获取头像路径
		File HeadPortraitDir = new File(
				path + "/" + username + "/HeadPortrait", filename);

		/*
		 * 截图
		 */
		BufferedImage bufferedImage = new BufferedImage(w, h,
				BufferedImage.TYPE_4BYTE_ABGR);

		int imgWidth = Preview.getWidth();
		int imgHeight = Preview.getHeight();
		int r = w / 2;
		int r2 = r * r;
		// 二维定位抠图点
		int[][] data = new int[imgWidth][imgHeight];
		for (int i = 0; i < imgWidth; i++) {
			for (int j = 0; j < imgHeight; j++) {
				double d1 = Math.pow(i - (x + w / 2), 2)
						+ Math.pow(j - (y + h / 2), 2);
			/*	// 获取拼块面积数组
				if (d1 < r2) {
					data[i][j] = 1;
				} else {
					data[i][j] = 0;
				}*/
				data[i][j] = 1;
			}
		}

		for (int i = x; i < x + w; i++) {
			for (int j = y; j < y + h; j++) {
				if (data[i][j] == 1) {
					bufferedImage.setRGB(i - x, j - y, Preview.getRGB(i, j));
				}
			}
		}

		// 输出文件
		FileOutputStream outfile = new FileOutputStream(path + "/" + username
				+ "/HeadPortrait/" + date + ".jpg");
		ImageOutputStream i = ImageIO.createImageOutputStream(outfile);

		ImageIO.write(bufferedImage, "png", i);

		System.out.println("ImageIO.write(image, 'jpg', os);");

		i.flush();
		i.close();
		outfile.flush();
		outfile.close();

		pw.write("更新成功!");

		/*
		 * request.getRequestDispatcher("forward:/"+reView).forward(request,
		 * response);
		 */
	}

	// 输出头像
	public void getHeadPortrait(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// TODO Auto-generated method stub
		// 获取用户名
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String filename = null;
		// 获取目标路径
		String path = request.getSession().getServletContext()
				.getRealPath("/upload/" + username + "/headPortrait/");

		// 获取图片
		File[] files = new File(path).listFiles();
		System.out.println("files:"+files);
		String imgPath = null;
		
		if(files==null){
			staticPath=staticPath.replaceAll("\\\\", "//");
			imgPath = staticPath+"/static/img/defaultHeadPortrait.jpg";
			try {
				imgPath = URLDecoder.decode(imgPath, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
		for (int i = 0; i < files.length; i++) {
			System.out.println("i:" + i);
			if (files[i].getName().replaceAll("(.jpg|.png|.bmp|.gif)+", "")
					.length() != files[i].getName().length()) {
				System.out.println("filename:" + files[i].getName());
				/*
				 * if(i!=files.length-1) {
				 * System.out.println("files["+i+"].delete()");
				 * files[i].delete(); }else{
				 */
				filename = files[i].getName();
				/* } */
			}
		}
        
		/*
		 * 读取最新预览图文件
		 */
			imgPath = request
					.getSession()
					.getServletContext()
					.getRealPath(
							"/upload/" + username + "/headPortrait/" + filename);
		}
		
		System.out.println("imgPath:" + imgPath);
		ImageIcon imgeIcon = new ImageIcon(imgPath);

		System.out.println("宽:" + imgeIcon.getIconWidth() + "  高:"
				+ imgeIcon.getIconHeight());
		BufferedImage HeadPortrait = new BufferedImage(imgeIcon.getIconWidth(),
				imgeIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics g2 = HeadPortrait.getGraphics();
		g2.drawImage(imgeIcon.getImage(), 0, 0, null);

		// 输出文件
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream sos = response.getOutputStream();

		ImageIO.write(HeadPortrait, "png", sos);

		System.out.println("ImageIO.write(image, 'jpg', os);");

		sos.flush();
		sos.close();
	}

}
