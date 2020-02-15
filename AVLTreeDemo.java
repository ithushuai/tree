package tree;

/**
 * created by it_hushuai
 * 2020/2/15 14:27
 */
public class AVLTreeDemo {
    public static void main(String[] args) {
//        int[] arr = {4, 3, 6, 5, 7, 8};
//        int[] arr = {10, 12, 8, 9, 7, 6};
        int[] arr = {10, 11, 7, 6, 8, 9};
        AVLTree avlTree = new AVLTree();
        for (int i : arr) {
            avlTree.add(new Node(i));
        }
//        avlTree.infixOrder();
        System.out.println("平衡之后树的高度：" + avlTree.height());
        System.out.println("平衡之后左子树树的高度：" + avlTree.getRoot().leftNode.height());
        System.out.println("平衡之后右子树树的高度：" + avlTree.getRoot().rightNode.height());
        System.out.println("当前根节点：" + avlTree.getRoot().no);

    }
}

class AVLTree {
    private Node root;

    public void add(Node node) {
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    public int height() {
        return root.height();
    }

    public void infixOrder() {
        if (this.root != null) {
            this.root.infixOrder();
        } else {
            System.out.println("二叉树为空");
        }
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}


