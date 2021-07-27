package com.neko.seed.batch;

import com.neko.seed.traffic.service.CoreDataService;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ConsoleItemWriter<T> implements ItemWriter<T> {

    private CoreDataService coreDataServiceImpl;

    public ConsoleItemWriter() {
        super();
    }


    @Override
    public void write(List<? extends T> items) throws Exception {
        for (T item : items) {
            System.out.println(item);
        }
    }
}