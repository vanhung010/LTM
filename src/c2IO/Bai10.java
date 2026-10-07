package c2IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Bai10 {

    public void pack(String folder, String packedFile) throws IOException {
        File file = new File(folder);
        if(!file.exists()) {
            System.out.println("Thư mục không tồn tại: "+folder);
            return;
        }
        File[] listFiles = file.listFiles(File::isFile);

        try(RandomAccessFile rafOut = new RandomAccessFile(packedFile, "rw")){
            //Nếu đã tồn tại trước đó xóa dữ liệu cũ
            rafOut.setLength(0);
            //Định dạng file Số lượng file - [tên - kích thước - nội dung file]
            rafOut.writeInt(listFiles.length);

            for(File f: listFiles) {
                rafOut.writeUTF(f.getName());
                rafOut.writeLong(f.length());

                try(RandomAccessFile rafIn = new RandomAccessFile(f, "r")){
                    byte[] buffer = new byte[4092];
                    int n;
                    while((n = rafIn.read(buffer)) != -1) {
                        rafOut.write(buffer, 0, n);
                    }
                }
            }
        }
    }

    public void unPack(String packedFile, String extractFile, String destFile) throws IOException{

        try(RandomAccessFile rafInputPack = new RandomAccessFile(packedFile, "r")){
            //Đọc số lượng file
            int numberFile = rafInputPack.readInt();
            //Duyệt qua từng file
            for(int i = 0; i< numberFile; i++){
                String nameFile = rafInputPack.readUTF();
                Long sizeFile = rafInputPack.readLong();
                    //Nếu tìm thấy
                if(nameFile.equalsIgnoreCase(extractFile)){
                    try(RandomAccessFile rafOut = new RandomAccessFile(destFile, "rw")){
                        rafOut.setLength(0);
                        byte[] buffer = new byte[4092];

                        long byteNeedRead = sizeFile;
                        while(byteNeedRead > 0) {
                            int byteRead = (int) Math.min(4092, byteNeedRead);

                            int n = rafInputPack.read(buffer, 0, byteRead); //Đọc

                            rafOut.write(buffer, 0, n); //Ghi
                            byteNeedRead -= n;

                        }
                    }
                    return;
                }
                else {
                    rafInputPack.seek(rafInputPack.getFilePointer() + sizeFile);
                }
            }
        }
    }
}
