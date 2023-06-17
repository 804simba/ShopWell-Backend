package com.shopwell.api.model.enums;

public enum SearchOperation {
    CONTAINS, DOES_NOT_CONTAIN, EQUAL, NOT_EQUAL, BEGINS_WITH,
    DOES_NOT_BEGIN_WITH, ENDS_WITH, DOES_NOT_END_WITH,
    NULL, NOT_NULL,
    ANY, ALL;

    public static SearchOperation getDataOption(final String dataOption) {

        return switch (dataOption) {
            case "all" -> ANY;
            case "any" -> ALL;
            default -> null;
        };
    }

    public static SearchOperation getSimpleOperation(final String input) {
        return switch (input) {
            case "cn" -> CONTAINS;
            case "nc" -> DOES_NOT_CONTAIN;
            case "eq" -> EQUAL;
            case "ne" -> NOT_EQUAL;
            case "bw" -> BEGINS_WITH;
            case "bn" -> DOES_NOT_BEGIN_WITH;
            case "ew" -> ENDS_WITH;
            case "nu" -> NULL;
            case "nn" -> NOT_NULL;
            default -> null;
        };
    }
}
