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
        File simpleNem12WithInvalidFirstAndLast = new File("SimpleNem12WithInvalidFirstAndLast.csv");
        File simpleNem12WithInvalid300Nmi = new File("SimpleNem12WithInvalid300Nmi.csv");
        File simpleNem12WithInvalid300Energy = new File("SimpleNem12WithInvalid300Energy.csv");
        File simpleNem12WithInvalid200Date = new File("SimpleNem12WithInvalid200Date.csv");
        File simpleNem12WithInvalid200Quality = new File("SimpleNem12WithInvalid200Quality.csv");

        // Uncomment below to try out test harness.
        Collection<MeterRead> meterReads = new CSVParserImpl().parseSimpleNem12(simpleNem12File);
        MeterRead read6123456789 = meterReads.stream().filter(mr -> mr.getNmi().equals("6123456789")).findFirst().get();
        System.out.println(String.format("Total volume for NMI 6123456789 is %f", read6123456789.getTotalVolume()));  // Should be -36.84
        MeterRead read6987654321 = meterReads.stream().filter(mr -> mr.getNmi().equals("6987654321")).findFirst().get();
        System.out.println(String.format("Total volume for NMI 6987654321 is %f", read6987654321.getTotalVolume()));  // Should be 14.33

        //Using stream parser version
        Collection<MeterRead> meterReadWithStream = new CSVStreamParserImpl().parseSimpleNem12(simpleNem12File);
        MeterRead read6123456789WithStream = meterReadWithStream.stream().filter(mr -> mr.getNmi().equals("6123456789")).findFirst().get();

        //Testing with invalid file
        Collection<MeterRead> meterReadsWithInvalidFile = new CSVParserImpl().parseSimpleNem12(simpleNem12WithInvalidFirstAndLast);
        System.out.println("meterReadsWithInvalidFile test");
        System.out.println(String.format("Collection size is %d, should be 0", meterReadsWithInvalidFile.size()));

        //Testing with invalid nmi
        Collection<MeterRead> meterReadsWithInvalidNmi = new CSVParserImpl().parseSimpleNem12(simpleNem12WithInvalid300Nmi);
        System.out.println("meterReadsWithInvalidNmi test");
        meterReadsWithInvalidNmi.forEach(s -> System.out.println(s.getNmi()));//should only get 6987654321


        Collection<MeterRead> meterReadWithInvalidEnergy = new CSVStreamParserImpl().parseSimpleNem12(simpleNem12WithInvalid300Energy);
        System.out.println("meterReadWithInvalidEnergy test");
        meterReadWithInvalidEnergy.forEach(s -> s.getVolumes().forEach((key, value) -> {
            System.out.println("Key : " + key + " Value : " + value.getVolume());
        }));//should have these entries
        /*
        300,20161215,-3.8,A
        300,20161216,0,A
        300,20161217,3.0,E
        300,20161218,-12.8,A
        300,20161219,23.43,E
        300,20161221,4.5,A
         */

        //Testing with invalid date
        Collection<MeterRead> meterReadWithInvalidDate = new CSVParserImpl().parseSimpleNem12(simpleNem12WithInvalid200Date);
        System.out.println("meterReadWithInvalidDate test");
        meterReadWithInvalidDate.forEach(s -> s.getVolumes().forEach((key, value) -> {
            System.out.println("Key : " + key + " Value : " + value.getVolume());
        }));//should have these entries
        /*
        300,20161115,32.0,A
        300,20161116,-33,A
        300,20161117,0,A
        300,20161118,0,E
        300,20161119,-9,A
        200,6987654321,KWH
        300,20161215,-3.8,A
        300,20161217,3.0,E
        300,20161218,-12.8,A
        300,20161219,23.43,E
        300,20161221,4.5,A
         */

        //Testing with invalid quality
        Collection<MeterRead> meterReadWithInvalidQuality = new CSVParserImpl().parseSimpleNem12(simpleNem12WithInvalid200Quality);
        System.out.println("meterReadWithInvalidQuality test");
        meterReadWithInvalidDate.forEach(s -> s.getVolumes().forEach((key, value) -> {
            System.out.println("Key : " + key + " Value : " + value.getVolume());
        }));//should have these entries
        /*
        200,6123456789,KWH
        300,20161113,-50.8,A
        300,20161114,23.96,A
        300,20161116,-33,A
        300,20161117,0,A
        300,20161118,0,E
        300,20161119,-9,A
        200,6987654321,KWH
        300,20161215,-3.8,A
        300,20161216,0,A
        300,20161218,-12.8,A
        300,20161219,23.43,E
        300,20161221,4.5,A
         */
    }
}
