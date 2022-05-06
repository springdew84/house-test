package com.cassey.house.algorithm.tree;

/**
 * BM33. 二叉树的镜像
 * https://www.nowcoder.com/practice/a9d0ecbacef9410ca97463e4a5c83be7?tpId=295&sfm=html&channel=nowcoder
 */
public class MirrorTree {
    public static void main(String[] args) {
        mirror(new TreeNode());
    }

    private static TreeNode mirror(TreeNode pRoot) {
        if(pRoot == null) return null;

        TreeNode left = mirror(pRoot.left);

        TreeNode right = mirror(pRoot.right);

        pRoot.left = right;
        pRoot.right = left;

        return pRoot;
    }
}
