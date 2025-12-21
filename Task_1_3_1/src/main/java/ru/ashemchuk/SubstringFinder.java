package ru.ashemchuk;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Substring Finder by Rabin-Karp algo.
 */
public class SubstringFinder {
    private static final int PRIME = 31;
    private static final int MOD = 1_000_000_009;
    private static final int DEFAULT_BUFFER_SIZE = 8192; // 8KB buffer

    /**
     * Overloading {@link #find(String filename, String pattern, int bufferSize)}
     * with default buffer size = 8192 bytes.
     *
     * @param filename the path to the file to search in
     * @param pattern  the substring to search for (empty pattern returns empty list)
     * @return a list of starting positions (indices) where the pattern occurs
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static List<Long> find(String filename, String pattern) throws IOException {
        return SubstringFinder.find(filename, pattern, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Finds all starting positions of the specified pattern within the file.
     * Positions are zero-based indices measured in {@code char} units
     * (UTF-16 code units), consistent with Java's {@code String} indexing.
     * For Unicode characters outside the Basic Multilingual Plane (represented
     * as surrogate pairs), each {@code char} in the pair is counted separately.
     * Important: This method reads the file in UTF-8 encoding.
     * Ensure the file encoding matches this expectation.
     *
     * @param filename the path to the file to search in
     * @param pattern the substring to search for (empty pattern returns empty list)
     * @param bufferSize the size of read buffer
     * @return a list of starting positions (indices) where the pattern occurs
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static List<Long> find(String filename, String pattern, int bufferSize) throws IOException {
        List<Long> indices = new ArrayList<>();
        if (pattern.isEmpty()) {
            return indices;
        }

        int m = pattern.length();
        if (m == 0) {
            return indices;
        }

        long patternHash = 0;
        long power = 1;

        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * PRIME + pattern.charAt(i)) % MOD;
            if (i < m - 1) {
                power = (power * PRIME) % MOD;
            }
        }

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(
                new FileInputStream(filename),
                StandardCharsets.UTF_8
            ),
            bufferSize
        )) {

            char[] window = new char[m];
            int windowSize = 0;
            long currentHash = 0;
            long position = 0;

            char[] buffer = new char[bufferSize];
            int charsRead;

            while ((charsRead = reader.read(buffer)) != -1) {
                for (int bufferIndex = 0; bufferIndex < charsRead; bufferIndex++) {
                    char c = buffer[bufferIndex];

                    if (windowSize < m) {
                        currentHash = (currentHash * PRIME + c) % MOD;
                        window[windowSize] = c;
                        windowSize++;

                        if (windowSize == m && currentHash == patternHash) {
                            if (arraysEqual(window, pattern.toCharArray(), m)) {
                                indices.add(position - m + 1);
                            }
                        }
                    } else {
                        char oldChar = window[0];
                        long oldHashContribution = (oldChar * power) % MOD;
                        currentHash = (currentHash - oldHashContribution + MOD) % MOD;
                        currentHash = (currentHash * PRIME + c) % MOD;

                        System.arraycopy(window, 1, window, 0, m - 1);
                        window[m - 1] = c;

                        if (currentHash == patternHash) {
                            if (arraysEqual(window, pattern.toCharArray(), m)) {
                                indices.add(position - m + 1);
                            }
                        }
                    }
                    position++;
                }
            }
        }
        return indices;
    }

    /**
     * Compares two character arrays for equality up to specified length.
     *
     * @param arr1   first character array
     * @param arr2   second character array
     * @param length number of characters to compare
     * @return true if arrays are equal up to specified length, false otherwise
     */
    private static boolean arraysEqual(char[] arr1, char[] arr2, int length) {
        for (int i = 0; i < length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Main method for command-line usage.
     *
     * @param args command-line arguments: filename and pattern
     * @throws IOException if file cannot be read
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java ru.ashemchuk.SubstringFinder <filename> <pattern>");
            return;
        }
        List<Long> result = find(args[0], args[1]);
        System.out.println(result);
    }
}