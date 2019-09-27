package atlan.ceer.controller;

import atlan.ceer.model.MyResult;
import com.google.gson.JsonObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/domain")
public class DomainNameController {
    private String url="http://www.nazhumi.com/list";
    private String register="/new?keyword=";
    private String renew="/renew?keyword=";
    private String transfer="/transfer?keyword=";
    private String currentPage="&page=";

    private Connection connection;
    private Connection.Response response;
    private Document document;

    @RequestMapping("/list")
    public MyResult getDomainNameList(String key, int page, int type){
        //创建连接
        switch (type){
            case 1:
                connection = Jsoup.connect(url+register+key+currentPage+page);
                break;
            case 2:
                connection = Jsoup.connect(url+renew+key+currentPage+page);
                break;
            case 3:
                connection = Jsoup.connect(url+transfer+key+currentPage+page);
                break;
            default:
                return new MyResult(false,"非法访问",202);
        }

        try {
            //得到response 响应
            response=connection.execute();
            //得到网页body代码
            document=Jsoup.parse(response.body());
            //提取出表格里面的每一行
            Elements thisPage=document.select("tbody").select("tr");
            int thisPageCount=thisPage.size();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

