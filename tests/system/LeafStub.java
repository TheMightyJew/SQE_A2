package system;

import system.Leaf;
import system.OutOfSpaceException;

public class LeafStub extends Leaf {
    /**
     * Ctor - create leaf.
     *
     * @param name Name of the leaf.
     * @param size Size, in KB, of the leaf.
     * @throws OutOfSpaceException Allocating space failed.
     */
    public LeafStub(String name, int size) throws OutOfSpaceException {
        super(name, size);
    }

    @Override
    public String[] getPath() {
        return new String[]{"root", "testDir", name};
    }
}
