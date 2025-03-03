package com.livk.local.lock;

import com.livk.spring.LivkSpring;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * LocalLockExample
 * </p>
 *
 * @author livk
 * @date 2022/9/29
 */
@SpringBootApplication
public class LocalLockExample {
    public static void main(String[] args) {
        LivkSpring.run(LocalLockExample.class, args);
    }
}
