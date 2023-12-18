package com.yagizeris.springsecurityjwtexample.common.config.modelMapper;

import com.yagizeris.springsecurityjwtexample.common.modelMapper.ModelMapperManager;
import com.yagizeris.springsecurityjwtexample.common.modelMapper.ModelMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    @Bean
    public ModelMapperService getModelMapperService(ModelMapper modelMapper){
        return new ModelMapperManager(modelMapper);
    }
}
