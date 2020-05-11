package com.zh.rbtree;

/**
 * @author raozihao
 * @date 2020/5/10
 */
public class RedBlackTree {
    /**
     * black node in red black tree
     * it represents all leaf in tree and root's parent
     */
    private final TreeNode nil = new TreeNode(null, null, null, Color.BLACK, -1);

    private TreeNode root;

    /**
     * x    -->     y
     *   y        x
     *
     * @param T
     * @param x
     */
    private void leftRotate(RedBlackTree T, TreeNode x) {
        TreeNode y = x.right;
        x.right = y.left;
        if (y.left.p != T.nil)
            y.left.p = x;
        y.p = x.p;
        if (x.p == T.nil)
            T.root = y;
        else if (x == x.p.left)
            x.p.left = y;
        else
            x.p.right = y;
        y.left = x;
        x.p = y;
    }
    /**
     *   x   -->  y
     * y            x
     *
     * @param T
     * @param x
     */
    private void rightRotate(RedBlackTree T, TreeNode x) {
        TreeNode y = x.left;
        x.left = y.right;
        if (y.right != T.nil)
            y.right.p = x;
        y.p = x.p;
        if (x.p == T.nil)
            T.root = y;
        else if (x == x.p.left)
            x.p.left = y;
        else
            x.p.right = y;
        y.right = x;
        x.p = y;
    }

    private void insert(TreeNode T,TreeNode z){

    }
}
