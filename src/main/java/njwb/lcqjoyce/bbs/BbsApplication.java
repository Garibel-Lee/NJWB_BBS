package njwb.lcqjoyce.bbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author: Garibel.Lee
 * @ClassName: BbsApplication  
 * @Date: 2020/5/26 2:22 
 * @Description: TODO
 */
@SpringBootApplication
@EnableScheduling
public class BbsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BbsApplication.class, args);
    }

}
