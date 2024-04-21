package com.ankhnotes;

import com.ankhnotes.mapper.TagMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = com.ankhnotes.BlogAdminApplication.class)
public class TagMapperTest {

    @Autowired
    TagMapper tagMapper;

    @Test
    public void myUpdateByIdTest(){
        tagMapper.myUpdateById(1L, 1);
    }
}
