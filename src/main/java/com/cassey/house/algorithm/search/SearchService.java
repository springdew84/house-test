package com.cassey.house.algorithm.search;

/**
 * 查找算法
 * 1.顺序查找
 *
 * 2.有序数列-二分查找（折半查找），元素必须有序
 *
 * 3.有序数列-插值查找
 *    折半查找这种查找方式，不是自适应的（也就是说是傻瓜式的）。二分查找中查找点计算如下：
 * 　　mid=(low+high)/2, 即mid=low+1/2*(high-low);
 * 　　通过类比，我们可以将查找的点改进为如下：
 * 　　mid=low+(key-a[low])/(a[high]-a[low])*(high-low)，
 * 　　也就是将上述的比例参数1/2改进为自适应的，根据关键字在整个有序表中所处的位置，让mid值的变化更靠近关键字key，这样也就间接地减少了比较次数。
 * 　　基本思想：基于二分查找算法，将查找点的选择改进为自适应选择，可以提高查找效率。当然，差值查找也属于有序查找。
 * 　　注：对于表长较大，而关键字分布又比较均匀的查找表来说，插值查找算法的平均性能比折半查找要好的多。反之，数组中如果分布非常不均匀，那么插值查找未必是很合适的选择。
 * 　　复杂度分析：查找成功或者失败的时间复杂度均为O(log2(log2n))。
 *
 * 4.有序数列-斐波那契查找
 *
 * 5.树表查找（a.二叉树 b.平衡查找树2-3查找树2-3 Tree c.平衡查找树红黑树Red-Black Tree d.B树和B+树（B Tree/B+ Tree））
 *
 *
 * 6.分块查找
 *
 * 7.哈希查找
 *   我们使用一个下标范围比较大的数组来存储元素。可以设计一个函数（哈希函数， 也叫做散列函数），使得每个元素的关键字都与一个函数值（即数组下标）相对应，
 *   于是用这个数组单元来存储这个元素
 *   法流程：
 * 　　1）用给定的哈希函数构造哈希表；
 * 　　2）根据选择的冲突处理方法解决地址冲突；
 * 　　　　常见的解决冲突的方法：拉链法和线性探测法
 *        拉链法是将大小为M的数组的每一个元素指向一个条链表，链表中的每一个节点都存储散列值为该索引的键值对
 *        线性探测法是开放寻址法解决哈希冲突的一种方法，基本原理为，使用大小为M的数组来保存N个键值对，其中M>N，我们需要使用数组中的空位解决碰撞冲突
 * 　　3）在哈希表的基础上执行哈希查找。
 * 哈希表是一个在时间和空间上做出权衡的经典例子。如果没有内存限制，那么可以直接将键作为数组的索引。那么所有的查找时间复杂度为O(1)；
 * 如果没有时间限制，那么我们可以使用无序数组并进行顺序查找，这样只需要很少的内存。哈希表使用了适度的时间和空间来在这两个极端之间找到了平衡。
 * 只需要调整哈希函数算法即可在时间和空间上做出取舍
 */
public interface SearchService {
    /**
     * 查询
     * @param arr
     * @param key
     * @return
     */
    int find(int[] arr, int key);
}
