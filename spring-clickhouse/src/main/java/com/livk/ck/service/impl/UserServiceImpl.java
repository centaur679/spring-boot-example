package com.livk.ck.service.impl;

import com.livk.ck.entity.User;
import com.livk.ck.mapper.UserMapper;
import com.livk.ck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * UserServiceImpl
 * </p>
 *
 * @author livk
 * @date 2022/11/9
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.selectList();
    }

    @Override
    public boolean remove(String regTime) {
        return userMapper.delete(regTime) > 0;
    }

    @Override
    public boolean save(User user) {
        return userMapper.insert(user) > 0;
    }
}
