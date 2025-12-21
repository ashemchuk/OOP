package ru.ashemchuk;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test.
 */
public class SubstringFinderTest {
    private static final long TARGET_SIZE = 1024L * 1024L * 1024L; // 1 ГБ
    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit();
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) tempFile.delete();
    }

    @Test
    void testBasicExample() throws IOException {
        writeToFile("абракадабра");
        List<Long> result = SubstringFinder.find(tempFile.getPath(), "бра");
        System.out.println("testBasicExample result: " + result);
        assertEquals(Arrays.asList(1L, 8L), result);
    }

    @Test
    void testEmptyPattern() throws IOException {
        writeToFile("test");
        assertEquals(Collections.emptyList(), SubstringFinder.find(tempFile.getPath(), ""));
    }

    @Test
    void testPatternLongerThanFile() throws IOException {
        writeToFile("abc");
        assertEquals(Collections.emptyList(), SubstringFinder.find(tempFile.getPath(), "abcdef"));
    }

    @Test
    void testPatternAtBeginning() throws IOException {
        writeToFile("start middle end");
        List<Long> result = SubstringFinder.find(tempFile.getPath(), "start");
        System.out.println("testPatternAtBeginning result: " + result);
        assertEquals(Collections.singletonList(0L), result);
    }

    @Test
    void testPatternAtEnd() throws IOException {
        writeToFile("start middle end");
        List<Long> result = SubstringFinder.find(tempFile.getPath(), "end");
        System.out.println("testPatternAtEnd result: " + result);
        assertEquals(Collections.singletonList(13L), result);
    }

    @Test
    void testUnicodeCharacters() throws IOException {
        writeToFile("🎉Привет🎉 мир 🎉");
        List<Long> result = SubstringFinder.find(tempFile.getPath(), "🎉");
        System.out.println("testUnicodeCharacters result: " + result);
        // Внимание: смайлик 🎉 занимает 2 символа char в Java
        assertEquals(Arrays.asList(0L, 8L, 15L), result);
    }

    @Test
    void testNoMatches() throws IOException {
        writeToFile("abcdefgh");
        assertEquals(Collections.emptyList(), SubstringFinder.find(tempFile.getPath(), "xyz"));
    }

    @Test
    void testLargeFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) sb.append("abc");
        writeToFile(sb.toString());

        List<Long> result = SubstringFinder.find(tempFile.getPath(), "abc");
        System.out.println("testLargeFile result size: " + result.size() + ", first few: " + result.subList(0, Math.min(5, result.size())));
        assertEquals(1000, result.size());
        for (int i = 0; i < 1000; i++) assertEquals(i * 3L, result.get(i));
    }

    @Test
    void testOverlappingMatches() throws IOException {
        writeToFile("aaaaaa");
        List<Long> result = SubstringFinder.find(tempFile.getPath(), "aaa");
        System.out.println("testOverlappingMatches result: " + result);
        assertEquals(Arrays.asList(0L, 1L, 2L, 3L), result);
    }

    @Test
    void testSingleCharPattern() throws IOException {
        writeToFile("abcabcabc");
        List<Long> result = SubstringFinder.find(tempFile.getPath(), "a");
        System.out.println("testSingleCharPattern result: " + result);
        assertEquals(Arrays.asList(0L, 3L, 6L), result);
    }

    @Test
    @Timeout(value = 120, unit = TimeUnit.SECONDS)
    void testGiganticFile() throws IOException {
        final String pattern = "TEST_PATTERN_123";
        final int patternRepeats = 100_000;
        final int bufferSize = 8192;

        try (BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream(tempFile), StandardCharsets.UTF_8), bufferSize)) {

            Random random = new Random(42);
            Set<Long> expectedPositions = new TreeSet<>();
            long currentPosition = 0;
            long bytesWritten = 0;

            while (expectedPositions.size() < patternRepeats) {
                long pos = random.nextLong(TARGET_SIZE / 2);
                expectedPositions.add(pos);
            }

            List<Long> positionList = new ArrayList<>(expectedPositions);
            int nextPatternIndex = 0;

            while (bytesWritten < TARGET_SIZE) {
                if (nextPatternIndex < positionList.size() &&
                    currentPosition >= positionList.get(nextPatternIndex)) {
                    writer.write(pattern);
                    currentPosition += pattern.length();
                    bytesWritten += pattern.getBytes(StandardCharsets.UTF_8).length;
                    nextPatternIndex++;
                } else {
                    int chunkSize = random.nextInt(1000) + 100;
                    StringBuilder chunk = new StringBuilder();
                    for (int i = 0; i < chunkSize && bytesWritten < TARGET_SIZE; i++) {
                        char c = (char) (random.nextInt(26) + 'a');
                        chunk.append(c);
                        bytesWritten++;
                    }
                    writer.write(chunk.toString());
                    currentPosition += chunk.length();
                }
            }

            for (int i = 0; i < 1000; i++) {
                writer.write(pattern);
            }
        }

        List<Long> foundPositions = SubstringFinder.find(tempFile.getAbsolutePath(), pattern);

        assertTrue(foundPositions.size() >= patternRepeats + 1000);
        assertTrue(foundPositions.size() < 100_000_000);

        Random random = new Random(42);
        List<Long> foundList = new ArrayList<>(foundPositions);
        for (int i = 0; i < Math.min(10, foundList.size()); i++) {
            long pos = foundList.get(random.nextInt(foundList.size()));
            assertTrue(pos >= 0 && pos < tempFile.length());

            try (RandomAccessFile raf = new RandomAccessFile(tempFile, "r")) {
                raf.seek(pos);
                byte[] buffer = new byte[pattern.length()];
                raf.read(buffer);
                String foundPattern = new String(buffer, StandardCharsets.UTF_8);
                assertEquals(pattern, foundPattern);
            }
        }
    }

    private void writeToFile(String content) throws IOException {
        try (Writer writer = new OutputStreamWriter(
            new FileOutputStream(tempFile), "UTF-8")) {
            writer.write(content);
        }
    }
}