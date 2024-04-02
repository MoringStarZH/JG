package com.example.gfjc.Controller;

import com.example.gfjc.Pojo.Picture;
import com.example.gfjc.Pojo.User;
import com.example.gfjc.Service.PictureService;
import com.example.gfjc.Service.UserService;
import com.example.gfjc.Utils.ZipUtil;
import com.example.gfjc.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @title PictureController
 * @Author: ZKY
 * @CreateTime: 2024-04-01  19:13
 * @Description: TODO
 */
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/picture")
public class PictureController {
    @Autowired
    PictureService pictureService;

    @Autowired
    UserService userService;

    @Value("${GFJC.originalBasePath}")
    private String originalBasePath;

    @Value("${GFJC.analyzedBasePath}")
    private String analyzedBasePath;

    @Value("${GFJC.originalHttpPath}")
    private String originalHttpPath;

    @Value("${GFJC.analyzedHttpPath}")
    private String analyzedHttpPath;

    @ApiOperation("上传图片")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file, HttpSession session, String description){
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();

        //计算文件相关信息
        long size = file.getSize()/1024;
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        String fileName = uuid + suffix;


        Picture picture = new Picture();
        picture.setId(fileName);
        picture.setSize("图像大小："+ size +"kb;"+"图片宽度："+ width + "px;"+"图片高度："+ height + "px;");
        String oriPath = originalHttpPath + fileName;
        log.info(oriPath);
        picture.setOriginalUrl(oriPath);
        picture.setAnalyzedUrl(analyzedHttpPath+"1.png");

//        picture.setUserInfo(String.valueOf(session.getAttribute("user")));
        String userid = String.valueOf(session.getAttribute("user"));
        User user = userService.getById(userid);
        String userinfo = user.getJob() + ":" + user.getNickName() + "; 电话:" + user.getPhone();
        picture.setUserInfo(userinfo);

        picture.setDescription(description);

        if(!pictureService.save(picture)){
            return Result.error("上传失败");
        };

        File dir = new File(originalBasePath);
        if (!dir.exists()){
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(originalBasePath+fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Result.success(fileName);
    }

//    @ApiOperation("图片下载")
//    @GetMapping("/download/{id}")
//    public void download(@PathVariable String id, HttpServletResponse response){
//
//        try {
//            Picture pic = pictureService.getById(id);
//            FileInputStream fileInputStream = new FileInputStream(new File(pic.getOriginalUrl()));
//            ServletOutputStream outputStream = response.getOutputStream();
//
//            response.setContentType("image/jpeg");
//
//            int len = 0;
//            byte[] bytes = new byte[1024];
//            while((len = fileInputStream.read(bytes)) != -1){
//                outputStream.write(bytes,0,len);
//                outputStream.flush();
//            }
//            outputStream.close();
//            fileInputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @ApiOperation("图片下载")
    @GetMapping("/download/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws IOException {
        Picture picture = pictureService.getById(id);
        String temp = picture.getAnalyzedUrl();

        String zipFileName = "archive.zip";
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + zipFileName + "\"");

        // 创建ZIP输出流，并直接写入到HTTP响应的输出流中
        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            // 假设我们有两个文件要打包：file1.txt 和 file2.txt
//            String[] fileNames = {"file1.txt", "file2.txt"};
//            String filePathPrefix = "/path/to/files/"; // 文件所在目录的前缀
            ArrayList<String> urls = new ArrayList<>();
            urls.add(originalBasePath+id);
            urls.add(analyzedBasePath+temp.substring(temp.lastIndexOf('/')));

            for (String url : urls) {
                log.info(url);
                File file = new File(url);
                if (file.exists()) {
                    ZipUtil.addToZip(zos, file, file.getName());
                } else {
                    // 处理文件不存在的情况
                    throw new IOException("File not found: " + file.getAbsolutePath());
                }
            }
        }
    }


}

