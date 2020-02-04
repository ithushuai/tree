package tree;

import java.util.ArrayList;
import java.util.Collections;

/**
 * created by it_hushuai
 * 2020/2/4 16:07
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        Node huffmanTree = createHuffmanTree(arr);
        huffmanTree.preOrder();
    }

    public static Node createHuffmanTree(int[] arr) {
        //1.将每一个arr元素构建成Node，再将node添加到集合中
        ArrayList<Node> treeList = new ArrayList<>();
        for (int i : arr) {
            Node node = new Node(i);
            treeList.add(node);
        }
        while (treeList.size() > 1){
            //2.将集合内的节点按照权从小到大排序
            Collections.sort(treeList);
            //3.取出集合的前两个元素,并组合成一个新的二叉树
            Node leftNode = treeList.get(0);
            Node rightNode = treeList.get(1);
            Node parentNode = new Node(leftNode.no + rightNode.no);
            parentNode.leftNode = leftNode;
            parentNode.rightNode = rightNode;
            //4.将将原集合中前两个节点删除，将新节点添加进去
            treeList.remove(leftNode);
            treeList.remove(rightNode);
            treeList.add(parentNode);
        }
        //得到最终的一个根节点
        return treeList.get(0);
    }
}

