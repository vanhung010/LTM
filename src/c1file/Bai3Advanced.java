package c1file;

import java.io.File;
import java.util.Arrays;

public class Bai3Advanced {
    public  void dirTree(String folder){
        File root = new File(folder);
        if (!root.exists()) {
            System.out.println("Đường dẫn không tồn tại: " + folder);
            return;
        }
        if (root.isDirectory()) {
            System.out.println(root.getName().toUpperCase() + ":" + folderSize(root));
            printChildren(root, "");
        }
        else System.out.println(root.getName().toLowerCase());
    }
    private static void printChildren(File dir, String prefix) {
        File[] files = dir.listFiles();
        if (files == null) return;


        File[] dirs = Arrays.stream(files)
                .filter(File::isDirectory)
                .toArray(File[]::new);

        File[] onlyFiles = Arrays.stream(files)
                .filter(File::isFile)
                .toArray(File[]::new);


        for (File d : dirs) {
            System.out.println(prefix + "+-" + d.getName().toUpperCase() + ":" + folderSize(d));
            printChildren(d, prefix + "|   ");
        }

        // File hiển thị sau
        for (File f : onlyFiles) {
            System.out.println(prefix + "+-" + f.getName().toLowerCase());
        }
    }

    private static long folderSize(File dir) {
        long total = 0;
        File[] files = dir.listFiles();
        if (files == null) return 0;
        for (File f : files) {
            total += f.isDirectory() ? folderSize(f) : f.length();
        }
        return total;
    }
}
