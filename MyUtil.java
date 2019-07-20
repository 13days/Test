package MyFileUtil;

import java.io.*;
import java.nio.file.Files;
import java.util.Date;

public class MyUtil {
    // 计算一个文件的大小,单位Byte,如果文件不存在返回-1
    public static long fileSizeByByte(String fileName){
        File file = new File(fileName);
        return fileSizeByByte(file);
    }
    public static long fileSizeByByte(File file){
        if(file.exists()){
            long sum = 0;
            // 如果是文件夹,则统计文件夹下文件的和
            if(file.isDirectory()){
                for(File zf : file.listFiles()){
                    if(zf.isDirectory()){
                        sum += fileSizeByByte(zf); // 如果是文件夹则递归地去计算
                    }else{
                        sum += zf.length();
                    }
                }
            }else{
                sum += file.length();
            }
            return sum;
        }else{
            System.out.println("文件"+file.getName()+"不存在!");
            return -1;
        }
    }
    // 计算一个文件的大小，单位KB
    public static double fileSizeByKB(File file){
        long size = fileSizeByByte(file);
        return size*1.0/1024;
    }
    public static double fileSizeByKB(String fileName){
        File file = new File(fileName);
        return fileSizeByKB(file);
    }
    // 计算一个文件的大小，单位MB
    public static double fileSizeByMB(File file){
        long size = fileSizeByByte(file);
        return size*1.0/(1024*1024);
    }
    public static double fileSizeByMB(String fileName){
        File file = new File(fileName);
        return fileSizeByMB(file);
    }

    // 文件剪裁，剪切掉文件的前cutPos字节
    public static void fileCutByByte(File file,long cutPos, String saveName){
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(saveName));
            fileInputStream.skip(cutPos); // 跳过前cutPosz个字节
            byte[] b = new byte[1024];
            while(fileInputStream.read(b) != -1){
                fileOutputStream.write(b);
            }
            fileInputStream.close();
            fileOutputStream.close();
            System.out.println("文件剪裁完成!");
        } catch (FileNotFoundException e) {
            System.out.println(file.getName()+"文件不存在");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("不合法剪辑");
            e.printStackTrace();
        }
    }
    // 文件剪裁，剪切掉文件的前cutPos字节
    public static void fileCutByByte(String fileName,long cutPos, String saveName){
        fileCutByMB(new File(fileName),cutPos,saveName);
    }
    // 文件剪裁，剪切掉文件的前cutPos KB
    public static void fileCutByKB(String fileName,double cutPos, String saveName){
        fileCutByByte(new File(fileName),(long)cutPos*1024,saveName);
    }
    // 文件剪裁，剪切掉文件的前cutPos KB
    public static void fileCutByKB(File file,double cutPos, String saveName){
        fileCutByByte(file,(long)cutPos*1024,saveName);
    }
    // 文件剪裁，剪切掉文件的前cutPos MB
    public static void fileCutByMB(String fileName,double cutPos, String saveName){
        fileCutByByte(new File(fileName),(long)cutPos*1024*1024,saveName);
    }
    // 文件剪裁，剪切掉文件的前cutPos MB
    public static void fileCutByMB(File file,double cutPos, String saveName){
        fileCutByByte(file,(long)cutPos*1024*1024,saveName);
    }
    // 文件合并,把firstFile和secondFile文件合并到targetFileName中
    public static void fileMerge(File firstFile, File secondFile, String targetFileName){
        try {
            FileInputStream fileInputStream = new FileInputStream(firstFile);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(targetFileName));
            byte[] buf = new byte[1024];
            while(fileInputStream.read(buf) != -1){
                fileOutputStream.write(buf);
            }
            fileInputStream = new FileInputStream(secondFile);
            while(fileInputStream.read(buf) != -1){
                fileOutputStream.write(buf);
            }
            fileInputStream.close();
            fileOutputStream.close();
            System.out.println("文件合并成功!");
        }catch (FileNotFoundException e){
            System.out.println("源文件不存在");
            e.printStackTrace();
        }catch (IOException e) {
            System.out.println("合并失败");
            e.printStackTrace();
        }
    }
    // 文件合并,把firstFileName和secondFileName文件合并到targetFileName中
    public static void fileMerge(String firstFileName, String  secondFileName, String targetFileName){
        fileMerge(new File(firstFileName),new File(secondFileName),targetFileName);
    }
    // 删除文件
    public static void delFile(File file){
        if(file.exists()){
            if(file.delete())
                System.out.println("文件删除成功!");
            else
                System.out.println("文件:"+file.getName()+"不能删除!");
        }else{
            System.out.println("文件不存在!");
        }
    }
    public static void delFile(String fileName){
        delFile(new File(fileName));
    }

    // 创建文件
    public static void creatFile(File file){
        try {
            file.createNewFile();
            System.out.println("文件创建成功");
        } catch (IOException e) {
            System.out.println("文件创建不成功!");
            e.printStackTrace();
        }
    }
    public static void creatFile(String fileName){
        creatFile(new File(fileName));
    }

    // 复制文件sourceFile复制到targetFile
    public static void copyFile(File sourceFile, String targetFile){
        if(sourceFile.exists()){
            File target = new File(targetFile);
            if(target.exists()){
                System.out.println("复制失败，文件已经存在!");
                return;
            }
            try {
                Files.copy(sourceFile.toPath(), target.toPath());
                System.out.println("文件复制成功!");
            } catch (IOException e) {
                System.out.println("文件复制失败");
                e.printStackTrace();
            }
        }else{
            System.out.println(sourceFile.getName()+"不存在");
        }
    }
    public static void copyFile(String sourceFileName, String targetFileName){
        copyFile(new File(sourceFileName), targetFileName);
    }

    // 查看文件信息，文件状态
    public static void fileState(String fileName) throws IOException{
        System.out.println("------"+fileName+"------");
        File f = new File(fileName);   // 创建 File 类对象
        if(!f.exists()){ // 测试文件是否存在
            System.out.println("文件没收找到"+"\n");
            return;
        }
        System.out.println("文件全名为:"+f.getCanonicalPath());
        String p = f.getParent();
        if(p!=null){
            System.out.println("Parent directory: "+p); // 显示文件的父目录
        }
        if(f.canRead()){
            System.out.println("File is readable.");    // 测试文件是否可读
        }
        if(f.canWrite()){ // 测试文件是否可写
            System.out.println("File is writable.");
        }
        Date d = new Date();
        d.setTime(f.lastModified());
        System.out.println("Last modifiled : " + d);
        if(f.isFile()){
            System.out.println("文件大小是: "+f.length()+"bytes");
        }else if(f.isDirectory()){
            System.out.println("它是目录");
        }else{
            System.out.println("既不是文件也不是目录");
        }
        System.out.println();
    }
    public static void main(String[] args) throws IOException {
        // 两首歌曲(name,name1)的合并到name2
        String name = "D:\\TSBrowserDownloads\\demomusic-java\\示例歌曲\\星月神话.mp3";
        String name1 = "D:\\TSBrowserDownloads\\demomusic-java\\示例歌曲\\我只在乎你.mp3";
        String name2 = "D:\\TSBrowserDownloads\\demomusic-java\\示例歌曲\\合并.mp3";
        MyUtil.fileMerge(name,name1,name2);

        // 新歌曲的大小
        System.out.println("name2大小:"+fileSizeByMB(name2)+"MB");

        // 查看name2信息
        MyUtil.fileState(name2);

        // 剪切掉前3MB,另存为name3
        String name3 = "D:\\TSBrowserDownloads\\demomusic-java\\示例歌曲\\剪切.mp3";
        MyUtil.fileCutByMB(name2,3,name3);
        System.out.println("name3大小:"+fileSizeByMB(name3)+"MB");

        // 删除name2文件
        MyUtil.delFile(name2);

        // 复制name3到name4
        String name4 = "D:\\TSBrowserDownloads\\demomusic-java\\示例歌曲\\复制.mp3";
        MyUtil.copyFile(name3,name4);

        // 新建name5
        String name5 = "D:\\TSBrowserDownloads\\demomusic-java\\示例歌曲\\新建.mp3";
        MyUtil.creatFile(name5);

    }
}
