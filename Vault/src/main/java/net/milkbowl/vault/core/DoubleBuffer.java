package net.milkbowl.vault.core;

import org.jetbrains.annotations.NotNull;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DoubleBuffer<T> {
    @NotNull
    private volatile Queue<T> currentBuffer = new ConcurrentLinkedQueue<>();

    @NotNull
    private volatile Queue<T> backBuffer = new ConcurrentLinkedQueue<>();

    @NotNull
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @NotNull
    public Queue<T> getReadBuffer() {
        lock.readLock().lock();
        try {
            return backBuffer;
        } finally {
            lock.readLock().unlock();
        }
    }

    @NotNull
    public Queue<T> getWriteBuffer() {
        lock.readLock().lock();
        try {
            return currentBuffer;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void swapBuffers() {
        lock.writeLock().lock();
        try {
            Queue<T> t = currentBuffer;
            currentBuffer = backBuffer;
            backBuffer = t;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
