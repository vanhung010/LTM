package c2IO;

import java.io.*;

public class Bai9 {
    public static void split(String source, int pSize) throws IOException {
        if(pSize <= 0 ) return;
        FileInputStream fis = new FileInputStream(source);

        byte[] buffer = new byte[pSize];
        int lenght;
        int part = 1; //số lần tạo file
        while((lenght = fis.read(buffer)) != -1){
            String partFilename = source + String.format(".%03d", part); //Tên file

            FileOutputStream fos = new FileOutputStream(partFilename);

            fos.write(buffer, 0, lenght);

            fos.close();

            part++;
        }

        fis.close();
    }


    public static void join(String partFilename) throws IOException {
        String source = partFilename.substring(0, partFilename.length() - 4);

        FileOutputStream fos = new FileOutputStream(source);

        byte[] buffer = new byte[1024];

        int length;
        int part = 1;
        while (true) {

            String currentPart = source + String.format(".%03d", part);

            File file = new File(currentPart);

            if (!file.exists()) {
                break;
            }
            FileInputStream fis = new FileInputStream(file);


            while ((length = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
            }
            fis.close();
            part++;
        }

        fos.close();
        }
    }


