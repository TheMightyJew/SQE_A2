package system;

import system.Leaf;
import system.OutOfSpaceException;
import system.Space;

public class SpaceStub extends Space {
    /**
     * Ctor - create \c size blank filesystem blocks.
     *
     * @param size
     */
    public SpaceStub(int size) {
        super(size);
    }

    @Override
    public void Alloc(int size, Leaf file) throws OutOfSpaceException {
        super.Alloc(size, file);
    }

    @Override
    public void Dealloc(Leaf file) {
        super.Dealloc(file);
    }

    @Override
    public int countFreeSpace() {
        return super.countFreeSpace();
    }

    @Override
    public Leaf[] getAlloc() {
        return super.getAlloc();
    }
}
