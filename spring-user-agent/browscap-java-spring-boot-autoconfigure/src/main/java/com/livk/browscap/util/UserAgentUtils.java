package com.livk.browscap.util;

import com.blueconic.browscap.Capabilities;
import com.blueconic.browscap.UserAgentParser;
import com.livk.support.SpringContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * <p>
 * UserAgentUtils
 * </p>
 *
 * @author livk
 * @date 2022/9/30
 */
@UtilityClass
public class UserAgentUtils {

    public static final String USER_AGENT = "user-agent";

    private static final UserAgentParser PARSER;

    static {
        PARSER = SpringContextHolder.getBean(UserAgentParser.class);
    }

    public Capabilities parse(String userAgent) {
        return PARSER.parse(userAgent);
    }

    public Capabilities parse(HttpServletRequest request) {
        String userAgent = request.getHeader(UserAgentUtils.USER_AGENT);
        return parse(userAgent);
    }

    public Capabilities parse(ServerHttpRequest request) {
        String userAgent = request.getHeaders().getFirst(UserAgentUtils.USER_AGENT);
        return parse(userAgent);
    }
}
