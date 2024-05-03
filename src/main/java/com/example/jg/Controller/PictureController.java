package com.example.jg.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.jg.Enum.TypeMap;
import com.example.jg.Pojo.Picture;
import com.example.jg.Pojo.User;
import com.example.jg.Pojo.WorkSheet;
import com.example.jg.Service.PictureService;
import com.example.jg.Service.UserService;
import com.example.jg.Service.WorkSheetService;
import com.example.jg.Utils.DeviceUtil;
import com.example.jg.Utils.ZipUtil;
import com.example.jg.common.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
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

    @Autowired
    RedisTemplate<Object, User> template;

    @Autowired
    WorkSheetService workSheetService;

    @Autowired
    RedisTemplate<String, Integer> redisTemplate;

    @Value("${JG.originalBasePath}")
    private String originalBasePath;

    @Value("${JG.analyzedBasePath}")
    private String analyzedBasePath;

    @Value("${JG.repairedBasePath}")
    private String repairedBasePath;

    @Value("${JG.originalHttpPath}")
    private String originalHttpPath;

    @Value("${JG.analyzedHttpPath}")
    private String analyzedHttpPath;

    @Value("${JG.repairedHttpPath}")
    private String repairedHttpPath;

    @ApiOperation("上传图片")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file, HttpServletRequest request, String description, String instrument, String picId){
        log.info(file.getName());
        log.info(picId+"********");
        Integer pictureNumId = redisTemplate.opsForValue().get("pictureNumId");
        String formatNum = String.format("%04d", pictureNumId);
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String uuid = UUID.randomUUID().toString();
        String fileName = "井盖" + formatNum + suffix;
        redisTemplate.opsForValue().increment("pictureNumId");
        String device;
        if (picId.equals("")){
            long size = file.getSize()/1024;
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            if (DeviceUtil.isMobileDevice(String.valueOf(request.getHeaderNames()))){
                device = "移动端";
            }else {
                device = "PC端";
            }
            log.info(device);
            Picture picture = new Picture();
            picture.setId(fileName);
            picture.setSize("图像大小："+ size +"kb;"+"图片宽度："+ width + "px;"+"图片高度："+ height + "px;");
            String oriPath = originalHttpPath + fileName;
            log.info(oriPath);
            picture.setOriginalUrl(oriPath);
            picture.setAnalyzedUrl(analyzedHttpPath+"1.png");
            picture.setUploadType(device);
            if (device.equals("移动端")){
                picture.setInstrumentType("移动端摄像头");
            } else {
                picture.setInstrumentType(instrument);
            }

            HttpSession session = request.getSession();
            String userid = String.valueOf(session.getAttribute("user"));
            log.info(userid);
            User user;
            if (userid.equals("null")){
                String token = request.getHeader("Authorization");
                log.info(token);
                user = template.opsForValue().get(token);
                log.info(user.toString());
            }else {
                user = userService.getById(userid);
            }

            String userinfo = user.getJob() + ":" + user.getNickName() + "; 电话:" + user.getPhone();
            picture.setUserInfo(userinfo);

            picture.setDescription(description);

            if(!pictureService.save(picture)){
                return Result.error("上传失败");
            }
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
        }else{
            Picture pic = pictureService.getById(picId);
            pic.setRepairedUrl(repairedHttpPath + fileName);
            pictureService.updateById(pic);
            try {
                log.info(repairedBasePath+fileName);
                file.transferTo(new File(repairedBasePath+fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return Result.success(fileName);
        }

    }

    @ApiOperation("文件下载")
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

    @ApiOperation("缺陷检测")
    @PostMapping("/analyze/{id}")
    public Result<String[]> analyzed(@PathVariable String id){
        Picture picture = pictureService.getById(id);
        String[] strings = new String[3];
        strings[0] = "井盖缺失";
        HashMap<String, String> map = TypeMap.map;
        String s = map.get(strings[0]);
        strings[1] = analyzedHttpPath + "2.png";
        strings[2] = TypeMap.map.get(strings[0]);



        picture.setAnalyzedUrl(strings[1]);
        picture.setDefectType(strings[0]);
        picture.setRiskLevel(strings[2]);
        if (!pictureService.updateById(picture)){
            return Result.error("出现错误");
        }
        return Result.success(strings);
    }

    @ApiOperation("图片分页查询")
    @GetMapping("/page")
    public Result<Page<Picture>> page(Integer page, Integer pageSize, String type){
        log.info("page = {}, pageSize = {}",page,pageSize);
        Page<Picture> pageInfo = new Page<Picture>(page,pageSize);

        LambdaQueryWrapper<Picture> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(type), Picture::getDefectType, type);
        queryWrapper.orderByDesc(Picture::getUpdateTime);

        pictureService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }

    @ApiOperation("查看对应工单")
    @GetMapping("/workSheet/{id}")
    public Result<WorkSheet> workSheet(@PathVariable String id){
        LambdaQueryWrapper<WorkSheet> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WorkSheet::getPicId,id);
        WorkSheet sheet = workSheetService.getOne(queryWrapper);
        return Result.success(sheet);
    }



}

