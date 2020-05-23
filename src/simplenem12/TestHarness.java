// Copyright Red Energy Limited 2017

package simplenem12;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Simple test harness for trying out SimpleNem12Parser implementation
 */
public class TestHarness {

    public static void main(String[] args) {
        File simpleNem12File = new File("SimpleNem12.csv");

        // Uncomment below to try out test harness.
        Collection<MeterRead> meterReads = new CSVParserImpl().parseSimpleNem12(simpleNem12File);

        MeterRead read6123456789 = meterReads.stream().filter(mr -> mr.getNmi().equals("6123456789")).findFirst().get();
        System.out.println(String.format("Total volume for NMI 6123456789 is %f", read6123456789.getTotalVolume()));  // Should be -36.84

        MeterRead read6987654321 = meterReads.stream().filter(mr -> mr.getNmi().equals("6987654321")).findFirst().get();
        System.out.println(String.format("Total volume for NMI 6987654321 is %f", read6987654321.getTotalVolume()));  // Should be 14.33
        Collection<MeterRead> meterReadWithStream = new CSVStreamParserImpl().parseSimpleNem12(simpleNem12File);
        MeterRead read6123456789WithStream  = meterReadWithStream.stream().filter(mr -> mr.getNmi().equals("6123456789")).findFirst().get();

        System.out.println(String.format("Total volume for NMI 6987654321 is %f", read6123456789WithStream.getTotalVolume()));  // Should be 14.33

    }
}
