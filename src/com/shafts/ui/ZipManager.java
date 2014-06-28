package com.shafts.ui;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
* 此类用于测试JAVA的ZIP压缩及解压
* 
* @author Sunny Lee (2008-1-4)
* @version 1.0
*/
public class ZipManager {

/**
* zip压缩功能测试. 将d:\\temp\\zipout目录下的所有文件连同子目录压缩到d:\\temp\\out.zip.
* 此方法可保留被压缩文件的目录结构
* 
* @param baseDir
*            所要压缩的目录名（包含绝对路径）
* @param objFileName
*            压缩后的文件名
* @throws Exception
*/
public void createZip(String baseDir, String objFileName) throws Exception {
   File folderObject = new File(baseDir);

   if (folderObject.exists()) {
    List fileList = getSubFiles(new File(baseDir));


    // 压缩文件名
    ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
      objFileName));

    ZipEntry ze = null;
    byte[] buf = new byte[1024];
    int readLen = 0;
    for (int i = 0; i < fileList.size(); i++) {
     File f = (File) fileList.get(i);
     System.out.println("Adding: " + f.getPath() + f.getName());

     /*
     * 创建一个ZipEntry，并设置Name和其它的一些属性
     * 注意，该ZipEntry是包含目录结构的，此功能是由getAbsFileName(String,string)完成的
     */
     ze = new ZipEntry(getAbsFileName(baseDir, f));
     ze.setSize(f.length());
     ze.setTime(f.lastModified());

     // 将ZipEntry加到zos中，再写入实际的文件内容
     zos.putNextEntry(ze);
     InputStream is = new BufferedInputStream(new FileInputStream(f));
     while ((readLen = is.read(buf, 0, 1024)) != -1) {
      zos.write(buf, 0, readLen);
     }
     is.close();
     System.out.println("done...");
    }
    zos.close();
   } else {
    throw new Exception("this folder isnot exist!");
   }
}

/**
* 对某一个文件进行压缩，不允许是目录或包含子目录
* 
* @param sourceFileName
*            被压缩的目标文件，不允许是目录或包含子目录
* @param zipFilename
*            为压缩后的文件指定的名称
* @return 压缩文件的大小
* @throws Exception
*/
public long createFileToZip(String sourceFileName, String zipFilename)
    throws Exception {

   File sourceFile = new File(sourceFileName);

   byte[] buf = new byte[1024];

   // 压缩文件名
   File objFile = new File(zipFilename);

   ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(objFile));

   ZipEntry ze = null;
   // 创建一个ZipEntry，并设置Name和其它的一些属性
   ze = new ZipEntry(sourceFile.getName());
   ze.setSize(sourceFile.length());
   ze.setTime(sourceFile.lastModified());

   // 将ZipEntry加到zos中，再写入实际的文件内容
   zos.putNextEntry(ze);

   InputStream is = new BufferedInputStream(
     new FileInputStream(sourceFile));

   int readLen = -1;
   while ((readLen = is.read(buf, 0, 1024)) != -1) {
    zos.write(buf, 0, readLen);
   }
   is.close();
   zos.close();

   return objFile.length();
}

/**
* 测试解压缩功能. 将d:\\download\\test.zip连同子目录解压到d:\\temp\\zipout目录下.
* 
* @param sourceZip
*            待解压的zip文件
* @param outFileName
* @throws IOException
*/
public void releaseZipToFile(String sourceZip, String outFileName)
    throws IOException {
   ZipFile zfile = new ZipFile(sourceZip);
   System.out.println("待解压的文件是： " + zfile.getName());
   Enumeration zList = zfile.entries();
   ZipEntry ze = null;
   byte[] buf = new byte[1024];
   while (zList.hasMoreElements()) {
    // 从ZipFile中得到一个ZipEntry
    ze = (ZipEntry) zList.nextElement();
    if (ze.isDirectory()) {
     continue;
    }
    // 以ZipEntry为参数得到一个InputStream，并写到OutputStream中
    InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
    OutputStream os = new BufferedOutputStream(new FileOutputStream(
      getRealFileName(outFileName, ze.getName())));
    int readLen = 0;
    while ((readLen = is.read(buf, 0, 1024)) != -1) {
     os.write(buf, 0, readLen);
    }
    is.close();
    os.close();
    System.out.println("Extracted: " + ze.getName());
   }
   zfile.close();

}

/**
* 取得指定目录以及其各级子目录下的所有文件的列表，注意，返回的列表中不含目录.
* 
* @param baseDir
*            File 指定的目录
* @return 包含java.io.File的List
*/
private List getSubFiles(File baseDir) {
   List fileList = new ArrayList();
   File[] tmp = baseDir.listFiles();
   for (int i = 0; i < tmp.length; i++) {
    // 如果是文件，直接加入fileList
    if (tmp[i].isFile()) {
     fileList.add(tmp[i]);
    }
    // 如果是目录，递归调用本方法，并将结果集加入fileList
    if (tmp[i].isDirectory()) {
     fileList.addAll(getSubFiles(tmp[i]));
    }
   }
   return fileList;
}

/**
* 给定根目录，返回一个相对路径所对应的实际文件名.
* 
* @param baseDir
*            指定根目录
* @param absFileName
*            相对路径名，来自于ZipEntry中的name
* @return java.io.File 实际的文件
*/
private File getRealFileName(String baseDir, String absFileName) {
   String[] dirs = absFileName.split("/");
   File ret = new File(baseDir);
   // 创建目录结构
   if (dirs.length > 1) {
    for (int i = 0; i < dirs.length - 1; i++) {
     ret = new File(ret, dirs[i]);
    }
   }
   if (!ret.exists()) {
    ret.mkdirs();
   }
   ret = new File(ret, dirs[dirs.length - 1]);
   return ret;
}

/**
* 给定根目录及文件的实际路径，返回带有相对路径的文件名，用于zip文件中的路径.
* 如将绝对路径，baseDir\dir1\dir2\file.txt改成 dir1/dir2/file.txt
* 
* @param baseDir
*            java.lang.String 根目录
* @param realFileName
*            java.io.File 实际的文件名
* @return 相对文件名
*/
private String getAbsFileName(String baseDir, File realFileName) {
   File real = realFileName;
   File base = new File(baseDir);
   String ret = real.getName();

   while (true) {
    real = real.getParentFile();
    if (real == null)
     break;
    if (real.equals(base))
     break;
    else {
     ret = real.getName() + "/" + ret;
    }
   }
   return ret;
}

/*public static void main(String args[]) {
   ZipManager manager = new ZipManager();
   String filePath = "E:\\1989511";
   try {
    //manager.releaseZipToFile("E:\\temp\\1989511.zip",
     // "E:\\temp\\1989511");
    // manager.createZip("E:\\1989511", "E:\\1989511.zip");
	   manager.createZip(filePath, filePath+".zip");
   } catch (Exception e) {
    e.printStackTrace();
   }
   System.out.println("over");
}*/
}

