package test;

import com.google.gson.Gson;

/**
 * Created by lc on 2016/6/15.
 */
public class GsonTest {

    public static void main(String args[]){
        Gson json = new Gson();
        String js = json.toJson(new U(1,2));
        System.out.println(new U(1,2));
        System.out.println(js);
    }
}

class U{
    public transient int a;
    public int b;
    public U(int a, int b){
        this.a = a;
        this.b = b;
    }

    public String toString(){
        return "U ->  a: "+a+", b: "+b;
    }
}