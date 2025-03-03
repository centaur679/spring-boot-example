package com.livk.autoconfigure.lock.redis;

import com.livk.autoconfigure.lock.constant.LockScope;
import com.livk.autoconfigure.lock.constant.LockType;
import com.livk.autoconfigure.lock.support.AbstractLockSupport;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * RedissonLock
 * </p>
 *
 * @author livk
 * @date 2022/9/5
 */
@RequiredArgsConstructor
public class RedissonLock extends AbstractLockSupport<RLock> {

    private final RedissonClient redissonClient;

    @Override
    protected RLock getLock(LockType type, String key) {
        return switch (type) {
            case LOCK -> redissonClient.getLock(key);
            case FAIR -> redissonClient.getFairLock(key);
            case READ -> redissonClient.getReadWriteLock(key).readLock();
            case WRITE -> redissonClient.getReadWriteLock(key).writeLock();
        };
    }


    @Override
    protected void unlock(String key, RLock lock) {
        if (lock != null && lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }


    @Override
    protected boolean tryLockAsync(RLock lock, long leaseTime, long waitTime) throws Exception {
        return lock.tryLockAsync(waitTime, leaseTime, TimeUnit.SECONDS).get();
    }

    @Override
    protected boolean tryLock(RLock lock, long leaseTime, long waitTime) throws Exception {
        return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
    }

    @Override
    protected void lockAsync(RLock lock) throws Exception {
        lock.lockAsync().get();
    }

    @Override
    protected void lock(RLock lock) {
        lock.lock();
    }

    @Override
    public LockScope scope() {
        return LockScope.DISTRIBUTED_LOCK;
    }
}
