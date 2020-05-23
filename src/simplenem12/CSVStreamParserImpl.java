package simplenem12;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class CSVStreamParserImpl implements SimpleNem12Parser {
    @Override
    public Collection<MeterRead> parseSimpleNem12(File simpleNem12File) {
        Collection<MeterRead> collection = null;
        try {

            BufferedReader reader = new BufferedReader(new FileReader(simpleNem12File));
            List<String []> stringArrays = reader.lines().map(mapToStringArray).skip(1)
                    .filter(remove900).collect(toList());
            this.filterList(stringArrays);
            collection = MeterReadMapper.mapValues(stringArrays);
            return collection;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return collection;

    }

    private Function<String, String[]> mapToStringArray = (row) -> row.split(",");

    Predicate<String []> remove900 =  p -> "200".equalsIgnoreCase(p[0]) || "300".equalsIgnoreCase(p[0]);

    /**
     * This method filters the list of Strings and checks if the record type is valid.
     * If the record type is invalid, it is removed from the final collection
     * @param stringArrays
     * @return stringArrays
     */
    private List<String[]> filterList(List<String[]> stringArrays) {
        List<String[]> tobeDeleted = new ArrayList<>();
        int i = 0;
        for (ListIterator<String[]> iterator = stringArrays.listIterator(); iterator.hasNext(); ) {
            String[] s = iterator.next();
            if ("300".equalsIgnoreCase(s[0])) {
                int q = iterator.previousIndex() -1 ;
                String[] x = stringArrays.get(q);
                if (Validator.is200RecordTypeInvalid(x)) {
                    iterator.remove();
                    tobeDeleted.add(x);
                }
            }
            i++;
        }
        stringArrays.removeAll(tobeDeleted);
        return stringArrays;
    }
}
