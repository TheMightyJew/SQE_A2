package system;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TreeTest {

    private Tree treeTest;
    private Tree root;
    private int initialSpace;
    private FileSystem fileSystem;
    private String testTreeName = "testTreeName";

    public TreeTest() {
        this.initialSpace = 20;
        fileSystem = new FileSystem(this.initialSpace);
    }

    @Before
    public void initiateTree(){
        try {
            fileSystem.dir(new String[]{"root", testTreeName});
            treeTest = fileSystem.DirExists(new String[]{"root", testTreeName});
            root = fileSystem.DirExists(new String[]{"root"});
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testConstructor(){
        assertEquals(treeTest.name, testTreeName);
    }

    @Test
    public void testGetChildByName(){
        assertEquals(root.GetChildByName(testTreeName), treeTest);
    }
    @Test
    public void testGetPath(){
        assertEquals(new String[]{"root", testTreeName}, treeTest.getPath());
    }
}
