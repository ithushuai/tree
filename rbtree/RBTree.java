package tree.rbtree;

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
    private RBNode<K, V> root;

    static class RBNode<K extends Comparable<K>, V> {
        K key;
        V value;
        RBNode<K, V> left;
        RBNode<K, V> right;
        RBNode<K, V> parent;
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

    private static RBNode leftOf(RBNode node) {
        return (node == null) ? null: node.left;
    }

    private static RBNode rightOf(RBNode node) {
        return (node == null) ? null: node.right;
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

    /**
     * 删除节点，并返回删除的节点
     * @param key
     * @return
     */
    public V remove(K key) {
        RBNode<K, V> p = getRBNode(key);// 根据key找到待删除节点
        if (p == null) {
            return null;
        }
        V oldValue = p.value;
        deleteRBNode(p);
        return oldValue;
    }

    /**
     * 删除指定节点
     * 删除：
     * 1.待删除节点没有子节点
     * 2.待删除节点只有一个子节点
     * 3.待删除节点有两个子节点
     * 当待删除节点有两个子节点时，用其后继节点的值替代待删除节点，然后转化为删除后继节点，后继节点只有一个或者没有子节点
     * @param p
     */
    private void deleteRBNode(RBNode<K,V> p) {
        // 如果待删除结点有两个子节点，需要找到其后继节点，然后转化为删除后继节点
        if (p.left != null && p.right != null) {
            // 1.找到后继节点
            RBNode<K, V> s = successor(p);
            // 2.用后继节点的属性替换待删除节点（键和值）
            p.key = s.key;
            p.value = s.value;
            // 3.p指向后继节点，接下来就是对后继节点进行删除
            p = s;
        }
        // 如果待删除节点有一个子节点，可以用待删除节点的子节点替换待删除节点的位置，再对剩下的节点做修正
        RBNode<K, V> r = (p.left != null ? p.left : p.right);// r是p的子节点，优先找左子节点，如果没有则取右子节点
        if (r != null) { // 有一个子节点
            r.parent = p.parent;
            if (p.parent == null) {
                r = root;
            } else if (p.parent.left == p) {
                p.parent.left = r;
            } else {
                p.parent.right = r;
            }
            p.left = p.right = p.parent = null;
            if (p.color == BLACK) { // 如果p为黑，删除p后路径上黑色-1，则需要进行修正
                fixAfterDeletion(r);
            }
        } else if (p.parent == null) {
            root = null;
        } else { // 没有子节点
            if (p.color == BLACK) { // 待删除节点为黑色，且没有子节点，先对节点进行修正
                fixAfterDeletion(p);
            }
            // 最后删除p
            if (p.parent != null) {
                if (p.parent.left == p) {
                    p.parent.left = null;
                } else {
                    p.parent.right = null;
                }
                p.parent = null;
            }
        }
    }

    /**
     * 修正红黑树（删除）
     * 针对的节点没有子节点,且需要调整颜色或者旋转的情形
     * 在修正时，由于待操作节点并没有真正删除，所以可以看成是黑色值-1
     * @param x
     */
    private void fixAfterDeletion(RBNode<K,V> x) {
        while (x != root && colorOf(x) == BLACK) { // “向上追溯”
            if (x == leftOf(parentOf(x))) { // 需要修正的节点是父节点的左子节点
                RBNode<K,V> xr = rightOf(parentOf(x));// 兄弟节点
                if (colorOf(xr) == RED) { // 情形九，转化成情形二三四
                    setColor(xr, BLACK);
                    setColor(parentOf(x), RED);
                    rotateLeft(parentOf(x));
                    xr = rightOf(parentOf(x));
                }
                // 情形一在父节点变红后，就退出循环，然后将父节点涂黑；情形五，需要向上追溯，直到不再是情形五
                if (colorOf(leftOf(xr)) == BLACK
                        && colorOf(rightOf(xr)) == BLACK) {
                    setColor(xr, RED);
                    x = parentOf(x);
                } else { // 无须向上追溯
                    if (colorOf(rightOf(xr)) == BLACK) { // 情形二转化为情形三、情形六转化为情形七
                        setColor(xr, RED);
                        setColor(leftOf(xr), BLACK);
                        rotateRight(xr);
                        xr = rightOf(parentOf(x));
                    }
                    // 转化后的共性操作
                    setColor(xr, colorOf(parentOf(x)));
                    setColor(rightOf(xr), BLACK);
                    setColor(parentOf(x), BLACK);
                    rotateLeft(parentOf(x));
                    x = root; // 跳出循环
                }
            } else { // 对称
                RBNode<K,V> xl = x.parent.left;
                if (colorOf(xl) == RED) {
                    setColor(xl, BLACK);
                    setColor(parentOf(x), RED);
                    rotateRight(parentOf(x));
                    xl = leftOf(parentOf(x));
                }

                if (colorOf(rightOf(xl)) == BLACK &&
                        colorOf(leftOf(xl)) == BLACK) {
                    setColor(xl, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(xl)) == BLACK) {
                        setColor(rightOf(xl), BLACK);
                        setColor(xl, RED);
                        rotateLeft(xl);
                        xl = leftOf(parentOf(x));
                    }
                    setColor(xl, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(xl), BLACK);
                    rotateRight(parentOf(x));
                    x = root;
                }
            }
        }
        setColor(x, BLACK);
    }

    /**
     * 返回某个节点的后继节点
     * @param t
     * @return
     */
    private RBNode<K,V> successor(RBNode<K,V> t) {
        if (t == null) {
            return null;
        }
        // 如果t有右子节点，则后继节点就是t的右子树的最左侧节点（右子树最小的节点）
        // 如果t没有右子节点，那么有两种情况：
        // 1.t是其父节点的左子节点，那么他的后继节点就是父节点
        // 2.t是其父节点的右子节点，那么就不断往上追溯找父节点，看是否能找到一个节点p，使得t在p的左子树中，那么首次找到的p
        // 就是t的后继节点，因为此时p的键是比t键大的最小节点。如果找到根节点都找不到符合条件的p，那么说明t在根节点的右侧，
        // 那么t的后继节点只能是null
        if (t.right != null) {
            RBNode<K, V> s = t.right;
            while (s.left != null) {
                s = s.left;
            }
            return s;
        } else { // 这种场景用不到，这里只是求到了比当前节点大的所有节点中最小的
            RBNode<K, V> p = t.parent;
            RBNode<K, V> cur = t;
            while (p != null && p.right == cur) {
                cur = p;
                p = p.parent;
            }
            return p;
        }
    }

    private RBNode<K, V> getRBNode(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        RBNode<K, V> p = root;
        while (p != null) {
            int cmp = key.compareTo(p.key);
            if (cmp > 0) {
                p = p.right;
            } else if (cmp < 0) {
                p = p.left;
            } else {
                return p;
            }
        }
        return null;
    }
}
