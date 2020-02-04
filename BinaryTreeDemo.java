package tree;

/**
 * created by it_hushuai
 * 2020/2/3 20:06
 */
public class BinaryTreeDemo {
    public static void main(String[] args) {
        Node node1 = new Node(1, "张三");
        Node node2 = new Node(2, "李四");
        Node node3 = new Node(3, "王五");
        Node node4 = new Node(4, "赵六");

        node1.leftNode = node2;
        node2.leftNode = node3;
        node1.rightNode = node4;

        BinaryTree tree = new BinaryTree(node1);

//        tree.preOrder();
//        tree.infixOrder();
//        tree.postOrder();
//        Node node = tree.preOrderSearch(3);
//        System.out.println(node);
        tree.delNode(3);
        tree.preOrder();
    }
}
