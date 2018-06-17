package io.jmerta.tau.config;

import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.flywaydb.core.Flyway;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataConfig {


    @Primary
    @Bean(name="dataSource")
    public DataSource dataSource() throws ClassNotFoundException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass((Class<? extends Driver>) Class.forName("org.postgresql.Driver"));
        dataSource.setUrl(String.format("jdbc:postgresql://%s:%s/%s", "localhost", "5432", "tau"));
        dataSource.setUsername("postgres");
        dataSource.setPassword("12345");
        return dataSource;
    }



//    @Autowired
//    @Bean(name="sqlSessionFactoryBean")
//    public SqlSessionFactoryBean getSqlSessionFactoryBean(@Qualifier("dataSource") DataSource dataSource) throws IOException {
//        return this.getPreparedSqlSessionBean("classpath:db/mapper/**/*.xml", dataSource);
//    }
//
//    private SqlSessionFactoryBean getPreparedSqlSessionBean(String resource, DataSource dataSource) throws IOException {
//        List<Resource> resources = new ArrayList<>();
//
//        org.apache.ibatis.logging.LogFactory.useLog4JLogging();
//        Resource[] mappers = new PathMatchingResourcePatternResolver().getResources(resource);
//        resources.addAll(Arrays.asList(mappers));
//
//
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(resources.toArray(new Resource[resources.size()]));
//
//        LocalDateTimeTypeHandler localDateTimeTypeHandler = new LocalDateTimeTypeHandler();
//        LocalDateTimeTypeHandler[] handlers = {localDateTimeTypeHandler};
//        bean.setTypeHandlers(handlers);
//
//        return bean;
//    }

    @Autowired
    @Bean(name="flyway")
    public Flyway getFlywayBean(@Qualifier("dataSource") DataSource dataSource, @Value("${application.db.schema}") String schema) {
        Flyway bean = new Flyway();
        bean.setDataSource(dataSource);
        bean.setSchemas(schema);
        bean.setOutOfOrder(true);
        bean.migrate();

        return bean;
    }

//
//    @Bean
//    public MapperScannerConfigurer getMapperScannerConfigurer() {
//        MapperScannerConfigurer bean = new MapperScannerConfigurer();
//        bean.setBasePackage("io.jmerta.tau.repository");
//        bean.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
//
//        return bean;
//    }

}
