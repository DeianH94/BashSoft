import main.bg.softuni.contracts.SimpleOrderedBag;
import main.bg.softuni.dataStructures.SimpleSortedList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SimpleOrderedBagTest {

    private SimpleOrderedBag<String> names;

    @Before
    public void setUp() {
        this.names = new SimpleSortedList<>(String.class);
    }

    @Test
    public void testEmptyConstructor() {
        int expectedCapacity = 16;
        int expectedSize = 0;

        this.names = new SimpleSortedList<>(String.class);
        int actualCapacity = this.names.capacity();
        int actualSize = this.names.size();

        Assert.assertEquals(expectedCapacity, actualCapacity);
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testConstructorWithInitialCapacity(){
        int expectedCapacity = 20;
        int expectedSize = 0;

        this.names = new SimpleSortedList<>(String.class, expectedCapacity);
        int actualCapacity = this.names.capacity();
        int actualSize = this.names.size();

        Assert.assertEquals(expectedCapacity, actualCapacity);
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testConstructorWithInitialComparator(){
        int expectedCapacity = 16;
        int expectedSize = 0;

        this.names = new SimpleSortedList<>(String.class,
                String.CASE_INSENSITIVE_ORDER);
        int actualCapacity = this.names.capacity();
        int actualSize = this.names.size();

        Assert.assertEquals(expectedCapacity, actualCapacity);
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testCtorWhitAllParams() {
        int expectedCapacity = 30;
        int expectedSize = 0;

        this.names = new SimpleSortedList<>(
                String.class,
                String.CASE_INSENSITIVE_ORDER,
                30);
        int actualCapacity = this.names.capacity();
        int actualSize = this.names.size();

        Assert.assertEquals(expectedCapacity, actualCapacity);
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testAddIncreasesSize() {
        int expectedSize = 1;

        this.names.add("Nasko");
        int actualSize = this.names.size();

        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullThrowsException() {
        this.names.add(null);
    }

    @Test
    public void testAddUnsortedDataIsHeldSorted() {
        String[] expected = {"Balkan", "Georgi", "Rosen"};
        this.names.add("Rosen");
        this.names.add("Georgi");
        this.names.add("Balkan");

        int index = 0;
        for (String name : this.names) {
            Assert.assertEquals(expected[index++], name);
        }
    }

    @Test
    public void testAddingMoreThanInitialCapacityShouldDoubleTheCapacity(){
        int expectedSize = 17;
        int expectedCapacity = 32;

        for (int i = 0; i < expectedSize; i++) {
            this.names.add(String.format("%d", i));
        }

        int actualCapacity = this.names.capacity();
        int actualSize = this.names.size();

        Assert.assertEquals(expectedCapacity, actualCapacity);
        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testAddingAllFromCollectionIncreasesSize(){
        int expectedSize = 2;

        this.names.addAll(Arrays.asList("ivan", "georgi"));
        int actualSize = this.names.size();

        Assert.assertEquals(expectedSize, actualSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingAllFromNullThrowsException() {
        this.names.addAll(Arrays.asList(null, null, null));
    }

    @Test
    public void testAddAllKeepsSorted(){
        List<String> elements = Arrays.asList(
                "ivan",
                "georgi",
                "dragan",
                "zlatko",
                "deian");
        this.names.addAll(elements);
        Collections.sort(elements);
        int index = 0;

        for (String name : names) {
            Assert.assertEquals(elements.get(index++), name);
        }
    }

    @Test
    public void testRemoveValidElementDecreasesSize() {
        int expectedSize = 1;

        this.names.addAll(Arrays.asList("ivan", "nasko"));
        this.names.remove("ivan");
        int actualSize = this.names.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testRemoveValidElementRemovesSelectedOne(){
        String firstName = "ivan";
        String secondName = "nasko";
        this.names.addAll(Arrays.asList(firstName, secondName));
        this.names.remove(firstName);

        for (String name : names) {
            assertEquals(secondName, name);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemovingNullThrowsException(){
        this.names.addAll(Arrays.asList("ivan", "nasko"));
        this.names.remove(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testJoinWithNullShouldThrow(){
        this.names.addAll(Arrays.asList("ivan", "nasko"));
        this.names.joinWith(null);
    }

    @Test
    public void testJoinWorksFine(){
        String expectedResult = "ivan, nasko, zlatko";

        this.names.addAll(Arrays.asList("ivan", "nasko", "zlatko"));
        String actualResult = this.names.joinWith(", ");

        assertEquals(expectedResult, actualResult);
    }
}