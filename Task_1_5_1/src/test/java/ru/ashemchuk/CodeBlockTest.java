package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
* test.
*/
public class CodeBlockTest {

    @Test
    void testCodeBlockWithoutLanguage() {
        CodeBlock codeBlock = new CodeBlock.Builder()
            .addLine("public class Main {")
            .addLine("    public static void main(String[] args) {")
            .addLine("        System.out.println(\"Hello\");")
            .addLine("    }")
            .addLine("}")
            .build();

        String expected = """
            ```
            public class Main {
                public static void main(String[] args) {
                    System.out.println("Hello");
                }
            }
            ```""";
        assertEquals(expected, codeBlock.toMarkdown());
    }

    @Test
    void testCodeBlockWithLanguage() {
        CodeBlock codeBlock = new CodeBlock.Builder()
            .withLanguage("java")
            .addMultiline("public class Test {\n    // comment\n}")
            .build();

        String expected = """
            ```java
            public class Test {
                // comment
            }
            ```""";
        assertEquals(expected, codeBlock.toMarkdown());
    }

    @Test
    void testEmptyCodeBlock() {
        assertThrows(IllegalStateException.class, () -> new CodeBlock.Builder().build());
    }

    @Test
    void testSingleLineCodeBlock() {
        CodeBlock codeBlock = new CodeBlock.Builder()
            .withLanguage("bash")
            .addLine("ls -la")
            .build();

        String expected = "```bash\nls -la\n```";
        assertEquals(expected, codeBlock.toMarkdown());
    }

    @Test
    void testMixedLineAddition() {
        CodeBlock codeBlock = new CodeBlock.Builder()
            .withLanguage("python")
            .addLine("def hello():")
            .addLine("    print(\"Hello\")")
            .addLine("    return True")
            .addLine("result = hello()")
            .build();

        String expected = """
            ```python
            def hello():
                print("Hello")
                return True
            result = hello()
            ```""";
        assertEquals(expected, codeBlock.toMarkdown());
    }

    @Test
    void testCodeBlockWithEmptyLines() {
        CodeBlock codeBlock = new CodeBlock.Builder()
            .withLanguage("text")
            .addLine("Line 1")
            .addLine("")
            .addLine("Line 3")
            .build();

        String expected = """
            ```text
            Line 1
            
            Line 3
            ```""";
        assertEquals(expected, codeBlock.toMarkdown());
    }
}