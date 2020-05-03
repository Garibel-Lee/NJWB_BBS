package njwb.lcqjoyce.bbs.provider;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class PathUtil {

    public static String getProjectPath() {
        try {
            ApplicationHome home = new ApplicationHome(PathUtil.class);
            File jarFile = home.getSource();
            return jarFile.getParentFile().toString();
        }catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }

    }

}
