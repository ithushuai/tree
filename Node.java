package tree;

/**
 * created by it_hushuai
 * 2020/2/3 19:56
 */
public class Node implements Comparable<Node>{
    int no;
    String name;
    Node leftNode;
    Node rightNode;

    /**
     * 前序遍历
     */
    public void preOrder(){
        System.out.println(this);
        if(this.leftNode != null){
            this.leftNode.preOrder();
        }
        if(this.rightNode != null){
            this.rightNode.preOrder();
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder(){
        if(this.leftNode != null){
            this.leftNode.infixOrder();
        }
        System.out.println(this);
        if(this.rightNode != null){
            this.rightNode.infixOrder();
        }
    }

    /**
     * 后续遍历
     */
    public void postOrder(){
        if(this.leftNode != null){
            this.leftNode.postOrder();
        }
        if(this.rightNode != null){
            this.rightNode.postOrder();
        }
        System.out.println(this);
    }

    /**
     * 前序查找
     * @param no
     * @return
     */
    public Node preOrderSearch(int no){
        if(this.no == no){
            return this;
        }
        Node node = null;
        if(this.leftNode != null){
            node = this.leftNode.preOrderSearch(no);
        }
        if(node != null){
            return node;
        }
        if(this.rightNode != null){
            node = this.rightNode.preOrderSearch(no);
        }
        return node;
    }

    /**
     * 中序查找
     * @param no
     * @return
     */
    public Node infixOrderSearch(int no){
        Node node = null;
        if(this.leftNode != null){
            node = this.leftNode.preOrderSearch(no);
        }
        if(node != null){
            return node;
        }
        if(this.no == no){
            return this;
        }
        if(this.rightNode != null){
            node = this.rightNode.preOrderSearch(no);
        }
        return node;
    }

    /**
     * 后续查找
     * @param no
     * @return
     */
    public Node postOrderSearch(int no){
        Node node = null;
        if(this.leftNode != null){
            node = this.leftNode.preOrderSearch(no);
        }
        if(node != null){
            return node;
        }
        if(this.rightNode != null){
            node = this.rightNode.preOrderSearch(no);
        }
        if(this.no == no){
            return this;
        }
        return node;
    }

    public void delNode(int no){
        if(this.leftNode != null && this.leftNode.no == no){
            this.leftNode = null;
            return;
        }
        if(this.rightNode != null && this.rightNode.no == no){
            this.rightNode = null;
            return;
        }
        if(this.leftNode != null){
            this.leftNode.delNode(no);
        }
        if(this.rightNode != null){
            this.rightNode.delNode(no);
        }
    }

    public Node(int no){
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
