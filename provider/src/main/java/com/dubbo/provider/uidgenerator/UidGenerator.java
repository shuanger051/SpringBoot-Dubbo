package com.dubbo.provider.uidgenerator;

import com.dubbo.provider.uidgenerator.exception.UidGenerateException;

public interface UidGenerator {

    long getUID() throws UidGenerateException;

    /**
     * Parse the UID into elements which are used to generate the UID. <br>
     * Such as timestamp & workerId & sequence...
     *
     * @param uid
     * @return Parsed info
     */
    String parseUID(long uid);

}
