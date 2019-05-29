package com.zanghongtu.servicedemo;

import com.zanghongtu.database.Dao2POJO;
import com.zanghongtu.servicedemo.testonly.model.Warning;
import com.zanghongtu.servicedemo.testonly.model.WarningRules;
import com.zanghongtu.servicedemo.testonly.repository.WarningRepository;
import com.zanghongtu.servicedemo.testonly.repository.WarningRulesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateDBSourceCode {

    @Autowired
    private Dao2POJO dao2POJO;

    @Test
    public void initDB() {
        dao2POJO.generateCodes();
    }

    @Autowired
    private WarningRulesRepository repository;

    @Test
    public void testCreate() {
        Map<String, String> conditions = new HashMap<>();
        conditions.put("tagName", "SWC");
        List<WarningRules> models = repository.listAll();
        System.out.println("***" +models.size() + "***" + models);
    }
}
