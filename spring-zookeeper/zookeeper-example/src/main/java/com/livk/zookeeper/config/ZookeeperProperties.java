package com.livk.zookeeper.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * ZookeeperProperties
 * </p>
 *
 * @author livk
 * @date 2022/4/6
 */
@Data
@ConfigurationProperties(ZookeeperProperties.PREFIX)
public class ZookeeperProperties {

    public static final String PREFIX = "spring.zookeeper";

    /**
     * zk连接集群，多个用逗号隔开
     */
    private String servers = "localhost:2181";

    /**
     * 会话超时时间
     */
    private int sessionTimeout = 60000;

    /**
     * 连接超时时间
     */
    private int connectionTimeout = 15000;

    /**
     * 初始重试等待时间(毫秒)
     */
    private int baseSleepTime = 1000;

    /**
     * 重试最大次数
     */
    private int maxRetries = 10;

}
