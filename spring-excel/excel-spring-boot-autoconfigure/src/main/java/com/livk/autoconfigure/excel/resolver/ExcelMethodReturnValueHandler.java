package com.livk.autoconfigure.excel.resolver;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.livk.autoconfigure.excel.annotation.ExcelReturn;
import com.livk.autoconfigure.excel.exception.ExcelExportException;
import com.livk.util.AnnotationUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * <p>
 * ExcelMethodResolver
 * </p>
 *
 * @author livk
 * @date 2022/2/11
 */
public class ExcelMethodReturnValueHandler implements AsyncHandlerMethodReturnValueHandler {

    public static final String UTF8 = "UTF-8";

    @Override
    public boolean supportsReturnType(@NonNull MethodParameter returnType) {
        return AnnotationUtils.hasAnnotation(returnType, ExcelReturn.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, @NonNull MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) {
        mavContainer.setRequestHandled(true);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        ExcelReturn excelReturn = AnnotationUtils.getAnnotation(returnType, ExcelReturn.class);
        Assert.notNull(response, "response not be null");
        Assert.notNull(excelReturn, "excelReturn not be null");
        if (returnValue instanceof Collection) {
            Class<?> excelModelClass = ResolvableType.forMethodParameter(returnType).resolveGeneric(0);
            this.write(excelReturn, response, excelModelClass, Map.of("sheet", (Collection<?>) returnValue));
        } else if (returnValue instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Collection<?>> result = (Map<String, Collection<?>>) returnValue;
            Class<?> excelModelClass = ResolvableType.forMethodParameter(returnType).getGeneric(1).resolveGeneric(0);
            this.write(excelReturn, response, excelModelClass, result);
        } else {
            throw new ExcelExportException("the return class is not java.util.Collection or java.util.Map");
        }
    }

    public void write(ExcelReturn excelReturn, HttpServletResponse response, Class<?> excelModelClass, Map<String, Collection<?>> result) {
        this.setResponse(excelReturn, response);
        try (ServletOutputStream outputStream = response.getOutputStream();
             ExcelWriter writer = EasyExcel.write(outputStream, excelModelClass).build()) {
            for (Map.Entry<String, Collection<?>> entry : result.entrySet()) {
                WriteSheet sheet = EasyExcel.writerSheet(entry.getKey()).build();
                writer.write(entry.getValue(), sheet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setResponse(ExcelReturn excelReturn, HttpServletResponse response) {
        String fileName = excelReturn.fileName().concat(excelReturn.suffix().getName());
        String contentType = MediaTypeFactory.getMediaType(fileName).map(MediaType::toString)
                .orElse("application/vnd.ms-excel");
        response.setContentType(contentType);
        response.setCharacterEncoding(UTF8);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
    }

    @Override
    public boolean isAsyncReturnValue(Object returnValue, @NonNull MethodParameter returnType) {
        return AnnotationUtils.hasAnnotation(returnType, ExcelReturn.class);
    }

}
