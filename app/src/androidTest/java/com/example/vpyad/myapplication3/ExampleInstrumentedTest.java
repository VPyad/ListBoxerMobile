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

    @Test
    public void numericModeWithMixedInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_NUMERIC);

        String input = "Apple1";

        assertEquals(false, listConfig.addToList(new ListItem(input)));
    }

    @Test
    public void numericModeWithNumericInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_NUMERIC);

        String input = "123";

        assertEquals(true, listConfig.addToList(new ListItem(input)));
    }

    @Test
    public void mixedModeWithIllegalInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_MIXED);

        String input = "!.?";

        assertEquals(false, listConfig.addToList(new ListItem(input)));
    }

    @Test
    public void mixedModeWithMixedInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_MIXED);

        String input = "Apple123";

        assertEquals(true, listConfig.addToList(new ListItem(input)));
    }

    @Test
    public void mixedModeWithMixedIllegalInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_MIXED);

        String input = "!Apple123";

        assertEquals(false, listConfig.addToList(new ListItem(input)));
    }

    @Test
    public void alphabeticModeWithAlphabeticInput() {
        ListConfig listConfig = new ListConfig();
        listConfig.setMode(ListConfig.MODE_ALPHABETIC);

        String input = "Qwerty";

        assertEquals(true, listConfig.addToList(new ListItem(input)));
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
    public void modeAlpabeticSortDesc() {
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
