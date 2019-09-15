package org.net5ijy.springcloud.admindemo.user.configuration;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import java.io.IOException;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * MyBatisConfiguration 类
 *
 * @author xuguofeng
 * @date 2019/5/29 9:40
 */
@Configuration
public class MyBatisConfiguration {

  @Bean
  @Resource
  @ConditionalOnMissingBean
  public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource)
      throws IOException {

    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

    // 设置数据源
    sqlSessionFactoryBean.setDataSource(dataSource);

    // 添加分页插件
    sqlSessionFactoryBean.setPlugins(new Interceptor[]{
        new PaginationInterceptor()
    });

    // 设置别名包
    sqlSessionFactoryBean.setTypeAliasesPackage("org.net5ijy.springcloud.admindemo.common.entity");

    // 设置mapper映射文件所在的包
    PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
    String packageSearchPath = "classpath*:mapper/**.xml";
    sqlSessionFactoryBean
        .setMapperLocations(pathMatchingResourcePatternResolver
            .getResources(packageSearchPath));

    return sqlSessionFactoryBean;
  }
}
