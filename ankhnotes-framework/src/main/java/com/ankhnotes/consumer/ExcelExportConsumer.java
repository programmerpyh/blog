package com.ankhnotes.consumer;

import com.alibaba.excel.EasyExcel;
import com.ankhnotes.utils.WebUtils;
import com.ankhnotes.vo.ExcelCategoryVO;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RocketMQMessageListener(topic = "excel-export", consumerGroup = "consumer_group")
public class ExcelExportConsumer implements RocketMQListener<List<ExcelCategoryVO>> {
    @Override
    public void onMessage(List<ExcelCategoryVO> excelCategoryVOS) {
        String targetDir = System.getProperty("user.dir") + File.separator + "target";
        File directory = new File(targetDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String filePath = new File(directory, "excel-category-" + timestamp + ".xlsx").getAbsolutePath();

        EasyExcel.write(filePath, ExcelCategoryVO.class).autoCloseStream(Boolean.FALSE)
                .sheet("文章分类").doWrite(excelCategoryVOS);
    }
}
