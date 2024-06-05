package zhang.jie.api.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zhang.jie.api.demos.util.WebState;
import zhang.jie.api.domain.CoverListData;

import java.io.IOError;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Controller
public class PostValue {

    @Autowired
    zhang.jie.api.service.loginService loginService;

    public WebState createValue(int a,String content){
        if (a == 1){
            WebState webState = new WebState();
            webState.setState(1);
            webState.setMessage("welcome");
            webState.setSuccess(true);
            webState.setContent(content);

            return webState;
        }
        return new WebState("登录失败",false,-1,"");
    }

//登录验证
    @RequestMapping("/signfor")
    @ResponseBody
    public String get(@RequestBody Map<String, String> pram){

//       通过map来拿到前端的post请求
        String ph = pram.get("phone") , pw = pram.get("password"),role = pram.get("role");

        boolean login = loginService.login(ph, pw,role);

        if (login){
            return createValue(1,createHash(ph,pw)).toString();
        }{
            return createValue(-1,"").toString();
        }

/*
        if (Objects.equals(pram.get("phone"), "18783174270") & Objects.equals(pram.get("password"), "123456")){

//            System.out.println(createValue(1,createHash("18783174270","123456")).toString());


            return createValue(1,createHash("18783174270","123456")).toString();
        }

        return createValue(-1,"").toString();*/

    }


    public String createHash(String text1,String text2){

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // 将两个字符串合并并获取其字节
            byte[] encodedhash = digest.digest((text1 + text2).getBytes());
            // 将哈希值转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }catch (IOError | NoSuchAlgorithmException ignored){

        }
        return "";
    }



//员工的头像，名字
    @PostMapping("/front/user/getinfo")
    @ResponseBody
    public CoverListData getinfo(@RequestBody Map<String,String> pram){

        CoverListData coverListData = new CoverListData();

        String ph = pram.get("phone") , pw = pram.get("password"),role = pram.get("role");

/*
        System.out.println(ph+" "+pw+ " "+role);
        UserInfo userInfo = new UserInfo();
        userInfo.setName("jack");
        userInfo.setPortrait("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
        System.out.println(userInfo);
        */


       coverListData.setData(loginService.getLoginInfo(ph, pw, role));
        return  coverListData;
    }







    @RequestMapping("/front/user/logout")
    @ResponseBody
    public String logOut(@RequestBody Map<String,String> parm){

        System.out.println("退出收到Post请求");

        return "{" +
                "\"content\":" +
                "\"login-Out\"" +
                "}";
    }





}
