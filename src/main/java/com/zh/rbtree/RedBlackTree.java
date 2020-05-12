package com.zh.rbtree;

/**
 * @author raozihao
 * @date 2020/5/10
 */
public class RedBlackTree {
    /**
     * Black node in red black tree
     * It represents all leaf in tree and root's parent
     */
    private final TreeNode nil = new TreeNode(null, null, null, Color.BLACK, -1);

    private TreeNode root;

    /**
     * x    -->     y
     * y        x
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
     * x   -->  y
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

    /**
     * 1. Move down the z to target position.
     * 2. Insert the z to target position.
     * 3. Fixup the red black tree influenced by insertion of the z.
     *
     * @param T red black tree
     * @param z the node to insert
     */
    private void insert(RedBlackTree T, TreeNode z) {
        TreeNode y = T.nil;
        TreeNode x = T.root;
        while (x != T.nil) {
            y = x;
            if (z.key < x.key)
                x = x.left;
            else x = x.right;
        }

        if (y != T.nil)
            if (z.key < y.key)
                y.left = z;
            else y.right = z;
        else T.root = z;
        z.p = y;
        z.left = T.nil;
        z.right = T.nil;
        z.color = Color.RED;

        insertFixup(T, z);
    }

    /**
     * case 1: the uncle of the z is RED.
     * case 2: the uncle of the z is BLACK and the z is a right child.
     * case 3: the uncle of the z is BLACK and the z is a left child.
     *
     * @param T
     * @param z
     */
    public void insertFixup(RedBlackTree T, TreeNode z) {
        // if z.p.color == Color.BLACK, T is still a red black tree.
        while (z.p.color == Color.RED) {
            if (z.p == z.p.p.left) {
                TreeNode y = z.p.p.right;
                // case 1:
                if (y.color == Color.RED) {
                    z.p.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.p.p.color = Color.RED;
                    z = z.p.p;
                }
                // case 2:
                else if (z.p.right == z) {
                    z = z.p;
                    leftRotate(T, z);
                }
                // case 3:
                z.p.color = Color.BLACK;
                z.p.p.color = Color.RED;
                rightRotate(T, z.p.p);
            } else {
                TreeNode y = z.p.p.left;
                // case 1:
                if (y.color == Color.RED) {
                    z.p.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.p.p.color = Color.RED;
                    z = z.p.p;
                }
                // case 2:
                else if (z.p.right == z) {
                    z = z.p;
                    leftRotate(T, z);
                }
                // case 3:
                z.p.color = Color.BLACK;
                z.p.p.color = Color.RED;
                rightRotate(T, z.p.p);
            }
            T.root.color = Color.BLACK;
        }
    }

    /**
     * Replace the child-tree u by child-tree v and let v.parent equals to u.parent.
     *
     * @param T
     * @param u
     * @param v
     */
    public void transplant(RedBlackTree T, TreeNode u, TreeNode v) {
        if (u == T.nil)
            T.root = v;
        else if (u == u.p.left)
            u.p.left = v;
        else u.p.right = v;
        v.p = u.p;
    }

    /**
     * 1. If the left-child is leaf, put z.right in position z. Otherwise
     * 2. If z has two internal child, find the minimum child of z.right and put it in position z.
     * 3. Fixup the red black tree influenced by deletion of the z.
     *
     * @param T
     * @param z
     */
    public void delete(RedBlackTree T, TreeNode z) {
        TreeNode y = z, x;
        Color yOriginalColor = y.color;
        if (z.left == T.nil) {
            x = z.right;
            transplant(T, z, z.right);
        } else if (z.right == T.nil) {
            x = z.left;
            transplant(T, z, z.left);
        } else {
            y = minimum(T, z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.p == z)
                x.p = y;
            else {
                transplant(T, y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
            transplant(T, z, y);
            y.left = z.left;
            y.left.p = y;
            y.color = z.color;
        }

        if (yOriginalColor == Color.BLACK)
            deleteFixup(T, x);
    }

    public TreeNode minimum(RedBlackTree T, TreeNode z) {
        while (z != T.nil) {
            z = z.left;
        }
        return z.p;
    }

    /**
     * case 1: x's brother is RED.
     * case 2: x's brother w is BLACK and w's two children are BLACK.
     * case 3: x's brother w is BLACK and w's left-child is RED, another is BLACK.
     * case 4: x's brother w is BLACK and w's right-child id RED
     *
     * @param T
     * @param x
     */
    public void deleteFixup(RedBlackTree T, TreeNode x) {
        while (x != T.root && x.color == Color.BLACK) {
            if (x == x.p.left) {
                TreeNode w = x.p.right;
                // case 1:
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.p.color = Color.RED;
                    leftRotate(T, x.p);
                    w = x.p.right;
                }
                // case 2:
                if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.p;
                }
                // case 3:
                else if (w.right.color == Color.BLACK && w.left.color == Color.RED) {
                    w.left.color = Color.BLACK;
                    w.color = Color.BLACK;
                    rightRotate(T, w);
                    w = x.p.right;

                }
                // case 4:
                if (w.right.color == Color.RED) {
                    w.color = x.p.color;
                    x.p.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    leftRotate(T, x.p);
                    x = T.root;
                }
                // Symmetry
            } else {
                TreeNode w = x.p.left;
                // case 1:
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    x.p.color = Color.RED;
                    rightRotate(T, x.p);
                    w = x.p.left;
                }
                // case 2:
                if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
                    w.color = Color.RED;
                    x = x.p;
                }
                // case 3:
                else if (w.right.color == Color.BLACK && w.left.color == Color.RED) {
                    w.left.color = Color.BLACK;
                    w.color = Color.BLACK;
                    rightRotate(T, w);
                    w = x.p.left;
                }
                // case 4:
                if (w.right.color == Color.RED) {
                    w.color = x.p.color;
                    x.p.color = Color.BLACK;
                    w.right.color = Color.BLACK;
                    rightRotate(T, x.p);
                    x = T.root;
                }
            }
            x.color = Color.BLACK;
        }
    }
}
