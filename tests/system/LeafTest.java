package system;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class LeafTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {4}, {2}
        });
    }

    private Leaf leaf;
    private int initialSpace;
    private int testLeafSize;
    private FileSystem fileSystem;
    private String testLeafName = "a";

    public LeafTest(int testLeafSize) {
        this.initialSpace = 20;
        this.testLeafSize = testLeafSize;
        fileSystem = new FileSystem(this.initialSpace);
    }

    @Before
    public void initialSpace(){
        try {
            fileSystem.file(new String[]{"root", testLeafName}, testLeafSize);
            leaf = FileSystem.fileStorage.getAlloc()[0];
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testConstructor(){
        assertEquals(leaf.name,testLeafName);
        assertEquals(leaf.size,testLeafSize);
    }

    @Test
    public void testGetPath(){
        assertEquals(new String[]{"root", testLeafName},leaf.getPath());
    }
}
