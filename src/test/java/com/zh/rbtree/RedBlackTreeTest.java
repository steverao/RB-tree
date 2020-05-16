package com.zh.rbtree;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author raozihao
 * @date 2020/5/10
 */
public class RedBlackTreeTest {

    @Test
    public void test() {
        int cnt = 10;
        RedBlackTree rbTree = new RedBlackTree();
        rbTree.root = rbTree.nil;
        for (int i = 0; i < cnt; i++) {
            rbTree.insert(rbTree, new TreeNode(null, null, null, Color.RED, i));
        }
        print(rbTree.root);
    }

    public void print(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int m = queue.size();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < m; i++) {
                TreeNode node = queue.remove();
                if (node != null) {
                    String color = node.color == Color.BLACK ? "B" : "R";
                    sb.append(node.key + ":" + color + " ");
                }
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            System.out.println(sb);
        }
    }

}
