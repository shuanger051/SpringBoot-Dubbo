package com.dubbo.provider.uidgenerator;

import com.dubbo.provider.uidgenerator.impl.CachedUidGenerator;
import com.dubbo.provider.uidgenerator.worker.DisposableWorkerIdAssigner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IDGeneratorConfiguration {

    @Bean(name = "disposableWorkerIdAssigner")
    public DisposableWorkerIdAssigner disposableWorkerIdAssigner(){
        DisposableWorkerIdAssigner disposableWorkerIdAssigner = new DisposableWorkerIdAssigner();
        return disposableWorkerIdAssigner;
    }

    @Bean(name = "cachedUidGenerator")
    public UidGenerator cachedUidGenerator(DisposableWorkerIdAssigner disposableWorkerIdAssigner){
        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator();
        cachedUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
        return cachedUidGenerator;
    }

}
