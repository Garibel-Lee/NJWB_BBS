package njwb.lcqjoyce.bbs.provider;

import org.springframework.boot.system.ApplicationHome;

import java.io.File;

public class PathUtil {

    public static String getProjectPath(){
        ApplicationHome home = new ApplicationHome(PathUtil.class);
        File jarFile = home.getSource();
        return jarFile.getParentFile().toString();
    }
}
