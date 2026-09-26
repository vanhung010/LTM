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
    public void findAllByExts(String path, String... exts) {
        File file = new File(path);
        File[] listFile = file.listFiles();
        if (listFile == null) return;

        for (File f : listFile) {
            if (f.isDirectory()) {
                findAllByExts(f.getAbsolutePath(), exts);
            }
            for (String ext : exts) {
                if (f.getName().endsWith("." + ext)) {
                    System.out.println(f.getAbsolutePath());
                }
            }
        }
    }
    }
