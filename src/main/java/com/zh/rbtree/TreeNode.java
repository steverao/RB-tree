package com.zh.rbtree;

/**
 * @author raozihao
 * @date 2020/5/10
 */
public class TreeNode {
    /**
     * parent node
     */
    public TreeNode p;
    /**
     * left child
     */
    public TreeNode left;
    /**
     * right child
     */
    public TreeNode right;
    /**
     * node's color
     */
    public Color color;
    /**
     * node's key
     */
    public int key;

    public TreeNode(TreeNode p, TreeNode left, TreeNode right, Color color, int key) {
        this.p = p;
        this.left = left;
        this.right = right;
        this.color = color;
        this.key = key;
    }
}

