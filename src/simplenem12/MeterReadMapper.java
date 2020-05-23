package simplenem12;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class MeterReadMapper {

    public static List<MeterRead> mapValues (List<String []> p1){
        List<MeterRead> meterReads = new ArrayList<>();

        for (String[] filteredValues : p1) {
            switch (filteredValues[0]) {
                case "200":
                    MeterRead meterRead = new MeterRead(filteredValues[1], EnergyUnit.valueOf(filteredValues[2]));
                    meterReads.add(meterRead);
                    break;
                case "300":
                    try {
                        MeterVolume volume = new MeterVolume(new BigDecimal(filteredValues[2]), Quality.valueOf(filteredValues[3]));
                        MeterRead r = meterReads.get(meterReads.size() - 1);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                        LocalDate localDate = LocalDate.parse(filteredValues[1], formatter);
                        meterReads.remove(meterReads.size() - 1);
                        r.appendVolume(localDate, volume);
                        meterReads.add(r);
                    } catch (IllegalArgumentException | DateTimeParseException ex) {
                        continue;
                    }
                    break;
                default:
                    break;
            }
        }
        return meterReads;
    }

}
