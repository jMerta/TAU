package io.jmerta.tau.repository;

import io.jmerta.tau.TauApplication;
import io.jmerta.tau.config.DataConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MybatisTest(excludeAutoConfiguration = {AutoConfigureTestDatabase.class, SpringBootTest.class})
@SpringBootTest(classes = {TauApplication.class, DataConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepository {


    @Autowired
    RoleRepository roleRepository;


    @Test
    public void CheckIfAllRolesExist(){

    }
}
