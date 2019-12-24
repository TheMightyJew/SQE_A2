package system;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SpaceTest {


    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {10,4}, {20,2}
        });
    }

    private Space space;
    private FileSystem  fileSystem;
    private int initialSpace;
    private int testLeafSize;
    private String testLeafName = "a";
    private int expectedFreeSpace;

    public SpaceTest(int initialSpace,int testLeafSize) {
        this.initialSpace = initialSpace;
        this.testLeafSize = testLeafSize;
        fileSystem = new FileSystem(this.initialSpace);
    }

    @Before
    public void initialSpace(){
        fileSystem = new FileSystem(this.initialSpace);
        expectedFreeSpace = this.initialSpace;
        space = FileSystem.fileStorage;
    }

    @Test
    public void testConstructor(){
        assertEquals(initialSpace, space.countFreeSpace());
    }

    @Test
    public void testAlloc(){
        try {
            addLeaf();
            assertEquals(expectedFreeSpace, space.countFreeSpace());
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    private void addLeaf() throws OutOfSpaceException, BadFileNameException {
        fileSystem.file(new String[]{"root", testLeafName},testLeafSize);
        expectedFreeSpace -= testLeafSize;
    }

    @Test
    public void testDealloc(){
        try {
            addLeaf();
            removeLeaf();
            assertEquals(expectedFreeSpace, space.countFreeSpace());
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    private void removeLeaf() throws BadFileNameException {
        fileSystem.rmfile(new String[]{"root", testLeafName});
        expectedFreeSpace += testLeafSize;
    }


    @Test
    public void testCountFreeSpace(){
        assertEquals(expectedFreeSpace, space.countFreeSpace());
    }

}
