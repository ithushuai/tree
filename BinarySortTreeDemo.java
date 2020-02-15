package tree;

/**
 * created by it_hushuai
 * 2020/2/10 14:05
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9};
        BinarySortTree  binarySortTree = new BinarySortTree();
        for (int i : arr) {
            binarySortTree.add(new Node(i));
        }
        System.out.println("中序遍历二叉树");
        binarySortTree.infixOrder();
    }
}

class BinarySortTree {
    private Node root;

    public void add(Node node) {
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    public void infixOrder(){
        if(this.root != null){
            this.root.infixOrder();
        }else {
            System.out.println("二叉树为空");
        }
    }
}
