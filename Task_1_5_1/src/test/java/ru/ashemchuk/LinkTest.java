package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * test.
 */
public class LinkTest {

    @Test
    void testBasicLink() {
        Link link = new Link.Builder()
            .withText("Google")
            .withUrl("https://google.com")
            .build();

        assertEquals("[Google](https://google.com)", link.toMarkdown());
    }

    @Test
    void testLinkWithTitle() {
        Link link = new Link.Builder()
            .withText("Search")
            .withUrl("https://google.com")
            .withTitle("Google Search")
            .build();

        assertEquals("[Search](https://google.com \"Google Search\")", link.toMarkdown());
    }

    @Test
    void testLinkWithoutText() {
        Link link = new Link.Builder()
            .withUrl("https://google.com")
            .build();

        assertEquals("[https://google.com](https://google.com)", link.toMarkdown());
    }

    @Test
    void testLinkWithEmptyText() {
        Link link = new Link.Builder()
            .withText("")
            .withUrl("https://google.com")
            .build();

        assertEquals("[https://google.com](https://google.com)", link.toMarkdown());
    }

    @Test
    void testLinkWithSpacesAsText() {
        Link link = new Link.Builder()
            .withText("   ")
            .withUrl("https://google.com")
            .build();

        assertEquals("[https://google.com](https://google.com)", link.toMarkdown());
    }

    @Test
    void testLinkEmptyUrl() {
        assertThrows(IllegalStateException.class, () -> new Link.Builder()
            .withText("Google")
            .withUrl("")
            .build());
    }

    @Test
    void testLinkNullUrl() {
        assertThrows(IllegalStateException.class, () -> new Link.Builder()
            .withText("Google")
            .build());
    }

}