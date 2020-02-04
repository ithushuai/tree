package tree;

/**
 * created by it_hushuai
 * 2020/2/3 20:03
 */
public class BinaryTree {
    private Node root;

    public void preOrder(){
        if(this.root != null){
            this.root.preOrder();
        }else {
            System.out.println("二叉树为空");
        }
    }

    public void infixOrder(){
        if(this.root != null){
            this.root.infixOrder();
        }else {
            System.out.println("二叉树为空");
        }
    }

    public void postOrder(){
        if(this.root != null){
            this.root.postOrder();
        }else {
            System.out.println("二叉树为空");
        }
    }

    /**
     * 前序查找节点
     * @param no
     * @return
     */
    public Node preOrderSearch(int no){
        if(this.root != null){
            return this.root.preOrderSearch(no);
        }else {
            return null;
        }
    }

    public void delNode(int no){
        if(this.root != null){
            if(this.root.no == no){
                this.root = null;
            }else {
                this.root.delNode(no);
            }
        }else {
            System.out.println("空树，不能删除!");
        }
    }


    public BinaryTree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
