package tree;

/**
 * created by it_hushuai
 * 2020/2/3 19:56
 */
public class Node implements Comparable<Node> {
    int no;
    String name;
    Node leftNode;
    Node rightNode;

    /**
     * 前序遍历
     */
    public void preOrder() {
        System.out.println(this);
        if (this.leftNode != null) {
            this.leftNode.preOrder();
        }
        if (this.rightNode != null) {
            this.rightNode.preOrder();
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        if (this.leftNode != null) {
            this.leftNode.infixOrder();
        }
        System.out.println(this);
        if (this.rightNode != null) {
            this.rightNode.infixOrder();
        }
    }

    /**
     * 后续遍历
     */
    public void postOrder() {
        if (this.leftNode != null) {
            this.leftNode.postOrder();
        }
        if (this.rightNode != null) {
            this.rightNode.postOrder();
        }
        System.out.println(this);
    }

    /**
     * 前序查找
     *
     * @param no
     * @return
     */
    public Node preOrderSearch(int no) {
        if (this.no == no) {
            return this;
        }
        Node node = null;
        if (this.leftNode != null) {
            node = this.leftNode.preOrderSearch(no);
        }
        if (node != null) {
            return node;
        }
        if (this.rightNode != null) {
            node = this.rightNode.preOrderSearch(no);
        }
        return node;
    }

    /**
     * 中序查找
     *
     * @param no
     * @return
     */
    public Node infixOrderSearch(int no) {
        Node node = null;
        if (this.leftNode != null) {
            node = this.leftNode.preOrderSearch(no);
        }
        if (node != null) {
            return node;
        }
        if (this.no == no) {
            return this;
        }
        if (this.rightNode != null) {
            node = this.rightNode.preOrderSearch(no);
        }
        return node;
    }

    /**
     * 后续查找
     *
     * @param no
     * @return
     */
    public Node postOrderSearch(int no) {
        Node node = null;
        if (this.leftNode != null) {
            node = this.leftNode.preOrderSearch(no);
        }
        if (node != null) {
            return node;
        }
        if (this.rightNode != null) {
            node = this.rightNode.preOrderSearch(no);
        }
        if (this.no == no) {
            return this;
        }
        return node;
    }

    public void delNode(int no) {
        if (this.leftNode != null && this.leftNode.no == no) {
            this.leftNode = null;
            return;
        }
        if (this.rightNode != null && this.rightNode.no == no) {
            this.rightNode = null;
            return;
        }
        if (this.leftNode != null) {
            this.leftNode.delNode(no);
        }
        if (this.rightNode != null) {
            this.rightNode.delNode(no);
        }
    }

    /**
     * 二叉排序树添加节点
     *
     * @param node
     */
    public void add(Node node) {
        if (node == null) {
            return;
        }
        if (node.no < this.no) {
            if (this.leftNode == null) {
                this.leftNode = node;
            } else {
                this.leftNode.add(node);
            }
        } else {
            if (this.rightNode == null) {
                this.rightNode = node;
            } else {
                this.rightNode.add(node);
            }
        }

         //当添加完一个节点后，如果：右子树的高度-左子树的高度 > 1， 左旋转
        if(rightHeight() - leftHeight() > 1){//左旋转
            //如果当前节点的右子树的左子树高度比右子树高度高，则需要先把当前节点的右子树先右旋转平衡一下，否则整树平衡后仍不不是平衡树
            if(rightNode !=null &&rightNode.leftHeight()>rightNode.rightHeight()){
                rightNode.rightRotate();
                //再进行整树左旋转
                leftRotate();
            }else {
                this.leftRotate();
            }
        }

        if (leftHeight() - rightHeight() > 1){//右旋转
            //同理
            if(leftNode !=null && leftNode.rightHeight()>leftNode.leftHeight()){
                leftNode.leftRotate();
                //再进行整树右旋转
                rightRotate();
            }else {
                this.rightRotate();
            }
        }
    }

    /**
     *  返回以该节点为根节点的数的高度
     * @return
     */
    public int height() {
        return Math.max(leftNode == null ? 0 : leftNode.height(), rightNode == null ? 0 : rightNode.height()) + 1;
    }

    /**
     * 返回左子树的高度
     * @return
     */
    public int leftHeight(){
        if(leftNode == null){
            return 0;
        }
        return leftNode.height();
    }

    /**
     * 返回右子树的高度
     * @return
     */
    public int rightHeight(){
        if(rightNode == null){
            return 0;
        }
        return rightNode.height();
    }

    /**
     * 左旋转形成平衡树
     * 提升右子节点为根节点
     */
    public void leftRotate(){
        //1.创建一个新节点newNode，值等于当前根节点的值
        Node newNode = new Node(this.no);
        //2.把新节点的左子树设置成当前节点的左子树
        newNode.leftNode = this.leftNode;
        //3.把新节点的右子树设置为当前节点的右子树的左子树
        newNode.rightNode = this.rightNode.leftNode;
        //4.把当前节点的值换成右子节点的值
        this.no = this.rightNode.no;
        //5.把当前节点的右子树设置为右子树的右子树
        this.rightNode = this.rightNode.rightNode;
        //6.把当前节点的左子树设置为新节点
        this.leftNode = newNode;
    }

    /**
     * 右旋转形成平衡树
     * 提升左子节点为根节点
     */
    public void rightRotate(){
        //1.创建一个新节点newNode， 值为根节点的值
        Node newNode = new Node(this.no);
        //2.新节点的右子树是当前节点的右子树
        newNode.rightNode = rightNode;
        //3.新节点的左子树是当前节点左子树的右子树
        newNode.leftNode = leftNode.rightNode;
        //4.将当前节点的值改成左子数根节点的值
        this.no = leftNode.no;
        //5.当前节点的左子树为左子节点的左子树
        leftNode = leftNode.leftNode;
        //6.当前节点的右子树为newNode
        rightNode = newNode;
    }

    public Node(int no) {
        this.no = no;
    }

    public Node(int no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return this.no - o.no;
    }
}
