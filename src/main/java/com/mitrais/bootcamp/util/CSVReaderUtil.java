package com.mitrais.bootcamp.util;

import com.mitrais.bootcamp.domain.ATMSimulationException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: CSVReaderUtil.java, v 0.1 2020‐07‐29 9:31 Aji Atin Mulyadi Exp $$
 */
public abstract class CSVReaderUtil<T> {
    protected List<T> data = new ArrayList<>();
    Map<String, String> recordsMap = new HashMap<>();

    protected abstract T parseRecord (CSVRecord record) throws ATMSimulationException;

    public List<T> parseFromCSV(String path) throws ATMSimulationException, IOException {

        Reader in = new FileReader(path);
        CSVParser records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        records.forEach(record -> this.data.add(this.parseRecord(record)));

        return this.data;
    }
}