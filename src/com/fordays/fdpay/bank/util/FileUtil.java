package com.fordays.fdpay.bank.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

import com.neza.tool.DateUtil;

/**
 * 文件操作工具类
 */
public class FileUtil {
	
	/**
	 * 日志文件增量处理方法
	 * 
	 * @param String
	 *            初始文件
	 * @param int
	 *            增量大小(kb)
	 */

	public static void autoIncrement(String file, int incrementSize) {
		
		
		int bytesum = 0;
		int byteread = 0;
		File oldFile = new File(file);
		
		if (!oldFile.exists()) {
			return;
		}
		InputStream inStream = null;
		String newFile = "";

		try {
			inStream = new FileInputStream(file);
			int oldFileSize = inStream.available() / 1024;// byte size

			if (oldFileSize > incrementSize) { // 如果文件内容超出设置范围
				// 复制日志内容到新文件
				if (oldFile.exists()) {
					newFile = getNewFileName(file);
					FileOutputStream fs = new FileOutputStream(newFile);
					byte[] buffer = new byte[1444];
					// int length;
					while ((byteread = inStream.read(buffer)) != -1) {
						bytesum += byteread; // 字节数 文件大小
						// System.out.println(bytesum);
						fs.write(buffer, 0, byteread);
					}
					inStream.close();
					// 删除旧文件
					oldFile.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取新的文件名
	 */
	public static String getNewFileName(String oldfilePath) {
		String head = oldfilePath.substring(0, oldfilePath.length() - 4);
		String nowTime = DateUtil.getDateString("HHMMss");
		String newFilePath = head + "." + nowTime + ".log";
		return newFilePath;
	}
	
	
	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String 文件内容
	 * @return boolean
	 */
	public void newFile(String filePathAndName, String fileContent) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();
		} catch (Exception e) {
			System.out.println("新建目录操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			myDelFile.delete();
		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
//				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}
	
	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 * 
	 * @param fileName
	 *            文件名
	 */
	public static void readFileByChars(String fileName) {
		File file = new File(fileName);
		Reader reader = null;
		try {
			System.out.println("以字符为单位读取文件内容，一次读一个字节：");
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while ((tempchar = reader.read()) != -1) {
				System.out.print((char) tempchar);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取文件大小
	 */
	public static int getFileSize(String file) {
		File myLogFile = new File(file);

		return getFileSize(myLogFile);
	}

	public static int getFileSize(File file) {
		int size = 0;
		FileInputStream inFile = null;
		try {
			inFile = new FileInputStream(file);

			size = inFile.available();// byte size

		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}
}
