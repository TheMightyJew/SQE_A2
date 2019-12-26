package system;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

import java.nio.file.DirectoryNotEmptyException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FileSystemTest {


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {10,4}, {20,2}
        });
    }

    private Space space;
    private FileSystem  fileSystem;
    private int initialSpace;
    private int testLeafSize;
    private String testTreeName = "testTreeName";
    private String testLeafName = "testLeafName";
    private int expectedFreeSpace;

    public FileSystemTest(int initialSpace,int testLeafSize) {
        this.initialSpace = initialSpace;
        this.testLeafSize = testLeafSize;
        fileSystem = new FileSystem(this.initialSpace);
    }

    @Before
    public void initialFileSystem(){
        fileSystem = new FileSystem(this.initialSpace);
        expectedFreeSpace = this.initialSpace;
        space = FileSystem.fileStorage;
        try {
            fileSystem.dir(new String[]{"root", testTreeName});
            fileSystem.file(new String[]{"root", testTreeName, testLeafName}, testLeafSize);
            expectedFreeSpace -= testLeafSize;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor(){
        assertEquals("root", fileSystem.DirExists(new String[]{"root"}).name);
        assertEquals(expectedFreeSpace, space.countFreeSpace());
    }

    @Test
    public void testDir(){
        assertTrue(fileSystem.DirExists(new String[]{"root"}) != null);
        try{
            fileSystem.dir(new String[]{"root", testTreeName, testLeafName});
            assertTrue(false);
        }
        catch (BadFileNameException e){
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
        try{
            fileSystem.dir(new String[]{testTreeName});
            assertTrue(false);
        }
        catch (BadFileNameException e){
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
        try{
            fileSystem.dir(new String[]{"root", testTreeName});
            assertTrue(fileSystem.DirExists(new String[]{"root", testTreeName}) != null);
        }
        catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void testFile(){
        assertTrue(fileSystem.FileExists(new String[]{"root", testTreeName, testLeafName}) != null);
        try{
            fileSystem.file(new String[]{"root", testTreeName}, testLeafSize);
            assertTrue(false);
        }
        catch (BadFileNameException e){
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
        try{
            fileSystem.file(new String[]{testLeafName}, testLeafSize);
            assertTrue(false);
        }
        catch (BadFileNameException e){
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
        try{
            fileSystem.file(new String[]{"root", testTreeName, testLeafName + "spaceTest"},initialSpace+1);
            assertTrue(false);
        }
        catch (OutOfSpaceException e){
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
        try{
            fileSystem.file(new String[]{"root", testTreeName, testLeafName},initialSpace+1);
            assertTrue(false);
        }
        catch (OutOfSpaceException e){
            assertTrue(true);
            assertTrue(fileSystem.FileExists(new String[]{"root", testTreeName, testLeafName}) != null);
        }
        catch (Exception e){
            assertTrue(false);
        }
        try{
            fileSystem.file(new String[]{"root", testTreeName, testLeafName},testLeafSize-1);
            assertEquals(testLeafSize-1, fileSystem.FileExists(new String[]{"root", testTreeName, testLeafName}).size);
            fileSystem.file(new String[]{"root", testTreeName, testLeafName},testLeafSize+1);
            assertEquals(testLeafSize-1, fileSystem.FileExists(new String[]{"root", testTreeName, testLeafName}).size);
        }
        catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void testRmfile(){
        try{
            fileSystem.rmfile(new String[]{"root", testTreeName, testLeafName + "some_lie"});
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
        try{
            fileSystem.rmfile(new String[]{"root", testTreeName, testLeafName});
            expectedFreeSpace += testLeafSize;
            assertEquals(expectedFreeSpace, space.countFreeSpace());
        }
        catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void testRmdir(){
        try{
            fileSystem.rmdir(new String[]{"root", testTreeName + "some_lie"});
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
        try{
            fileSystem.rmdir(new String[]{"root", testTreeName});
            assertTrue(false);
        }
        catch (DirectoryNotEmptyException e){
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }
        try{
            fileSystem.dir(new String[]{"root", testTreeName + "test"});
            assertTrue(fileSystem.DirExists(new String[]{"root", testTreeName + "test"}) != null);
            fileSystem.rmdir(new String[]{"root", testTreeName + "test"});
            assertTrue(fileSystem.DirExists(new String[]{"root", testTreeName + "test"}) == null);
        }
        catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void testLsdir(){
        try{
            assertTrue(fileSystem.lsdir(new String[]{"some_lie"}) == null);
        }
        catch (Exception e){
            assertTrue(false);
        }
        try{
            String[] res = fileSystem.lsdir(new String[]{"root"});
            assertEquals(1, res.length);
            assertEquals(testTreeName, res[0]);
        }
        catch (Exception e){
            assertTrue(false);
        }
        try{
            String[] res = fileSystem.lsdir(new String[]{"root", testTreeName});
            assertEquals(1, res.length);
            assertEquals(testLeafName, res[0]);        }
        catch (Exception e){
            assertTrue(false);
        }
    }

    @Test
    public void testDisk(){
        try{
            int count = 0;
            for(String[] path : fileSystem.disk()){
                if(count<testLeafSize){
                    assertTrue(path != null);
                }
                else{
                    assertTrue(path == null);
                }
                count++;
            }
        }
        catch (Exception e){
            assertTrue(false);
        }
    }
}
