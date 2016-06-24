package xupt.se.web;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import xupt.se.config.Config;
import xupt.se.util.ResponseJson;
import xupt.se.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lc on 2016/6/16.
 */
public class FileUploadServlet extends HttpServlet {
    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 5 * 1024 * 1024; //5M
    private int maxMemSize = 4 * 1024;
    private File file;

    public void init() {
        // 获取文件将被存储的位置
        filePath = this.getServletContext().getRealPath(Config.FileUploadPath);
        //如果上传目录不存在， 应该创建
//        File uploadPath = new File( filePath);
//        if (!uploadPath.exists()){
//            uploadPath.mkdir();
//        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
        throws ServletException, java.io.IOException {
        // 检查我们有一个文件上传请求
        isMultipart = ServletFileUpload.isMultipartContent(request);

        java.io.PrintWriter out = response.getWriter();
        if (!isMultipart) {
            out.println(ResponseJson.toJSONString(ResponseJson.Error, "no file!", ""));
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 文件大小的最大值将被存储在内存中
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        System.out.println("System tmpdir >" + System.getProperty("java.io.tmpdir"));
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        // 创建一个新的文件上传处理程序
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        // 允许上传的文件大小的最大值
        upload.setSizeMax(maxFileSize);

        try {
            // 解析请求，获取文件项
            List fileItems = upload.parseRequest(request);
            // 处理上传的文件项
            Iterator i = fileItems.iterator();

            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (!fi.isFormField()) {
                    // 获取上传文件的参数
                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();
//                  取随机文件名
                    fileName = StringUtils.getRandFilaName() + fileName.substring(fileName.lastIndexOf('.'));
                    System.out.println("FileUpload > fileName " + fileName + "  fieldName " + fieldName);
                    // 写入文件
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(filePath +
                                            fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath +
                                            fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fi.write(file);
//                    "Uploaded Filename: " + filePath + fileName + "<br>"
                    out.println(ResponseJson.toJSONString(ResponseJson.Success,"upload/"+fileName,""));
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, java.io.IOException {

        throw new ServletException("GET method used with " +
                                       getClass().getName() + ": POST method required.");
    }
}
