package simplenem12;

import java.util.List;

public class Validator {
    /**
     * This checks if the record type 200 has valid values
     * 1. Energy Unit cannot have values other than KWH
     * 2. nmi should be 10 chars long
     * @param value
     * @return boolean
     */
    public static boolean is200RecordTypeInvalid(String[] value) {
        if ("200".equalsIgnoreCase(value[0])) {
            if (value[1].length() != 10) {
                return true;
            }
            try {
                EnergyUnit.valueOf(value[2]);
            } catch (IllegalArgumentException ex) {
                return true;
            }
        }
        return false;
    }

    /**
     * This checks if the file is valid, should begin with 100 and end in 900
     * @param linesArray
     * @return boolean
     */
    public static boolean isFileValid(List<String[]> linesArray) {
        if (!"100".equalsIgnoreCase(linesArray.get(0)[0])) {
            return false;
        }
        if (!"900".equalsIgnoreCase(linesArray.get(linesArray.size()-1)[0])) {
            return false;
        }
        return true;
    }
}
