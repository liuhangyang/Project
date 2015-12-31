package ystruct.com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by yang on 15-12-29.
 */
public class test5 {
    public static void main(String[] args) {
        try {
            FileInputStream in = new FileInputStream("222.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
