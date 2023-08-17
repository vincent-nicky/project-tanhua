package com.wsj.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "tanhua.huanxin")
public class HuanXinProperties {
    private String appkey;
    private String clientId;
    private String clientSecret;
}
