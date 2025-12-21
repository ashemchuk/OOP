package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * test.
 */
public class ImageTest {

    @Test
    void testBasicImage() {
        Image image = new Image.Builder()
            .withAltText("Logo")
            .withUrl("https://example.com/logo.png")
            .build();

        assertEquals("![Logo](https://example.com/logo.png)", image.toMarkdown());
    }

    @Test
    void testImageWithTitle() {
        Image image = new Image.Builder()
            .withAltText("Diagram")
            .withUrl("https://example.com/diagram.png")
            .withTitle("System Architecture")
            .build();

        assertEquals("![Diagram](https://example.com/diagram.png \"System Architecture\")",
            image.toMarkdown());
    }

    @Test
    void testImageWithoutAltText() {
        Image image = new Image.Builder()
            .withUrl("https://example.com/image.png")
            .build();

        assertEquals("![](https://example.com/image.png)", image.toMarkdown());
    }

    @Test
    void testImageWithEmptyAltText() {
        Image image = new Image.Builder()
            .withAltText("")
            .withUrl("https://example.com/image.png")
            .build();

        assertEquals("![](https://example.com/image.png)", image.toMarkdown());
    }

    @Test
    void testImageEmptyUrl() {
        assertThrows(IllegalStateException.class, () -> new Image.Builder()
            .withAltText("Image")
            .withUrl("")
            .build());
    }

    @Test
    void testImageNullUrl() {
        assertThrows(IllegalStateException.class, () -> new Image.Builder()
            .withAltText("Image")
            .build());
    }

    @Test
    void testImageWithSpecialCharacters() {
        Image image = new Image.Builder()
            .withAltText("My Image (v1.0)")
            .withUrl("https://example.com/image_v1.png")
            .withTitle("Version 1.0")
            .build();
        var expected = "![My Image (v1.0)](https://example.com/image_v1.png \"Version 1.0\")";
        assertEquals(expected,
            image.toMarkdown());
    }
}