package ystruct.com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yang on 15-12-28.
 */
public class test {
    public static void main(String[] args) {
        FileInputStream file = null;
        FileOutputStream out = null;
        int  v=9;
        try {
              file = new FileInputStream("1.txt");
              out = new FileOutputStream("1.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            out.write((v >>> 24) & 0xFF);
            out.write((v >>> 16) & 0xFF);
            out.write((v >>>  8) & 0xFF);
            out.write((v >>>  0) & 0xFF);

        } catch (IOException e) {
            e.printStackTrace();
        }

            try {
                for(int i=0;i<4;i++)
                   System.out.println(Integer.toBinaryString(file.read()));
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
