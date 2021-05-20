package com.dubbo.provider.uidgenerator.buffer;

import java.util.List;

@FunctionalInterface
public interface BufferedUidProvider {

    List<Long> provide(long momentInSecond);

}
