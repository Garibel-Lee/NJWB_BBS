
package njwb.lcqjoyce.bbs.interceptor;

import njwb.lcqjoyce.bbs.provider.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
//@EnableWebMvc  静态文件被拦截如果打开
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.uploadFolder}")
    private String uploadFolder;



    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/**")
              .addResourceLocations("file:" + PathUtil.getProjectPath() + uploadFolder)
              .addResourceLocations("classpath:/static/");
    }
}
