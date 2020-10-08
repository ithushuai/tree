package tree.rbtree;

import java.util.Scanner;
import java.util.TreeMap;

/**
 * created by it_hushuai
 * 2020/8/20 23:01
 */
public class RBTreeTest {
    public static void main(String[] args) {
        TreeMap treeMap = new TreeMap();
        treeMap.put(1, 1);
        treeMap.put(2, 2);
        treeMap.put(3, 3);
        treeMap.put(4, 4);
        treeMap.put(5, 5);
        treeMap.put(6, 6);

        treeMap.remove(1);

        RBTree<String, Object> tree = new RBTree<>();
        for (int i = 1; i <= 6; i++) {
            RBTree.RBNode rbNode = new RBTree.RBNode();
            rbNode.key = i + "";
            tree.insert(rbNode);
        }
        tree.remove("1");
        System.out.println();
    }
}
