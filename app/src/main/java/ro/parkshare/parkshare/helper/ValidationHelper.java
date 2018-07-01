package ro.parkshare.parkshare.helper;

public class ValidationHelper {

    public static String validateStringMinSize(String fieldName, String field, int minSize) {
        if (minSize > 0 && (field == null || field.isEmpty())) {
            throw new IllegalArgumentException(fieldName + " must have " + minSize + " characters");
        }
        if (field == null || field.length() < minSize) {
            throw new IllegalArgumentException(fieldName + " must have " + minSize + " characters");
        }

        return field;
    }
}
