package com.m2.bookstore.common.constants;

/**
 * @author Mohammed Abdu
 * @version vr0.1
 * @email eng.mo.abdu@gmail.com
 * @creationDate 25 Nov 2023
 */
public final class Constants {

    public static final String UTILITY_CLASS = "Utility class";
    public static final String JUNIT_TEST = "JUNIT_TEST";

    public static final class M2Files {

        public static final String BASE_FILE_NAME = "data";
        public static final String FILE_EXTENSION = ".txt";
        public static final String USER_FILE_NAME = BASE_FILE_NAME + "/users" + FILE_EXTENSION;

        private M2Files() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
    }

    public static class PrintFormatter {

        public static final String TRADE_SECTION_TYPE_HEADER = "%n******************************** %s ********************************%n";

        private PrintFormatter() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
    }

    public static class Messages {

        public static class SuccessMessages {

            public static final String CREATED_SUCCESSFULLY = "Created successfully";

            private SuccessMessages() {
                throw new IllegalStateException(UTILITY_CLASS);
            }
        }

        private Messages() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
    }

    public static final class Characters {
        public static final String SLASH = "/";
        public static final String PERCENTAGE = "%";
        public static final String COMMA = ",";
        public static final String COMMA_SPACE = ", ";
        public static final String SPACE = " ";
        public static final String EMPTY_STRING = "";
        public static final String UNDER_SCORE = "_";
        public static final String LEFT_BRACKET = "(";
        public static final String RIGHT_BRACKET = ")";
        public static final String NEWLINE = "\n";
        public static final String DOUBLE_SLASH = "\\";
        public static final String DASH = "-";
        public static final String STAR = "*";
        public static final String DOT = ".";
        public static final String QUESTION_MARK = "?";
        public static final String UTF8 = "UTF-8";

        private Characters() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
    }

    private Constants() {
        throw new IllegalStateException(UTILITY_CLASS);
    }
}
