package c1file;

import java.io.File;

public class Bai1 {
    public boolean delete(String path){
        File file = new File(path);
        if (!file.exists()) return true;
        if(file.isDirectory()) {
            File[] listFile = file.listFiles();
            for (File f : listFile) {
               delete(f.getAbsolutePath());
            }
        }
        return file.delete();
    }
    public void findAllByExts(String path, String... exts){
       File file = new File(path);
       File[] listFile = file.listFiles();

    }
}
