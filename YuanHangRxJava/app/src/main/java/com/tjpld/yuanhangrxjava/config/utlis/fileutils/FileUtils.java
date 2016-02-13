package com.tjpld.yuanhangrxjava.config.utlis.fileutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipFile;


public class FileUtils {

	private static final String SD = Environment.getExternalStorageDirectory().toString(); //SD卡路径
	/***
	 * 保存照片到SD卡，位置固定
	 */
	public String SaveFile(Bitmap bitmap) {
		String pictureDir = "";
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			byte[] byteArray = baos.toByteArray();

			String saveDir = Environment.getExternalStorageDirectory() + "/wggl/pictures";
			System.out.println(saveDir);
			File dir = new File(saveDir);
			if (!dir.exists()) {
				boolean result = dir.mkdirs();
				System.out.println(result);
			}

			Long fileName = System.currentTimeMillis();

			File file = new File(saveDir, fileName.toString() + ".jpg");

			file.delete();
			if (!file.exists()) {
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(byteArray);
			pictureDir = file.getPath();
			// return pictureDir;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("FileExceptionMessage:" + e.getMessage());
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
		}

		return pictureDir;
	}

	/***
	 * 根据一组文件名获取一组照片
	 */
	public List<Bitmap> getBitmaps(List<String> filenames) {
		String saveDir = Environment.getExternalStorageDirectory() + "/wggl/pictures";
		ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
		for (String filename : filenames) {
			String filePath = saveDir + "/" + filename;
			Bitmap bitmap = null;
			try {
				File file = new File(filePath);
				if (file.exists()) {
					bitmap = BitmapFactory.decodeFile(filePath);
					bitmaps.add(bitmap);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return bitmaps;
	}

	/***
	 * 根据照片名从SD卡读取照片
	 */
	public Bitmap getBitmap(String filename) {
		String saveDir = Environment.getExternalStorageDirectory() + "/wggl/pictures";
		Bitmap bitmap = null;
		try {
			File file = new File(saveDir, filename);
			if (file.exists()) {
				String path = saveDir + "/" + filename;
				bitmap = BitmapFactory.decodeFile(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	/***
	 * 保存文件到新路径
	 */
	public static boolean save(String filedir,String filename,File newfile) {
		boolean result = true;
		InputStream inputStream = null;// 输入流
		OutputStream outputStream = null;// 输出流
		try {
			File dir = new File(filedir);
			if(!dir.exists()){//判断文件目录是否存在
				dir.mkdirs();
			}
			File fileOut = new File(filedir,filename);
			inputStream = new FileInputStream(newfile);
			outputStream = new FileOutputStream(fileOut, false);// 文件存在则覆盖掉
			byte[] bytes = new byte[1024];
			int c;
			while ((c = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, c);
			}

		} catch (Exception e) {
			// TODO: handle exception
			result = false;
			Log.e("cuowu", e.getMessage());
		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return result;
	}

		/**
		 * 获得指定文件的byte数组
		 */
		public static byte[] getBytes(String filePath){
			byte[] buffer = null;
			try {
				File file = new File(filePath);
				FileInputStream fis = new FileInputStream(file);
				ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
				byte[] b = new byte[1000];
				int n;
				while ((n = fis.read(b)) != -1) {
					bos.write(b, 0, n);
				}
				fis.close();
				bos.close();
				buffer = bos.toByteArray();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return buffer;
		}

		/**
		 * 根据byte数组，生成文件
		 */
		public static void createFileByByte(byte[] bfile, String filePath,String fileName) {
			BufferedOutputStream bos = null;
			FileOutputStream fos = null;
			File file = null;
			try {
				File dir = new File(filePath);
				if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
					dir.mkdirs();
				}
				file = new File(filePath + fileName);
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos);
				bos.write(bfile);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}

	//读文件，返回字符串
	public static String ReadJsonFile(String filedir,String filename) {
		File file = new File(filedir,filename);
		BufferedReader reader = null;
		String laststr = "";
		try {
			//System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			//一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				laststr = tempString;
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			laststr = "";
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return laststr;
	}
	/***
	 * 保存照片到绝对路径
	 */
	public static void saveBitmap(String directory,String fileName,Bitmap bitmap) {
		File dir = null;
		File file = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			dir = new File(directory);
			file = new File(dir,fileName);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			if (!file.exists()) {
				try {
					file.createNewFile();
					FileOutputStream fos = new FileOutputStream(file);
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
					fos.flush();
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static void saveBitmap(String directory,String fileName,Bitmap bitmap,int quality) {
		File dir = null;
		File file = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			dir = new File(directory);
			file = new File(dir,fileName);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			if (!file.exists()) {
				try {
					file.createNewFile();
					FileOutputStream fos = new FileOutputStream(file);
					bitmap.compress(Bitmap.CompressFormat.PNG, quality, fos);
					fos.flush();
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/***
	 * 创建文件
	 * @param fileName 文件名
	 * @param directory 目录名
	 * @return 返回文件
	 */
	public static File createFile(String directory,String fileName) {
			File dir = null;
			File file = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			dir = new File(directory);
			file = new File(dir, fileName);

			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return file;
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

	// 删除文件夹
	// param folderPath 文件夹完整绝对路径

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	// 解压缩zip格式地图数据文件，不支持中文路径
//	/***
//	 * 
//	 * @param zipFile
//	 *            下载到SD卡zip源文件
//	 * @param destination
//	 *            解压缩路径
//	 * @throws IOException
//	 */
//
//	public static void unZip(String zipFile, String destination) throws IOException {
//		ZipFile zip = new ZipFile(zipFile);
//		Enumeration<?> en = zip.entries();
//		ZipEntry entry = null;
//		byte[] buffer = new byte[8192];
//		int length = -1;
//		InputStream input = null;
//		BufferedOutputStream bos = null;
//		File file = null;
//		while (en.hasMoreElements()) {
//			entry = (ZipEntry) en.nextElement();
//			if (entry.isDirectory()) {
//
//				continue;
//			}
//
//			input = zip.getInputStream(entry);
//
//			file = new File(destination, entry.getName());
//			if (!file.getParentFile().exists()) {
//				file.getParentFile().mkdirs();
//			}
//			if (file.exists()) {
//				file.delete();
//				file.createNewFile();
//			}
//			bos = new BufferedOutputStream(new FileOutputStream(file));
//
//			while (true) {
//				length = input.read(buffer);
//				if (length == -1)
//					break;
//				bos.write(buffer, 0, length);
//			}
//			bos.close();
//			input.close();
//		}
//		zip.close();
//	}
	// 解压缩zip格式地图数据文件，支持中文名称

	// 删除SD卡指定路径下的文件
	public static boolean deleteFile(String path) {
		boolean result = false;
		File file = new File(path);
		if (file.exists()) {
			file.delete();
			result = true;
		}
		return result;

	}

	/**
	 * 删除空目录
	 * @param dir 将要删除的目录路径
	 */
	private static void DeleteEmptyDir(String dir) {
		File file = new File(dir);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * @param dir 将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful.
	 *                 If a deletion fails, the method stops attempting to
	 *                 delete and returns "false".
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			//递归删除目录中的子目录下
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	// SD中文件复制到SD卡中另一个位置
	public static boolean copyTo(String from, String to) {
		boolean result = false;
		InputStream inputStream = null;// 输入流
		OutputStream outputStream = null;// 输出流
		try {
			inputStream = new FileInputStream(from);
			File fileOut = new File(to);
			outputStream = new FileOutputStream(fileOut, false);// 文件存在则覆盖掉
			byte[] bytes = new byte[1024];
			int c;
			while ((c = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, c);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("sjdkal:s" + e);
		} finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return result;

	}

	// 获取文件夹目录下所有文件的名称
	public static String[] getFileNames(String path) {
		File d = new File(path);
		ArrayList<String> names = new ArrayList<String>();
		File list[] = d.listFiles();
		for (int i = 0; i < list.length; i++) {
			if (list[i].isFile()) {
				System.out.println(list[i].getName());
				String[] name = list[i].getName().split("\\.");
				names.add(name[0]);
			}
		}
		return names.toArray(new String[names.size()]);
	}
}
