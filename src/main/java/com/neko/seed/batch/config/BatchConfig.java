package com.neko.seed.batch.config;

import com.neko.seed.batch.ConsoleItemWriter;
import com.neko.seed.traffic.entity.CoreData;
import com.neko.seed.traffic.service.CoreDataService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CoreDataService coreDataServiceImpl;

    @Bean
    public Job readCSVFilesJob() {
        return jobBuilderFactory
                .get("readCSVFilesJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<CoreData, CoreData>chunk(5)
                .reader(reader())
                .writer(writer2())
                .build();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public FlatFileItemReader<CoreData> reader() {
        //Create reader instance
        FlatFileItemReader<CoreData> reader = new FlatFileItemReader<CoreData>();

        //Set input file location
        reader.setResource(new FileSystemResource("input/inputdata.csv"));

        //Set number of lines to skips. Use it if file has header rows.
        reader.setLinesToSkip(1);

        //Configure how each line will be parsed and mapped to different values
        reader.setLineMapper(new DefaultLineMapper() {
            {
                //3 columns in each row
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[]{"roadSectionName", "formatedTime", "numsBlueCar", "numsYellCar", "total",
                                "avgSpeed", "cartMixRate", "congestionLength", "inhalableEmissions", "noxEmissions",
                                "carbonMonoxideEmissions", "serviceLevel", "meausreInfo", "forecastTraffic", "v2xAvgSpeed",
                                "v2xAvgHeadway", "V2Xcls", "v2xDrivenDistance", "carNum", "carType"});
                    }
                });
                //Set values in Employee class
                setFieldSetMapper(new BeanWrapperFieldSetMapper<CoreData>() {
                    {
                        setTargetType(CoreData.class);
                    }
                });
            }
        });

        return reader;
    }

    @Bean
    public ConsoleItemWriter<CoreData> writer() {
        return new ConsoleItemWriter<CoreData>();
    }

    @Bean
    public ItemWriter<CoreData> writer2() {

        return new ItemWriter<CoreData>() {
            @Override
            public void write(List<? extends CoreData> list) throws Exception {

                list.stream().forEach(
                        l -> {
                            if (!StringUtils.isEmpty(l.getFormatedTime())) {

                                try {
                                    l.setRecTime(formatter.parse(l.getFormatedTime()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
                coreDataServiceImpl.saveBatch((Collection<CoreData>) list);
            }
        };
    }
}
