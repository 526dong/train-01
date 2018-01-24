package dong.utils;

import org.apache.lucene.document.FieldType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by ${xzd} on 2017/11/30.
 * @Description
 */
public class FileUtil {
    public static List<File> listAllFiles(String path){
        //file list
        List<File> fileList = new ArrayList<>();

        //读取目录文件路径
        File file = new File(path);
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File myFile:files) {
                fileList.add(myFile);
            }
        }
        return fileList;
    }

    public static String readFile(String path) {
        StringBuilder sb = new StringBuilder();
        File file = new File(path);

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(sb.toString());
        return sb.toString();
    }
}
