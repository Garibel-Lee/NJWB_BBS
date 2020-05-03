package njwb.lcqjoyce.bbs.provider;

import njwb.lcqjoyce.bbs.dto.AccessTokenDTO;
import  njwb.lcqjoyce.bbs.dto.GithubUserDTO;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token=string.split("&")[0].split("=")[1];
            System.out.println("github返回值"+string);
            System.out.println("截取的accesstoken"+token);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //获取email 邮箱设置  登录信息插入
    public GithubUserDTO gerUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //得到的是json字符串，因此需要转为GithubUser对象
            String string = response.body().string();
            System.out.println(string);
            return JSON.parseObject(string, GithubUserDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
