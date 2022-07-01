package com.epam.xmcy.cryptorecommendationservice.service.impl;

import com.epam.xmcy.cryptorecommendationservice.model.CryptoModel;
import com.epam.xmcy.cryptorecommendationservice.service.ReadFileService;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * Read File Service class for reading data from csv format file
 */
@Service
@Qualifier("csvReader")
public class ReadFileServiceImpl implements ReadFileService {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CryptoModel> read(File file) {
        CSVReader csvReader;
        try {
            csvReader = new CSVReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        CsvToBeanBuilder<CryptoModel> beanBuilder = new CsvToBeanBuilder<>(csvReader);
        beanBuilder.withType(CryptoModel.class);
        return beanBuilder.build().parse();
    }
}
