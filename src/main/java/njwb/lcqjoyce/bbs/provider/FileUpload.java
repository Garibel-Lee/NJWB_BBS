package njwb.lcqjoyce.bbs.provider;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUpload {

    public static String saveFile(MultipartFile file, String saveUrl){
        //获得文件保存目录
        String originName=file.getOriginalFilename();
        String fileName = UUID.randomUUID()+originName.substring(originName.lastIndexOf("."));;
        String dir = PathUtil.getProjectPath() + saveUrl;
        Path path = Paths.get(dir,fileName);
        try {
            //如果没有此目录，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(dir));
            }
            //验证此路径下是否存在相同文件内
            if(!path.toFile().exists()) {
                Files.copy(file.getInputStream(),path);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return fileName;
    }

    public static boolean deleteFile(String saveUrl){
        String path = PathUtil.getProjectPath() + saveUrl;
        File file = new File(path);
        if(file.exists()){
            if(file.delete()){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return true;
        }
    }
}
