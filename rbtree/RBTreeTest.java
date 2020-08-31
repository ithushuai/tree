package rbtree;

import java.util.Scanner;

/**
 * created by it_hushuai
 * 2020/8/20 23:01
 */
public class RBTreeTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RBTree<String, Object> tree = new RBTree<>();
        while (true) {
            System.out.println("请输入key：");
            String key = scanner.next();
            RBTree.RBNode rbNode = new RBTree.RBNode();
            rbNode.key = key;
            tree.insert(rbNode);
        }
    }
}
