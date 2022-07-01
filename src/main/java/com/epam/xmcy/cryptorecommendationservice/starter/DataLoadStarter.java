package com.epam.xmcy.cryptorecommendationservice.starter;

import com.epam.xmcy.cryptorecommendationservice.repository.CryptoStorageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Load information about crypto after started application
 */
@Component
@Slf4j
public class DataLoadStarter implements CommandLineRunner {
    private final CryptoStorageData cryptoStorageData;

    private final String path;

    public DataLoadStarter(CryptoStorageData cryptoStorageData,
                           @Value("${crypto.folder.name}") String path) {
        this.cryptoStorageData = cryptoStorageData;
        this.path = path;
    }

    /**
     * entry method for loading data
     *
     * @param args args
     * @throws Exception exception
     */
    @Override
    public void run(String... args) throws Exception {
        File folder;
        try {
            folder = ResourceUtils.getFile("classpath:" + path);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e.getCause());
            throw e;
        }

        if (folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                String filename = file.getName();
                String[] array = filename.split("_");
                cryptoStorageData.loadData(array[0], file);
            }
        }
    }
}
