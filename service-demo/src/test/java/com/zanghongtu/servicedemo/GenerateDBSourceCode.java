package com.zanghongtu.servicedemo;

import com.zanghongtu.database.Dao2POJO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateDBSourceCode {

    @Autowired
    private Dao2POJO dao2POJO;

    @Test
    public void initDB() {
        dao2POJO.generateCodes();
    }

}
