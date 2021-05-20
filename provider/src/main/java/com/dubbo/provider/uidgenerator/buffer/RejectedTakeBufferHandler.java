package com.dubbo.provider.uidgenerator.buffer;

@FunctionalInterface
public interface RejectedTakeBufferHandler {

    void rejectTakeBuffer(RingBuffer ringBuffer);

}
