package com.howhich.fuchuang.demos.init;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.howhich.fuchuang.demos.entity.Base.Record;
import com.howhich.fuchuang.demos.mapper.RecordMapper;
import com.howhich.fuchuang.demos.service.RecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class FakeListener implements ApplicationListener<FakeEvent> {
    @Autowired
    private RecordMapper recordMapper;
    @Override
    public void onApplicationEvent(FakeEvent event) {
        Object msg = event.getSource();
        System.out.println("监听器1收到消息" + msg);
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("RESET");
//        Record record = recordMapper.selectOne(new LambdaQueryWrapper<Record>().eq(Record::getId, 9));
//        record.setRecordName("f");
        recordMapper.updateMy(9L);
        //        Record record = recordsService.getOne(new LambdaQueryWrapper<Record>()
//                .eq(Record::getId, 9));
//        Long id = 9L;
//        recordMapper.setJAD(id);
    }
}
