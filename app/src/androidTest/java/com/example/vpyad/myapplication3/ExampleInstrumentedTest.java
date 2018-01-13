package com.example.vpyad.myapplication3;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.vpyad.myapplication3.helpers.StringValidatorHelper;
import com.example.vpyad.myapplication3.models.ListConfig;
import com.example.vpyad.myapplication3.models.ListItem;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.vpyad.myapplication3", appContext.getPackageName());
    }

    // region Numeric Mode test
    @Test
    public void numericModeWithNumericInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_NUMERIC);

        String input = "123";
        ListItem item = new ListItem(input);

        boolean isAdded = listConfig.addToList(item);
        boolean fact = listConfig.getList().contains(item);

        assertEquals(true, isAdded);
        assertEquals(true, fact);
    }

    @Test
    public void numericModeWithMixedInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_NUMERIC);

        String input = "Apple1";
        ListItem item = new ListItem(input);

        boolean isAdded = listConfig.addToList(item);
        boolean fact = listConfig.getList().contains(item);

        assertEquals(false, isAdded);
        assertEquals(false, fact);
    }

    @Test
    public void numericModeWithAlphabeticInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_NUMERIC);

        String input = "Apple";
        ListItem item = new ListItem(input);

        boolean isAdded = listConfig.addToList(item);
        boolean fact = listConfig.getList().contains(item);

        assertEquals(fact, isAdded);
        assertEquals(false, fact);
    }

    @Test
    public void numericModeWithIllegalInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_NUMERIC);

        String input = "!?Apple1";
        ListItem item = new ListItem(input);

        boolean isAdded = listConfig.addToList(item);
        boolean fact = listConfig.getList().contains(item);

        assertEquals(fact, isAdded);
        assertEquals(false, fact);
    }

    // endregion

    // region Mixed Mode tests
    @Test
    public void mixedModeWithMixedInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_MIXED);

        String input = "Apple123Йо";
        ListItem item = new ListItem(input);

        boolean isAdded = listConfig.addToList(item);
        boolean fact = listConfig.getList().contains(item);

        assertEquals(true, isAdded);
        assertEquals(true, fact);
    }

    @Test
    public void mixedModeWithNumericInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_MIXED);

        String input = "123";
        ListItem item = new ListItem(input);

        boolean isAdded = listConfig.addToList(item);
        boolean fact = listConfig.getList().contains(item);

        assertEquals(fact, isAdded);
        assertEquals(true, fact);
    }

    @Test
    public void mixedModeWithAlphabeticInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_MIXED);

        String input = "ASDASD";
        ListItem item = new ListItem(input);

        boolean isAdded = listConfig.addToList(item);
        boolean fact = listConfig.getList().contains(item);

        assertEquals(fact, isAdded);
        assertEquals(true, fact);
    }

    @Test
    public void mixedModeWithIllegalInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_MIXED);

        String input = "!.?QWf";
        ListItem item = new ListItem(input);

        boolean isAdded = listConfig.addToList(item);
        boolean fact = listConfig.getList().contains(item);

        assertEquals(false, isAdded);
        assertEquals(false, fact);
    }
    // endregion

    // region Alphabetic Mode tests
    @Test
    public void alphabeticModeWithAlphabeticInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_ALPHABETIC);

        String input = "Qwerty";
        ListItem item = new ListItem(input);

        boolean isAdded = listConfig.addToList(item);
        boolean fact = listConfig.getList().contains(item);

        assertEquals(true, isAdded);
        assertEquals(true, fact);
    }

    @Test
    public void alphabeticModeWithNumericInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_ALPHABETIC);

        String input = "12121";
        ListItem item = new ListItem(input);

        boolean isAdded = listConfig.addToList(item);
        boolean fact = listConfig.getList().contains(item);

        assertEquals(false, isAdded);
        assertEquals(false, fact);
    }

    @Test
    public void alphabeticModeWithMixedInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_ALPHABETIC);

        String input = "123Apple";
        ListItem item = new ListItem(input);

        boolean isAdded = listConfig.addToList(item);
        boolean fact = listConfig.getList().contains(item);

        assertEquals(false, isAdded);
        assertEquals(false, fact);
    }

    @Test
    public void alphabeticModeWithIllegalInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_ALPHABETIC);

        String input = "!?.(Apple";
        ListItem item = new ListItem(input);

        boolean isAdded = listConfig.addToList(item);
        boolean fact = listConfig.getList().contains(item);

        assertEquals(false, isAdded);
        assertEquals(false, fact);
    }
    // endregion

    @Test
    public void nullInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_MIXED);

        String input = "Qwerty";
        ListItem item = new ListItem(input);

        boolean isAdded = listConfig.addToList(null);
        boolean fact = listConfig.getList().contains(item);

        assertEquals(false, isAdded);
        assertEquals(false, fact);
    }

    @Test
    public void sortAsc() {
        ListConfig listConfig = new ListConfig();
        listConfig.setSort(ListConfig.SORT_ASC);
        listConfig.addToList(new ListItem("B"));
        listConfig.addToList(new ListItem("A"));
        listConfig.addToList(new ListItem("C"));
        listConfig.addToList(new ListItem("1"));
        listConfig.addToList(new ListItem("1A"));

        List<ListItem> expectedList = new ArrayList<>();
        expectedList.add(new ListItem("1"));
        expectedList.add(new ListItem("1A"));
        expectedList.add(new ListItem("A"));
        expectedList.add(new ListItem("B"));
        expectedList.add(new ListItem("C"));

        assertArrayEquals(expectedList.toArray(), listConfig.getList().toArray());
    }

    @Test
    public void sortDesc() {
        ListConfig listConfig = new ListConfig();
        listConfig.setSort(ListConfig.SORT_DESC);
        listConfig.addToList(new ListItem("B"));
        listConfig.addToList(new ListItem("A"));
        listConfig.addToList(new ListItem("C"));
        listConfig.addToList(new ListItem("1"));
        listConfig.addToList(new ListItem("1A"));

        List<ListItem> expectedList = new ArrayList<>();
        expectedList.add(new ListItem("C"));
        expectedList.add(new ListItem("B"));
        expectedList.add(new ListItem("A"));
        expectedList.add(new ListItem("1A"));
        expectedList.add(new ListItem("1"));

        assertArrayEquals(expectedList.toArray(), listConfig.getList().toArray());
    }

    @Test
    public void noSort() {
        ListConfig listConfig = new ListConfig();
        listConfig.setSort(ListConfig.SORT_NO_SORT);
        listConfig.addToList(new ListItem("B"));
        listConfig.addToList(new ListItem("A"));
        listConfig.addToList(new ListItem("C"));
        listConfig.addToList(new ListItem("1"));
        listConfig.addToList(new ListItem("1A"));

        List<ListItem> expectedList = new ArrayList<>();
        expectedList.add(new ListItem("B"));
        expectedList.add(new ListItem("A"));
        expectedList.add(new ListItem("C"));
        expectedList.add(new ListItem("1"));
        expectedList.add(new ListItem("1A"));

        assertArrayEquals(expectedList.toArray(), listConfig.getList().toArray());
    }

    @Test
    public void modeMixedNoSort() {
        ListConfig listConfig = new ListConfig();
        listConfig.setSort(ListConfig.SORT_NO_SORT);
        listConfig.setMode(ListConfig.MODE_MIXED);
        listConfig.addToList(new ListItem("B"));
        listConfig.addToList(new ListItem("A"));
        listConfig.addToList(new ListItem("C"));
        listConfig.addToList(new ListItem("1"));
        listConfig.addToList(new ListItem("1A"));

        List<ListItem> expectedList = new ArrayList<>();
        expectedList.add(new ListItem("B"));
        expectedList.add(new ListItem("A"));
        expectedList.add(new ListItem("C"));
        expectedList.add(new ListItem("1"));
        expectedList.add(new ListItem("1A"));

        assertArrayEquals(expectedList.toArray(), listConfig.getList().toArray());
    }

    @Test
    public void modeNumericNoSort() {
        ListConfig listConfig = new ListConfig();
        listConfig.setSort(ListConfig.SORT_NO_SORT);
        listConfig.setMode(ListConfig.MODE_NUMERIC);
        listConfig.addToList(new ListItem("B"));
        listConfig.addToList(new ListItem("A"));
        listConfig.addToList(new ListItem("C"));
        listConfig.addToList(new ListItem("1"));
        listConfig.addToList(new ListItem("1A"));

        List<ListItem> expectedList = new ArrayList<>();
        expectedList.add(new ListItem("1"));

        assertArrayEquals(expectedList.toArray(), listConfig.getList().toArray());
    }

    @Test
    public void modeAlphabeticNoSort() {
        ListConfig listConfig = new ListConfig();
        listConfig.setSort(ListConfig.SORT_NO_SORT);
        listConfig.setMode(ListConfig.MODE_ALPHABETIC);
        listConfig.addToList(new ListItem("B"));
        listConfig.addToList(new ListItem("A"));
        listConfig.addToList(new ListItem("C"));
        listConfig.addToList(new ListItem("1"));
        listConfig.addToList(new ListItem("1A"));

        List<ListItem> expectedList = new ArrayList<>();
        expectedList.add(new ListItem("B"));
        expectedList.add(new ListItem("A"));
        expectedList.add(new ListItem("C"));

        assertArrayEquals(expectedList.toArray(), listConfig.getList().toArray());
    }

    @Test
    public void modeNumericSortAsc() {
        ListConfig listConfig = new ListConfig();
        listConfig.setSort(ListConfig.SORT_ASC);
        listConfig.setMode(ListConfig.MODE_NUMERIC);
        listConfig.addToList(new ListItem("B"));
        listConfig.addToList(new ListItem("A"));
        listConfig.addToList(new ListItem("2"));
        listConfig.addToList(new ListItem("C"));
        listConfig.addToList(new ListItem("1"));
        listConfig.addToList(new ListItem("1A"));

        List<ListItem> expectedList = new ArrayList<>();
        expectedList.add(new ListItem("1"));
        expectedList.add(new ListItem("2"));

        assertArrayEquals(expectedList.toArray(), listConfig.getList().toArray());
    }

    @Test
    public void modeNumericSortDesc() {
        ListConfig listConfig = new ListConfig();
        listConfig.setSort(ListConfig.SORT_DESC);
        listConfig.setMode(ListConfig.MODE_NUMERIC);
        listConfig.addToList(new ListItem("B"));
        listConfig.addToList(new ListItem("A"));
        listConfig.addToList(new ListItem("2"));
        listConfig.addToList(new ListItem("C"));
        listConfig.addToList(new ListItem("1"));
        listConfig.addToList(new ListItem("1A"));

        List<ListItem> expectedList = new ArrayList<>();
        expectedList.add(new ListItem("2"));
        expectedList.add(new ListItem("1"));

        assertArrayEquals(expectedList.toArray(), listConfig.getList().toArray());
    }

    @Test
    public void modeAlphabeticSortAsc() {
        ListConfig listConfig = new ListConfig();
        listConfig.setSort(ListConfig.SORT_ASC);
        listConfig.setMode(ListConfig.MODE_ALPHABETIC);
        listConfig.addToList(new ListItem("B"));
        listConfig.addToList(new ListItem("A"));
        listConfig.addToList(new ListItem("2"));
        listConfig.addToList(new ListItem("C"));
        listConfig.addToList(new ListItem("1"));
        listConfig.addToList(new ListItem("1A"));

        List<ListItem> expectedList = new ArrayList<>();
        expectedList.add(new ListItem("A"));
        expectedList.add(new ListItem("B"));
        expectedList.add(new ListItem("C"));

        assertArrayEquals(expectedList.toArray(), listConfig.getList().toArray());
    }

    @Test
    public void modeAlphabeticSortDesc() {
        ListConfig listConfig = new ListConfig();
        listConfig.setSort(ListConfig.SORT_DESC);
        listConfig.setMode(ListConfig.MODE_ALPHABETIC);
        listConfig.addToList(new ListItem("B"));
        listConfig.addToList(new ListItem("A"));
        listConfig.addToList(new ListItem("2"));
        listConfig.addToList(new ListItem("C"));
        listConfig.addToList(new ListItem("1"));
        listConfig.addToList(new ListItem("1A"));

        List<ListItem> expectedList = new ArrayList<>();
        expectedList.add(new ListItem("C"));
        expectedList.add(new ListItem("B"));
        expectedList.add(new ListItem("A"));

        assertArrayEquals(expectedList.toArray(), listConfig.getList().toArray());
    }
}
