package system;

import system.Tree;

public class TreeStub extends Tree {
    /**
     * Ctor - create tree.
     *
     * @param name The name of the root element of this tree.
     */
    public TreeStub(String name) {
        super(name);
    }

    @Override
    public Tree GetChildByName(String name) {
        return super.GetChildByName(name);
    }

    @Override
    public String[] getPath() {
        return new String[]{"root", "testDir", name};
    }
}
