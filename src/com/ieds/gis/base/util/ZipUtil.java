package com.ieds.gis.base.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.ZipException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 实现的Zip工具,压缩与解压缩
 * 
 * @author once
 */
public class ZipUtil {
	private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte

	/**
	 * 解压缩文件（夹），通过应用ant.jar解决中文乱码
	 * 
	 * @param srcPathName
	 *            要解压缩的文件
	 * @param pathName
	 *            生成的解压缩文件
	 */
	public static boolean compress(String zipfileName, String filePath) {
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {

			ZipFile zipFile = new ZipFile(zipfileName, "GB2312");
			Enumeration emu = ((ZipFile) zipFile).getEntries();

			while (emu.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) emu.nextElement();
				// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
				if (entry.isDirectory()) {
					new File(filePath, entry.getName()).mkdirs();
					continue;
				}
				bis = new BufferedInputStream(zipFile.getInputStream(entry));
				File file = new File(filePath, entry.getName());
				// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos, BUFF_SIZE);
				int count;
				byte data[] = new byte[BUFF_SIZE];
				while ((count = bis.read(data, 0, BUFF_SIZE)) != -1) {
					bos.write(data, 0, count);
				}

			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.flush();
					bos.close();
				}
				if (bis != null) {
					bis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 批量压缩文件（夹）
	 * 
	 * @param resFileList
	 *            要压缩的文件（夹）列表
	 * @param zipFile
	 *            生成的压缩文件
	 * @throws IOException
	 *             当压缩过程出错时抛出
	 */
	public static void zipFiles(File[] resFileList, File zipFile)
			throws IOException {
		ZipOutputStream zipout = null;
		try {
			zipout = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(zipFile), BUFF_SIZE));
			for (File resFile : resFileList) {
				zipFile(resFile, zipout, "");
			}
		} finally {
			if (zipout != null) {
				zipout.close();
			}
		}
	}

	/**
	 * 批量压缩文件（夹）
	 * 
	 * @param resFileList
	 *            要压缩的文件（夹）列表
	 * @param zipFile
	 *            生成的压缩文件
	 * @param comment
	 *            压缩文件的注释
	 * @throws IOException
	 *             当压缩过程出错时抛出
	 */
	public static void zipFiles(Collection<File> resFileList, File zipFile,
			String comment) throws IOException {
		ZipOutputStream zipout = null;
		try {
			zipout = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(zipFile), BUFF_SIZE));
			for (File resFile : resFileList) {
				zipFile(resFile, zipout, "");
			}
			zipout.setComment(comment);
		} finally {
			if (zipout != null) {
				zipout.close();
			}
		}
	}

	/**
	 * 解压缩一个文件
	 * 
	 * @param zipFile
	 *            压缩文件
	 * @param folderPath
	 *            解压缩的目标目录
	 * @throws IOException
	 *             当解压缩过程出错时抛出
	 */
	public static void upZipFile(File zipFile, String folderPath)
			throws ZipException, IOException {
		File desDir = new File(folderPath);
		if (!desDir.exists()) {
			desDir.mkdirs();
		}
		ZipFile zf = new ZipFile(zipFile);
		for (Enumeration<?> entries = zf.getEntries(); entries
				.hasMoreElements();) {
			InputStream in = null;
			OutputStream out = null;
			try {
				ZipEntry entry = ((ZipEntry) entries.nextElement());
				in = zf.getInputStream(entry);
				String str = folderPath + entry.getName();
				str = new String(str.getBytes("8859_1"), "GB2312");
				File desFile = new File(str);
				int flag = 1;
				if (!desFile.exists()) {
					if (flag == 1) {
						desFile.mkdir();
						flag = 2;
					} else {
						File fileParentDir = desFile.getParentFile();
						if (!fileParentDir.exists()) {
							fileParentDir.mkdirs();
						}
						desFile.createNewFile();
					}
				}
				out = new FileOutputStream(desFile);
				byte buffer[] = new byte[BUFF_SIZE];
				int realLength;
				while ((realLength = in.read(buffer)) != -1) {
					out.write(buffer, 0, realLength);
				}
			} finally {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			}
		}
	}

	/**
	 * 解压文件名包含传入文字的文件
	 * 
	 * @param zipFile
	 *            压缩文件
	 * @param folderPath
	 *            目标文件夹
	 * @param nameContains
	 *            传入的文件匹配名
	 * @throws ZipException
	 *             压缩格式有误时抛出
	 * @throws IOException
	 *             IO错误时抛出
	 */
	public static ArrayList<File> upZipSelectedFile(File zipFile,
			String folderPath, String nameContains) throws ZipException,
			IOException {
		ArrayList<File> fileList = new ArrayList<File>();

		File desDir = new File(folderPath);
		if (!desDir.exists()) {
			desDir.mkdir();
		}

		ZipFile zf = new ZipFile(zipFile);
		for (Enumeration<?> entries = zf.getEntries(); entries
				.hasMoreElements();) {
			ZipEntry entry = ((ZipEntry) entries.nextElement());
			if (entry.getName().contains(nameContains)) {
				InputStream in = null;
				OutputStream out = null;
				String str = folderPath + File.separator + entry.getName();
				str = new String(str.getBytes("8859_1"), "GB2312");
				// str.getBytes("GB2312"),"8859_1" 输出
				// str.getBytes("8859_1"),"GB2312" 输入
				File desFile = new File(str);
				try {
					in = zf.getInputStream(entry);

					if (!desFile.exists()) {
						File fileParentDir = desFile.getParentFile();
						if (!fileParentDir.exists()) {
							fileParentDir.mkdirs();
						}
						desFile.createNewFile();
					}
					out = new FileOutputStream(desFile);
					byte buffer[] = new byte[BUFF_SIZE];
					int realLength;
					while ((realLength = in.read(buffer)) != -1) {
						out.write(buffer, 0, realLength);
					}
				} finally {
					if (in != null) {
						in.close();
					}
					if (out != null) {
						out.close();
					}
				}
				fileList.add(desFile);
			}
		}
		return fileList;
	}

	/**
	 * 获得压缩文件内文件列表
	 * 
	 * @param zipFile
	 *            压缩文件
	 * @return 压缩文件内文件名称
	 * @throws ZipException
	 *             压缩文件格式有误时抛出
	 * @throws IOException
	 *             当解压缩过程出错时抛出
	 */
	public static ArrayList<String> getEntriesNames(File zipFile)
			throws ZipException, IOException {
		ArrayList<String> entryNames = new ArrayList<String>();
		Enumeration<?> entries = getEntriesEnumeration(zipFile);
		while (entries.hasMoreElements()) {
			ZipEntry entry = ((ZipEntry) entries.nextElement());
			entryNames.add(new String(getEntryName(entry).getBytes("GB2312"),
					"8859_1"));
		}
		return entryNames;
	}

	/**
	 * 获得压缩文件内压缩文件对象以取得其属性
	 * 
	 * @param zipFile
	 *            压缩文件
	 * @return 返回一个压缩文件列表
	 * @throws ZipException
	 *             压缩文件格式有误时抛出
	 * @throws IOException
	 *             IO操作有误时抛出
	 */
	public static Enumeration<?> getEntriesEnumeration(File zipFile)
			throws ZipException, IOException {
		ZipFile zf = new ZipFile(zipFile);
		return zf.getEntries();

	}

	/**
	 * 取得压缩文件对象的注释
	 * 
	 * @param entry
	 *            压缩文件对象
	 * @return 压缩文件对象的注释
	 * @throws UnsupportedEncodingException
	 */
	public static String getEntryComment(ZipEntry entry)
			throws UnsupportedEncodingException {
		return new String(entry.getComment().getBytes("GB2312"), "8859_1");
	}

	/**
	 * 取得压缩文件对象的名称
	 * 
	 * @param entry
	 *            压缩文件对象
	 * @return 压缩文件对象的名称
	 * @throws UnsupportedEncodingException
	 */
	public static String getEntryName(ZipEntry entry)
			throws UnsupportedEncodingException {
		return new String(entry.getName().getBytes("GB2312"), "8859_1");
	}

	/**
	 * 压缩文件
	 * 
	 * @param resFile
	 *            需要压缩的文件（夹）
	 * @param zipout
	 *            压缩的目的文件
	 * @param rootpath
	 *            压缩的文件路径
	 * @throws FileNotFoundException
	 *             找不到文件时抛出
	 * @throws IOException
	 *             当压缩过程出错时抛出
	 */
	private static void zipFile(File resFile, ZipOutputStream zipout,
			String rootpath) throws FileNotFoundException, IOException {
		rootpath = rootpath
				+ (rootpath.trim().length() == 0 ? "" : File.separator)
				+ resFile.getName();
		rootpath = new String(rootpath);
		if (resFile.isDirectory()) {
			File[] fileList = resFile.listFiles();
			if (fileList != null) {
				for (File file : fileList) {
					zipFile(file, zipout, rootpath);
				}
			}
		} else {
			BufferedInputStream in = null;
			try {
				byte buffer[] = new byte[BUFF_SIZE];
				in = new BufferedInputStream(new FileInputStream(resFile),
						BUFF_SIZE);
				zipout.putNextEntry(new ZipEntry(rootpath));
				zipout.setEncoding("GBK");
				int realLength;
				while ((realLength = in.read(buffer)) != -1) {
					zipout.write(buffer, 0, realLength);
				}
			} finally {
				if (in != null) {
					in.close();
				}
				if (zipout != null) {
					zipout.flush();
					zipout.closeEntry();
				}
			}
		}
	}
}