package c1file;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Bai3 {
    private String defaultDir;
    private File defaultFile;
    private Scanner sc = new Scanner(System.in);

    public String getDefaultDir() {
        return defaultDir;
    }

    public void setDefaultDir(String defaultDir) {
        this.defaultDir = defaultDir;
    }

    public Bai3(String defaultDir) {
        this.defaultDir = defaultDir;
        defaultFile = new File(defaultDir);
    }

    public void cmd() {
        while (true) {
            System.out.print(defaultDir + ">");
            String prompt = sc.nextLine();
            StringTokenizer st = new StringTokenizer(prompt);
            String[] promptValue = new String[2];
            int count = 0;
            while (st.hasMoreTokens() && count < 2) {
                if (promptValue[0] == null) {
                    promptValue[0] = st.nextToken();
                } else promptValue[1] = st.nextToken();
                count++;
            }
            switch (promptValue[0]) {
                case ("EXIT"):
                  if(promptValue[1] == null) exits();
                  break;
                case ("CD"):
                    cd(promptValue[1]);
                    break;
                case ("DIR"):
                    dir();
                    break;
                case ("DELETE"):
                    deleted(promptValue[1]);
                    break;
            }
        }

    }
    private void exits () {
        System.exit(0);
    }

    private void cd(String value){
        if(value == null) return;
        File newDir;
        if(value.equals("..")){
            newDir = defaultFile.getParentFile();

            if(newDir == null) {
              return;
            }

        }
        else {
          newDir = new File(value);
        }
        if (!newDir.exists() || !newDir.isDirectory()) {

            return;
        }
        defaultFile = newDir;
        this.setDefaultDir(newDir.getAbsolutePath());
    }

    private void dir(){
        File[] listFile = defaultFile.listFiles();
        for(File f: listFile){
            if(f.isDirectory()){
                System.out.println(f.getName().toUpperCase());
            }
        }
        for(File f: listFile){
            if(f.isFile()){
                System.out.println(f.getName().toLowerCase());
            }
        }
    }

    private void deleted(String value){
        //Xoa toàn bộ file
        File[] listFile = defaultFile.listFiles();
        if(listFile == null) return;
        if(value.equals("file")){
            for(File f: listFile){
                if(f.isDirectory()){
                    deleted(value);
                }
                f.delete();
            }
        }
        if(value.equals("folder")){
            for(File f: listFile){
                if(f.isDirectory()){
                   f.delete();
                }

            }
        }
    }
    }

