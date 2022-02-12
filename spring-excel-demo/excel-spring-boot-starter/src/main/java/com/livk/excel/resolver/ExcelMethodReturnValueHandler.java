package com.livk.excel.resolver;

import com.alibaba.excel.EasyExcel;
import com.livk.excel.annotation.ExcelReturn;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

/**
 * <p>
 * ExcelMethodResolver
 * </p>
 *
 * @author livk
 * @date 2022/2/11
 */
public class ExcelMethodReturnValueHandler implements AsyncHandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.hasMethodAnnotation(ExcelReturn.class);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        ExcelReturn excelReturn = returnType.getMethodAnnotation(ExcelReturn.class);
        Assert.notNull(response, "response not be null");
        Assert.notNull(excelReturn, "excelReturn not be null");
        if (returnValue instanceof List) {
            ServletOutputStream outputStream = response.getOutputStream();
            String fileName = excelReturn.fileName();
            String contentType = MediaTypeFactory.getMediaType(fileName).map(MediaType::toString).orElse("application/vnd.ms-excel");
            response.setContentType(contentType);
            response.setCharacterEncoding("utf-8");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + excelReturn.suffix());
            EasyExcel.write(outputStream, excelReturn.dataClass())
                    .sheet()
                    .doWrite((List<?>) returnValue);
            outputStream.close();
        }
    }

    @Override
    public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
        return true;
    }
}
