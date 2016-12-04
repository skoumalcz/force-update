package net.skoumal.forceupdate;

/**
 * Helper to avoid test failing due to missing internet connection. Simply place current timestamp
 * to {@link #CURRENT_TIMESTAMP} and {@link #ignoreOfflineErrors()} will return true for next two
 * hours.
 */
public class OfflineHelper {

    private static long CURRENT_TIMESTAMP = 1480859257;

    private static long IGNORE_ERRORS_TILL = CURRENT_TIMESTAMP + (2 * 3600);

    public static boolean ignoreOfflineErrors() {
        return (System.currentTimeMillis() / 1000) < IGNORE_ERRORS_TILL;
    }
}
