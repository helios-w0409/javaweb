import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
    private static final String FILE_DIRECTORY = "D:/Javaweb测试文件/"; // 你的文件目录

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // 获取文件名
        String filename = request.getParameter("filename");
        if (filename == null || filename.isEmpty()) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("❌ 错误：文件名不能为空！");
            return;
        }

        // 调试：打印文件名和路径
        System.out.println("请求的文件名：" + filename);

        // 处理文件路径
        File file = new File(FILE_DIRECTORY + filename);
        System.out.println("文件路径：" + file.getAbsolutePath());

        if (!file.exists()) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("❌ 错误：文件不存在！路径：" + file.getAbsolutePath());
            return;
        }

        // 设置文件下载头部，避免乱码
        response.setContentType("application/octet-stream");
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+", "%20");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFilename + "\"");

        // 读取文件并写入响应
        response.setContentLengthLong(file.length());
        try (FileInputStream fis = new FileInputStream(file);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }
}
