package rbtree;

/**
 * created by it_hushuai
 * 2020/8/17 21:21
 */
public class RBTree<K extends Comparable<K>, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    /**
     * 根节点引用
     */
    private RBNode root;

    static class RBNode<K extends Comparable<K>, V> {
        K key;
        V value;
        RBNode left;
        RBNode right;
        RBNode parent;
        boolean color = BLACK;

        public void setValue(V value) {
            this.value = value;
        }

        public V getValue() {
            return value;
        }
    }

    /**
     * 获取节点的父节点
     *
     * @param node
     * @return
     */
    private static RBNode parentOf(RBNode node) {
        return (node == null ? null : node.parent);
    }

    /**
     * 获取节点的颜色
     *
     * @param node
     * @return
     */
    private static boolean colorOf(RBNode node) {
        return (node == null ? BLACK : node.color);
    }

    /**
     * 给节点设置颜色
     *
     * @param p
     * @param c
     */
    private static void setColor(RBNode p, boolean c) {
        if (p != null)
            p.color = c;
    }

    /**
     * 左旋
     *       p.parent             r.parent
     *        丨                   丨
     *        p     -------->      r
     *      /   \                /  \
     *   p.left  r              p    y
     *         / \            /  \
     *        x   y       p.left  x
     * p：当前操作节点
     * 1.将p的右子节点更新为r的左子节点，r的左子节点的父节点更新为p
     * 2.r替换p的位置，即r的父节点更新为p的父节点，p的父节点的子节点更新为r
     * 3.r的左子节点更新为p，p的父节点更新为r
     * 如果当前节点为null，不操作
     *
     * @param p
     */
    private void rotateLeft(RBNode p) {
        if (p != null) {
            RBNode r = p.right;
            // 1.1 将p的右子节点更新为r的左子节点
            p.right = r.left;
            // 1.2 r的左子节点的父节点更新为p
            if (r.left != null) {
                r.left.parent = p;
            }
            // 2.1 r的父节点更新为p的父节点
            r.parent = p.parent;
            // 2.2 p的父节点的子节点更新为r
            if (p.parent == null) {
                root = r;
            } else if (p.parent.left == p) {
                p.parent.left = r;
            } else {
                p.parent.right = r;
            }
            // 3 r的左子节点更新为p，p的父节点更新为r
            r.left = p;
            p.parent = r;
        }
    }

    /**
     * 右旋
     *       p.parent             r.parent
     *        丨                   丨
     *        p     -------->      l
     *      /   \                /  \
     *     l   p.right         x     p
     *   / \                       /  \
     *  x   y                     y    p.right
     *  p：当前操作节点
     *  1.将p的左子节点更新为l的右子节点，l的右子节点的父节点更新为p
     *  2.l替换p的位置，即l的父节点更新为p的父节点，p的父节点更新为l
     *  3.l的右子节点更新为p，p的父节点更新为l
     *  如果当前节点为null，不操作
     * @param p
     */
    private void rotateRight(RBNode p) {
        if (p != null) {
            RBNode l = p.left;
            p.left = l.right;
            if (l.right != null) {
                l.right.parent = p;
            }
            l.parent = p.parent;
            if (p.parent == null) {
                root = l;
            } else if (p.parent.left == p) {
                p.parent.left = l;
            } else {
                p.parent.right = l;
            }
            l.right = p;
            p.parent = l;
        }
    }

    /**
     * 插入节点
     * @param node
     */
    public void insert(RBNode node) {
        if (node == null) {
            return;
        }
        if (root == null) { // 初始化root
            root = node;
            root.color = BLACK;
            return;
        }
        RBNode x = root;
        RBNode p = null;// p用来记录x的父节点

        int cmp;
        do {
            p = x;
            cmp = node.key.compareTo(x.key);// cmp > 0：新节点比当前节点大；cmp < 0:新节点比当前节点小；否则相等
            if (cmp < 0) {// 往当前结点的左子节点遍历
                x = x.left;
            } else if (cmp > 0){ // 往右子节点遍历
                x = x.right;
            } else {
                x.setValue(node.getValue());
            }
        } while (x != null);
        node.parent = p;
        if (cmp < 0) {
            p.left = node;
        }else {
            p.right = node;
        }

        // 插入新节点后，进行调整转成红黑树
        fixAfterInsertion(node);
    }

    /**
     * 对插入新节点后的二叉树进行调整转成红黑树
     *
     * @param x
     */
    private void fixAfterInsertion(RBNode x) {
        x.color = RED; // 新插入的节点必须为红色

        // 不需要任何调整的情况：新节点的父节点为黑色
        // 如果新节点就是root节点，只需变色即可
        // 最为简单的情况，暂不予考虑
        while (x != root && x.parent.color == RED) {
            if (x.parent == x.parent.parent.left) { // 新节点的父节点是祖父节点的左子节点
                RBNode y = x.parent.parent.right; // 叔叔节点
                if (colorOf(y) == RED) { // 叔叔节点也为红色
                    // 将父节点和叔叔节点变为黑色，祖父节点变为红色，再把祖父节点当做新节点依次雷同操作，直到祖父节点为root或黑色
                    setColor(x.parent, BLACK);
                    setColor(y, BLACK);
                    setColor(x.parent.parent, RED);
                    x = x.parent.parent;
                } else { // 叔叔节点为黑色
                    // 需要判断新节点是父节点的左子节点还是右子节点
                    if (x == x.parent.right) { // 左右，先父节点左旋，之后与左左一致做法
                        x = x.parent;
                        rotateLeft(x);
                    }
                    // 左左
                    setColor(x.parent, BLACK);
                    setColor(x.parent.parent, RED);
                    // 祖父节点右旋
                    rotateRight(x.parent.parent);
                }
            } else { // 新节点的父节点是祖父节点的右子节点
                RBNode y = x.parent.parent.left;
                if (colorOf(y) == RED) {
                    setColor(x.parent, BLACK);
                    setColor(y, BLACK);
                    setColor(x.parent.parent, RED);
                } else {
                    if (x == x.parent.left) {
                        x = x.parent;
                        rotateRight(x);
                    }
                    setColor(x.parent, BLACK);
                    setColor(x.parent.parent, RED);
                    rotateLeft(x.parent.parent);
                }
            }
        }
    }
}
