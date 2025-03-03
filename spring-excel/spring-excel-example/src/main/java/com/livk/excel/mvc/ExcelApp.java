package com.livk.excel.mvc;

import com.livk.spring.LivkSpring;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * ExcelApp
 * </p>
 *
 * @author livk
 * @date 2022/1/19
 */
@SpringBootApplication
public class ExcelApp {

    public static void main(String[] args) {
        LivkSpring.run(ExcelApp.class, args);
    }

}
