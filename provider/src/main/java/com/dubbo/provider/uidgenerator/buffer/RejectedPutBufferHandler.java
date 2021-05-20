package com.dubbo.provider.uidgenerator.buffer;

@FunctionalInterface
public interface RejectedPutBufferHandler {

    void rejectPutBuffer(RingBuffer ringBuffer, long uid);

}
