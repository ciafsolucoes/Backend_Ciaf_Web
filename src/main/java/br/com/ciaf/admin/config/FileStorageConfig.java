package br.com.ciaf.admin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


@Configuration
@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {
//    private String upload_dir = "/var/www/html/images/";
    private String upload_dir = "/var/www/ciafonline.com.br/images/";

    public String getUploadDir() {
        return upload_dir;
    }
    public void setUploadDir(String uploadDir) {
        this.upload_dir = uploadDir;
    }
}
