package simplenem12;

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



}
