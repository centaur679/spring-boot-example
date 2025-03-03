package com.livk.mail.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.StringReader;
import java.util.Map;

/**
 * <p>
 * FreemarkerUtils
 * </p>
 *
 * @author livk
 * @date 2022/8/11
 */
@Slf4j
@UtilityClass
public class FreemarkerUtils extends FreeMarkerTemplateUtils {

    private static final Configuration CONFIGURATION = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    private static final String TEMPLATE_NAME = "template";

    public String parse(String freemarker, Map<String, Object> model) {
        return parse(TEMPLATE_NAME, freemarker, model);
    }

    public String parse(String templateName, String freemarker, Map<String, Object> model) {
        try (StringReader reader = new StringReader(freemarker)) {
            Template template = new Template(templateName, reader, CONFIGURATION);
            return processTemplateIntoString(template, model);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
