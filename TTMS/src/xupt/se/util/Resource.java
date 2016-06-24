package xupt.se.util;

import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.JinjavaConfig;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.loader.ResourceLocator;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Resource implements ResourceLocator {
    private JinjavaInterpreter interpreter;
    private String tplPath;
    static final Properties PROPERTIES = new Properties(System.getProperties());

    public Resource(String path) {
        tplPath = path;
        interpreter = new Jinjava().newInterpreter();
    }

    public static String getHtmlstr(String path) {
        StringBuffer res = new StringBuffer();
        String temp;
        BufferedReader fread;
        try {
            fread = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));
            while ((temp = fread.readLine()) != null) {
                res.append(temp);
                res.append("\n");
            }
            fread.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res.toString();
    }

    public static String renderTpl(String tplPath, String tpl, Map<String, Object> context) {
        if (!tplPath.endsWith(Resource.getPathSeparator()))
            tplPath += Resource.getPathSeparator();
        JinjavaConfig config = new JinjavaConfig();
        Jinjava jinjava = new Jinjava(config);
        //注册过滤器，简单加减
        jinjava.getGlobalContext().registerFilter(new MathOPFilter());
        jinjava.setResourceLocator(new Resource(tplPath));
        //Jinjava jinjava = new Jinjava();
        String template = Resource.getHtmlstr(tplPath + tpl);

        return jinjava.render(template, context);
    }


    public String getString(String s, Charset charset, JinjavaInterpreter jinjavaInterpreter) throws IOException {
//        System.out.println("TP Path: >>" + tplPath + s);
        List<String> lines = Files.readAllLines(Paths.get(tplPath + s), StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line);
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * 获取系统路径分隔符
     *
     * @return
     */
    public static String getPathSeparator() {
        return File.separator;
    }
}
