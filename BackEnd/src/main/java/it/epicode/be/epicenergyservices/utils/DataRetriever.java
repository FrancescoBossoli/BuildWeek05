package it.epicode.be.epicenergyservices.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import it.epicode.be.epicenergyservices.models.Municipality;
import it.epicode.be.epicenergyservices.models.Province;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataRetriever {

	private boolean check;

	public List<String[]> lineReader(String fileName) {
		List<String[]> r = null;
		CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withCSVParser(csvParser).withSkipLines(1).build()) {
			r = reader.readAll();
		} catch (IOException e) {
			log.info(e.getMessage());
		} catch (CsvException e) {
			log.info(e.getMessage());
		}
		return r;
	}

	public List<Set<?>> dataParse(List<String[]> list, String fileName) {
		Set<Province> provinces = new HashSet<Province>();
		Set<Municipality> listMunicipality = new HashSet<Municipality>();
		List<Set<?>> listData = new ArrayList<Set<?>>();
		List<String[]> municipalities = lineReader(fileName);
		list.forEach(x -> {
			Province p = Province.builder().acronym(x[0]).name(x[1]).region(x[2]).build();
			check = false;
			municipalities.forEach(y -> {
				if (y[3].equals(x[1])) {
					p.setProvinceCode(Integer.parseInt(y[0]));
					if (y[1].contains("#RIF!")) y[1] = "0";
					Municipality m = Municipality.builder().districtCode(Integer.parseInt(y[1])).name(y[2]).province(p).build();
					provinces.add(p);
					listMunicipality.add(m);
					check = true;
				}
			});
			if (!check) provinces.add(p);
			listData.add(provinces);
			listData.add(listMunicipality);
		});
		return listData;
	}
}
