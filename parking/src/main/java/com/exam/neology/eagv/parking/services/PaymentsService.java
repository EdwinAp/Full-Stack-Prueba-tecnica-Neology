package com.exam.neology.eagv.parking.services;

import com.exam.neology.eagv.parking.dtos.PlateMinutesCostCsv;
import com.exam.neology.eagv.parking.entity.CatCostVehicle;
import com.exam.neology.eagv.parking.entity.CatVehicle;
import com.exam.neology.eagv.parking.entity.StayVehicle;
import com.exam.neology.eagv.parking.repository.CatCostVehicleRepository;
import com.exam.neology.eagv.parking.repository.CatVehicleRepository;
import com.exam.neology.eagv.parking.repository.StayVehicleRepository;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class PaymentsService {

    private final StayVehicleRepository stayVehicleRepository;
    private final CatCostVehicleRepository catCostVehicleRepository;
    private final CatVehicleRepository catVehicleRepository;

    public PaymentsService(
            StayVehicleRepository stayVehicleRepository,
            CatCostVehicleRepository catCostVehicleRepository,
            CatVehicleRepository catVehicleRepository
    ) {
        this.stayVehicleRepository = stayVehicleRepository;
        this.catCostVehicleRepository = catCostVehicleRepository;
        this.catVehicleRepository = catVehicleRepository;
    }

    public String generateReportBy(Integer catVehicle) throws IOException {

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        LocalDateTime startDate = LocalDateTime.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                1, 0, 0, 0 ,0);

        Optional<CatVehicle> catVehicleOptional = catVehicleRepository.findById(catVehicle);

        // Costo
        Optional<CatCostVehicle> catCostVehicle = catCostVehicleRepository.findByCatVehicle(catVehicleOptional.get());

        // Lista de vehiculos del mes
        List<StayVehicle> result =  stayVehicleRepository.findByCatVehicleIdAndStartDate(catVehicle, startDate);

        Map<String, Long> totalMinutes = new HashMap<>();

        result.parallelStream().forEach(stayVehicle -> {
            Long resultMinutes = ChronoUnit.MINUTES.between(stayVehicle.getInputDate(), stayVehicle.getOutputDate());
            totalMinutes.compute(stayVehicle.getPlate().getPlate(), (plate, minutes) ->
                    minutes == null ? resultMinutes : minutes + resultMinutes);
        });

        List<PlateMinutesCostCsv> plateMinutesCostCsvs = new ArrayList<>();

        totalMinutes.forEach((plate, minutes) -> {
            String costTotal = multiplyFormater(catCostVehicle.get().getCost(), minutes);
            plateMinutesCostCsvs.add(PlateMinutesCostCsv.builder()
                            .plate(plate)
                            .minutes(minutes)
                            .cost(costTotal)
                    .build());
        });

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(PlateMinutesCostCsv.class).withHeader();

        StringWriter writer = new StringWriter();
        mapper.writer(schema).writeValue(writer, plateMinutesCostCsvs);

        return writer.toString();
    }

    public static String multiplyFormater(BigDecimal bigDecimal, Long longValue) {
        BigDecimal longBigDecimal = new BigDecimal(longValue);

        BigDecimal resultado = bigDecimal.multiply(longBigDecimal);

        DecimalFormat df = new DecimalFormat("#.00");

        return df.format(resultado);
    }

}
