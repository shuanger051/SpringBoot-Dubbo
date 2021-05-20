package com.dubbo.provider.uidgenerator;

import com.dubbo.provider.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BaiduUidGeneratorTest extends BaseTest {

    @Autowired
    private UidGenerator uidGenerator;

    @Test
    public void testUid() throws Exception{
        System.out.println(uidGenerator.getUID());
    }

}
