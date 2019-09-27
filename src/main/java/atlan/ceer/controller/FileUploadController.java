package atlan.ceer.controller;

import atlan.ceer.config.MyLogger;
import atlan.ceer.model.ImageUrl;
import atlan.ceer.model.MyResult;
import atlan.ceer.util.CodeUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/upload")
public class FileUploadController {
    @Value("${imagePath}")
    private String imagePath;
    @Autowired
    private CodeUtil codeUtil;
    @RequestMapping("/images")
    public MyResult uploadImage(MultipartFile image , String first){
        double zoom=1;
        double quality=0.4;
        try {
            BufferedImage bufferedImage=javax.imageio.ImageIO.read(image.getInputStream());
            /*System.out.println("图片宽度"+bufferedImage.getWidth());
            System.out.println("图片长度"+bufferedImage.getHeight());
            System.out.println("文件上传。。。。。"+image.getSize()+"====="+image.getBytes().length);*/
            MyLogger.logger.info("图片宽度"+bufferedImage.getWidth());
            MyLogger.logger.info("图片长度"+bufferedImage.getHeight());
            MyLogger.logger.info("文件上传。。。。。"+image.getSize()+"====="+image.getBytes().length);
            //如果图片小于50k就不压缩
            MyLogger.logger.info("开始上传===========》》》》》"+"size:"+image.getSize());
            if (image.getSize()<=51200){
                //zoom=1;
                quality=1;
            }else {
                //如果图片高度大于宽度
                if (bufferedImage.getHeight()>bufferedImage.getWidth()){
                    //System.out.println("长的");
                    MyLogger.logger.info("长图片");
                    zoom=1024.0/bufferedImage.getWidth();
                    //quality=0.3;
                    //System.out.println("缩放比例"+zoom);
                    //MyLogger.logger.info("缩放比例"+zoom);
                }else {
                    //System.out.println("宽的");
                    MyLogger.logger.info("宽图片");
                    zoom=1024.0/bufferedImage.getWidth();
                    //quality=0.3;
                    //System.out.println("缩放比例"+zoom);
                }
                //quality=5120.0/image.getSize();
                /*zoom=0.85;
                quality=0.25;
                System.out.println(quality);*/
            }
            String dayPath=new SimpleDateFormat("/yyyy/MM/dd").format(new Date(System.currentTimeMillis()));
            File file=new File(imagePath+dayPath);
            //不存在就创建文件夹
            if (!file.exists()){
                file.mkdirs();
            }
            String originalFileName=image.getOriginalFilename();
            String fileName=codeUtil.getUUID()+originalFileName.substring(originalFileName.lastIndexOf("."));
            ImageUrl imageUrl=new ImageUrl();
            imageUrl.setImage("/images"+dayPath+"/"+fileName);
            //判断是否裁剪图片
            if (first.equals("true")){
                String fileNameCut=codeUtil.getUUID()+originalFileName.substring(originalFileName.lastIndexOf("."));
                int length=300;
                //BufferedImage bufferedImage=javax.imageio.ImageIO.read(image.getInputStream());
                if(bufferedImage.getWidth()>bufferedImage.getHeight()){
                    length=bufferedImage.getHeight();
                }else {
                    length=bufferedImage.getWidth();
                }
                MyLogger.logger.info("图片上传压缩裁剪。。。。。");
                //图片压缩
                Thumbnails.of(image.getInputStream()).sourceRegion(Positions.CENTER,length,length).size(500,500).toFile(new File(imagePath+dayPath,fileNameCut));
                imageUrl.setImageCut("/images"+dayPath+"/"+fileNameCut);
            }
            //图片压缩
            Thumbnails.of(image.getInputStream()).scale(zoom).outputQuality(quality).toFile(new File(imagePath+dayPath,fileName));
            //image.transferTo(new File(imagePath+dayPath,fileName));
            MyLogger.logger.info("上传成功。。。"+imageUrl.toString());
            return new MyResult(imageUrl,true,"上传成功",200);
        } catch (Exception e) {
            e.printStackTrace();
            return new MyResult(false,"上传失败",201);
        }
    }
}
