package atlan.ceer.controller;

import atlan.ceer.config.MyLogger;
import atlan.ceer.model.MyResult;
import atlan.ceer.util.TokenUtil;
import com.qiniu.common.Region;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class QiNiuYunController {
    @Autowired
    private TokenUtil tokenUtil;
    private String accessKey = "a12YWzAirPDlnESTsaXFbRGftCoqEeFR4YzLpW04";
    private String secretKey = "jcURdM7kDpAZeZ7DvaVTL1g8JBuykwCKSyubFFzP";
    private String bucket = "fsuse";

    @RequestMapping("/qiniu")
    public MyResult getToken(HttpServletResponse response, HttpServletRequest request){
        try {
            String token=request.getHeader("token");
            if (!tokenUtil.verifyToken(token)){
                return new MyResult(false,"非法访问","300");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new MyResult(false,"非法访问","300");
        }
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        response.setHeader("upToken",upToken);
        MyLogger.logger.info(upToken);
        return new MyResult(true,"成功","200");
    }
    public void test(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
    }
}
