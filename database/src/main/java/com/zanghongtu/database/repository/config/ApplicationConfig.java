package com.zanghongtu.database.repository.config;

import com.zanghongtu.database.repository.factory.BaseInLikeRepositoryFactoryBean;
import com.zanghongtu.database.repository.factory.BaseRepositoryFactoryBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午1:22 19-5-28
 */
@ComponentScan
@Configuration
@EnableJpaRepositories(basePackages = {"com.zanghongtu"},
        repositoryFactoryBeanClass = BaseInLikeRepositoryFactoryBean.class)
public class ApplicationConfig {
}
