package c2IO.bai11;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class StudentFileManager {

    private static final int MAX_NAME_LEN = 50;

    private static final int REC_SIZE = 4 + MAX_NAME_LEN * 2 + 4 + 8;
    private static final int HEADER_SIZE = 4 + 4;

    private final String fileName;

    public StudentFileManager(String fileName) throws IOException {
        this.fileName = fileName;
        File f = new File(fileName);
        if(!f.exists()){
            //Nếu chưa có file thì tạo
            try(RandomAccessFile randomAccessFile = new RandomAccessFile(f, "rw")){
                randomAccessFile.writeInt(0); //Số lượng sinh viên
                randomAccessFile.writeInt(REC_SIZE);
                randomAccessFile.setLength(8);
            }
        }
    }

    public void addStudent(Student st)  throws IOException {
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw")){
            //Ghi đè số lượng sinh viên
            //Tăng size
            //Nhảy con chuột xuống vị trí cần thêm sinh viên
            //Ghi sinh viên
            int numberStudent = randomAccessFile.readInt();
            randomAccessFile.seek(0);
            randomAccessFile.writeInt(numberStudent + 1);
            randomAccessFile.setLength(HEADER_SIZE + REC_SIZE * (numberStudent + 1));
            randomAccessFile.seek(HEADER_SIZE + (long) REC_SIZE * numberStudent);

            //build name
           String name = buildNameFile(st.getName());

            randomAccessFile.writeInt(st.getId());
            randomAccessFile.writeChars(name);
            randomAccessFile.writeInt(st.getbYear());
            randomAccessFile.writeDouble(st.getGrade());




        }
    }

    public Student getStudent(int index) throws IOException {
        Student student = new Student();
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw")){
            //Nhảy trỏ
            //đọc
            int numberStudent = randomAccessFile.readInt();
            if(index >= numberStudent || index < 0 ) return null;

            randomAccessFile.seek(HEADER_SIZE  +(long) REC_SIZE * index);

            student.setId(randomAccessFile.readInt());
          String name = buildNameStudent(randomAccessFile);

           student.setName(name);
           student.setbYear(randomAccessFile.readInt());
           student.setGrade(randomAccessFile.readDouble());

        }
        return student;
    }

    public void updateStudent(int index, Student newSt)  throws IOException {
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw")) {
            int numberStudent = randomAccessFile.readInt();
            if(index >= numberStudent || index < 0 ) return;

            randomAccessFile.seek(HEADER_SIZE  + (long) REC_SIZE * index);

            //build name
            String name = buildNameFile(newSt.getName());

            //ghi đè
            randomAccessFile.writeInt(newSt.getId());
            randomAccessFile.writeChars(name);
            randomAccessFile.writeInt(newSt.getbYear());
            randomAccessFile.writeDouble(newSt.getGrade());

        }

        }

        public Student findById(int id)  throws IOException{
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw")){
            int numberStudent = randomAccessFile.readInt();
            for(int i = 0; i < numberStudent; i++){
                randomAccessFile.seek(HEADER_SIZE +(long) i * REC_SIZE);
                //nếu tìm thấy
                if(randomAccessFile.readInt() == id){
                    return new Student(id, buildNameStudent(randomAccessFile), randomAccessFile.readInt(), randomAccessFile.readDouble());
                }
            }
            return null;
        }
        }


        private String buildNameFile(String name){
            if(name.length() > MAX_NAME_LEN) {
                name = name.substring(0, MAX_NAME_LEN);
            }
            else {
                StringBuilder stringBuilder = new StringBuilder(name);
                while (stringBuilder.length() < MAX_NAME_LEN) {
                    //Nối thêm khoảng trắng vào
                    stringBuilder.append(' ');
                }
                name = stringBuilder.toString();
            }
            return name;
        }

        private String buildNameStudent(RandomAccessFile randomAccessFile) throws IOException{
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i < MAX_NAME_LEN; i++){
                stringBuilder.append(randomAccessFile.readChar());
            }
            return stringBuilder.toString().trim();
        }
    }


