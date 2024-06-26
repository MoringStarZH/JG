package com.example.jg.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import com.example.jg.Utils.HttpClientUtil;
import com.example.jg.Utils.StringUtil;
import com.example.jg.Utils.ZipUtil;
import com.example.jg.common.Result;
import io.jsonwebtoken.lang.Strings;
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
import java.util.Map;
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
        String fileName = formatNum + suffix;
        redisTemplate.opsForValue().increment("pictureNumId");
        String device;
        if (picId==null){
            long size = file.getSize()/1024;
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(file.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            Picture picture = new Picture();
            picture.setId(fileName);
            picture.setSize("图像大小："+ size +"kb;"+"图片宽度："+ width + "px;"+"图片高度："+ height + "px;");
            String oriPath = originalHttpPath + fileName;
            log.info(oriPath);
            picture.setOriginalUrl(oriPath);
            picture.setAnalyzedUrl(analyzedHttpPath+"1.png");


            HttpSession session = request.getSession();
            String userid = String.valueOf(session.getAttribute("user"));
            log.info(userid);
            User user;
            if (userid.equals("null")){
                String token = request.getHeader("Authorization");
                log.info(token);
                user = template.opsForValue().get(token);
                picture.setInstrumentType("手机移动端小程序");
                log.info(user.toString());
            }else {
                picture.setInstrumentType("PC机");
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

//    @ApiOperation("上传图片(小程序)")
//    @PostMapping("/upload")
//    public Result<String> upload1(MultipartFile file, HttpServletRequest request, String description, String instrument, String picId){
//        String url = "http://47.109.204.138:8080/picture/upload";
//        Map<String, String> map = new HashMap<>();
//        String token = request.getHeader("Authorization");
//        map.put("description",description);
//        map.put("instrument",instrument);
//        map.put("picId",picId);
//        JSONObject jsonObject = JSON.parseObject(HttpClientUtil.doPostFormData(url, "file", file, map, token));
//        return Result.success(jsonObject.getString("data"));
//    }

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
        JSONObject jsonObject = JSON.parseObject(HttpClientUtil.doGet("http://47.109.204.138:5000/api/get_pic_info?source=/home/image/JG/original/" + id));
        String s1 = jsonObject.getString("label");
        log.info(s1);
        String defectType = StringUtil.parse(s1);
        log.info(defectType);
        HashMap<String, String> map = TypeMap.map;
        String riskLevel = map.get(defectType);
        String analyzedUrl = "http://47.109.204.138:8080/analyzedPicture/"+id;

        picture.setDefectType(defectType);
        picture.setRiskLevel(riskLevel);
        picture.setAnalyzedUrl(analyzedUrl);
        pictureService.updateById(picture);

        String[] strings = new String[3];
        strings[0] = defectType;
        strings[1] = analyzedUrl;
        strings[2] = riskLevel;
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

