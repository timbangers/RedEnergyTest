package simplenem12;

import java.io.*;
import java.util.*;

public class CSVParserImpl implements SimpleNem12Parser {

    /**
     * This overridden method parses the csv file into a Collection<MeterRead>
     * Invalid values are removed from the Collection<MeterRead>
     * @param simpleNem12File file in Simple NEM12 format
     * @return collection
     */
    @Override
    public Collection<MeterRead> parseSimpleNem12(File simpleNem12File) {
        List<String[]> linesArray = new ArrayList<>();
        try {
            //value [0] record type
            //value [1] nmi if 200, date if 300, null if 100/900
            //value [2] energyunit if 200, volume if 300, null if 100/900
            //value [3] quality if 300, null if 100/200/900
            BufferedReader reader = new BufferedReader(new FileReader(simpleNem12File));
            String row;
            while ((row = reader.readLine()) != null) {
                linesArray.add(row.split(","));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.filterList(linesArray);
        Collection<MeterRead> collection = MeterReadMapper.mapValues(linesArray);
        return collection;
    }

    /**
     * This method filters the list of Strings and checks if the record type is valid.
     * If the record type is invalid, it is removed from the final collection
     * @param linesArray
     * @return stringArrays
     */
    private List<String[]> filterList(List<String[]> linesArray) {
        List<String[]> tobeDeleted = new ArrayList<>();
        int i = 0;
        for (ListIterator<String[]> iterator = linesArray.listIterator(); iterator.hasNext(); ) {
            String[] s = iterator.next();
            if ("100".equalsIgnoreCase(s[0]) || "900".equalsIgnoreCase(s[0])) {
                iterator.remove();
            }
            if ("300".equalsIgnoreCase(s[0])) {
                int q = iterator.previousIndex() - 1;
                String[] x = linesArray.get(q);
                if (Validator.is200RecordTypeInvalid(x)) {
                    iterator.remove();
                    tobeDeleted.add(x);
                }
            }
            i++;
        }

        linesArray.removeAll(tobeDeleted);
        return linesArray;
    }
}
