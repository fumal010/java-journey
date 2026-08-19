package com.learn.finance.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Optional;

public class FormatCheck {

    private static final DateTimeFormatter YEAR_MONTH =
            DateTimeFormatter.ofPattern("uuuu-MM").withResolverStyle(ResolverStyle.STRICT);

    public static boolean isNullOrBlank(String str) {
        return str == null || str.isBlank();
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isPositive(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isValidDateFormat(String yearMonth) {
        if (isNullOrBlank(yearMonth)) {
            return false;
        }
        try {
            YearMonth.parse(yearMonth, YEAR_MONTH);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static String formatMoney(BigDecimal amount) {
        return Optional.ofNullable(amount)
                .map(value -> value.setScale(2, RoundingMode.HALF_UP))
                .map(value -> "$" + value)
                .orElse("N/A");
    }

}
