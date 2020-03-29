package edu.neu.coe.huskySort.sort;

import org.junit.Test;

import static org.junit.Assert.*;

public class BaseHelperTest {

    @Test
    public void instrumented() {
        assertFalse(new BaseHelper<String>("test").instrumented());
    }

    @Test
    public void less() {
        assertTrue(new BaseHelper<String>("test").less("a", "b"));
    }

    @Test
    public void compare() {
        String[] xs = new String[]{"a", "b"};
        final Helper<String> helper = new BaseHelper<>("test");
        assertEquals(-1, helper.compare(xs, 0, 1));
        assertEquals(0, helper.compare(xs, 0, 0));
        assertEquals(1, helper.compare(xs, 1, 0));
    }

    @Test
    public void swap() {
        String[] xs = new String[]{"a", "b"};
        final Helper<String> helper = new BaseHelper<>("test");
        helper.swap(xs, 0, 1);
        assertArrayEquals(new String[]{"b", "a"}, xs);
        helper.swap(xs, 0, 1);
        assertArrayEquals(new String[]{"a", "b"}, xs);
    }

    @Test
    public void sorted() {
        String[] xs = new String[]{"a", "b"};
        final Helper<String> helper = new BaseHelper<>("test");
        assertTrue(helper.sorted(xs));
        helper.swap(xs, 0, 1);
        assertFalse(helper.sorted(xs));
    }

    @Test
    public void inversions() {
        String[] xs = new String[]{"a", "b"};
        final Helper<String> helper = new BaseHelper<>("test");
        assertEquals(0, helper.inversions(xs));
        helper.swap(xs, 0, 1);
        assertEquals(1, helper.inversions(xs));
    }

    @Test
    public void postProcess1() {
        String[] xs = new String[]{"a", "b"};
        final Helper<String> helper = new BaseHelper<>("test");
        helper.postProcess(xs);
    }

    @Test(expected = BaseHelper.HelperException.class)
    public void postProcess2() {
        String[] xs = new String[]{"b", "a"};
        final Helper<String> helper = new BaseHelper<>("test");
        helper.postProcess(xs);
    }

    @Test
    public void random() {
        String[] words = new String[]{"Hello", "World"};
        final Helper<String> helper = new BaseHelper<>("test", 3, 0L);
        final String[] strings = helper.random(String.class, r -> words[r.nextInt(2)]);
        assertArrayEquals(new String[]{"World", "World", "Hello"}, strings);
    }

    @Test
    public void testToString() {
        final Helper<String> helper = new BaseHelper<>("test", 3);
        assertEquals("Helper for test with 3 elements", helper.toString());
    }

    @Test
    public void getDescription() {
        final Helper<String> helper = new BaseHelper<>("test", 3);
        assertEquals("test", helper.getDescription());
    }

    @Test(expected = RuntimeException.class)
    public void getSetN() {
        final Helper<String> helper = new BaseHelper<>("test", 3);
        assertEquals(3, helper.getN());
        helper.setN(4);
        assertEquals(4, helper.getN());
    }

    @Test
    public void getSetNBis() {
        final Helper<String> helper = new BaseHelper<>("test");
        assertEquals(0, helper.getN());
        helper.setN(4);
        assertEquals(4, helper.getN());
    }

    @Test
    public void close() {
        final Helper<String> helper = new BaseHelper<>("test");
        helper.close();
    }

    @Test
    public void swapStable() {
        String[] xs = new String[]{"a", "b"};
        final Helper<String> helper = new BaseHelper<>("test");
        helper.swapStable(xs, 1);
        assertArrayEquals(new String[]{"b", "a"}, xs);
        helper.swapStable(xs, 1);
        assertArrayEquals(new String[]{"a", "b"}, xs);
    }

    @Test
    public void fixInversion1() {
        String[] xs = new String[]{"a", "b"};
        final Helper<String> helper = new BaseHelper<>("test");
        helper.fixInversion(xs, 1);
        assertArrayEquals(new String[]{"a", "b"}, xs);
        helper.swapStable(xs, 1);
        assertArrayEquals(new String[]{"b", "a"}, xs);
        helper.fixInversion(xs, 1);
        assertArrayEquals(new String[]{"a", "b"}, xs);
    }

    @Test
    public void testFixInversion2() {
        String[] xs = new String[]{"a", "b"};
        final Helper<String> helper = new BaseHelper<>("test");
        helper.fixInversion(xs, 0, 1);
        assertArrayEquals(new String[]{"a", "b"}, xs);
        helper.swap(xs, 0, 1);
        assertArrayEquals(new String[]{"b", "a"}, xs);
        helper.fixInversion(xs, 0, 1);
        assertArrayEquals(new String[]{"a", "b"}, xs);
    }
}