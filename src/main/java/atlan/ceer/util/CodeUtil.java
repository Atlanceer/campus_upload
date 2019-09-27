package atlan.ceer.util;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component("codeUtil")
public class CodeUtil {
    private char[] createId= {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            '1','2','3','4','5','6','7','8','9','0'};
    private char[] messageId={'1','2','3','4','5','6','7','8','9','0'};
    private char[] createUserName={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            '_','-'};

    //获取16为随机数
    public String getCode(){
        //StringBuffer id=new StringBuffer();
        StringBuilder id=new StringBuilder();
        Random random=new Random();
        for(int i=0;i<16;i++) {
            int position=random.nextInt(36);
            id.append(createId[position]);
        }
        return id.toString();
    }

    //获取随机用户名
    public String getUserName(){
        //StringBuffer id=new StringBuffer();
        StringBuilder id=new StringBuilder();
        Random random=new Random();
        for(int i=0;i<10;i++) {
            int position=random.nextInt(28);
            id.append(createId[position]);
        }
        return id.toString();
    }

    //UUID获取32位随机数
    public String getUUID(){
        UUID id=UUID.randomUUID();
        return id.toString().replace("-","");
    }

    //获取短信验证码
    public String getMessageCode(){
        //StringBuffer code=new StringBuffer();
        StringBuilder code=new StringBuilder();
        Random random=new Random();
        for (int i=0;i<6;i++){
            int position=random.nextInt(10);
            code.append(messageId[position]);
        }
        return code.toString();
    }
}
