import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HashTableTest {
    @Test
    void constructorTest() {
        HashTable<Integer> ht = new HashTable<>();
        assertEquals(0, ht.getSize());
        assertEquals(4, ht.getCapacity());
    }

    @Test
    void insertLookupTest() {
        HashTable<Integer> ht = new HashTable<>();
        ht.insert("a", 1);
        assertEquals(1, ht.lookup("a"));
        assertEquals(1, ht.getSize());
    }

    @Test
    void capacityGrowTest() {
        HashTable<Integer> ht = new HashTable<>();
        ht.insert("a", 1);
        ht.insert("b", 2);
        ht.insert("c", 3);
        assertEquals(8, ht.getCapacity());
        assertEquals(3, ht.getSize());
    }

    @Test
    void updateValueTest() {
        HashTable<String> ht = new HashTable<>();
        ht.insert("a", "x");
        ht.insert("a", "y");
        assertEquals(1, ht.getSize());
        assertEquals("y", ht.lookup("a"));
    }

    @Test
    void validKeysTest() {
        HashTable<Integer> ht = new HashTable<>();
        ht.insert("a", 1);
        ht.insert("b", 2);
        String[] vk = ht.getValidKeys();
        Set<String> s = new HashSet<>(Arrays.asList(vk));
        assertTrue(s.contains("a"));
        assertTrue(s.contains("b"));
        assertEquals(2, vk.length);
    }

    @Test
    void deleteExistingTest() {
        HashTable<Integer> ht = new HashTable<>();
        ht.insert("a", 1);
        ht.insert("b", 2);
        ht.insert("c", 3);
        int removedIdx = ht.delete("b");
        assertTrue(removedIdx >= 0 && removedIdx < ht.getCapacity());
        assertNull(ht.lookup("b"));
        assertEquals(2, ht.getSize());
    }

    @Test
    void deleteNonexistentTest() {
        HashTable<Integer> ht = new HashTable<>();
        ht.insert("a", 1);
        int sizeBefore = ht.getSize();
        int capBefore = ht.getCapacity();
        int idx = ht.delete("z");
        assertTrue(idx >= 0 && idx < capBefore);
        assertEquals(sizeBefore, ht.getSize());
        assertEquals(capBefore, ht.getCapacity());
    }

    @Test
    void capacityShrinkTest() {
        HashTable<Integer> ht = new HashTable<>();
        ht.insert("a", 1);
        ht.insert("b", 2);
        ht.insert("c", 3);
        assertEquals(8, ht.getCapacity());
        ht.delete("b");
        ht.delete("c");
        assertEquals(4, ht.getCapacity());
        assertEquals(1, ht.getSize());
        assertNull(ht.lookup("b"));
        assertNull(ht.lookup("c"));
        assertEquals(1, ht.lookup("a"));
    }
}