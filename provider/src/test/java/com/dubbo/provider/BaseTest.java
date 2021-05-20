package com.dubbo.provider;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//由于工程里引入了websocket技术，ServerEndpointExporter 这类需要依赖tomcat，所以这里需要单独指定web环境，不然就无法正常启动测试类
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {

}
