package njwb.lcqjoyce.bbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BbsApplication {

    public static void main(String[] args) {
        String proxyHost = "127.0.0.1";
        String proxyPort = "1080";
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", proxyPort);
        // 对https也开启代理
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", proxyPort);
        SpringApplication.run(BbsApplication.class, args);

    }

}
