package com.bgi.bktree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bgi.bktree.trie.Trie;
import com.bgi.util.SystemConfig;
import com.bgi.util.Element;

public class BKTree<T> {
	private static Trie trie = SystemConfig.TRIE;
    private final MetricSpace<T> metricSpace;
    
    private Node<T> root;
    
    public BKTree(MetricSpace<T> metricSpace) {
        this.metricSpace = metricSpace;
    }
    
    
    public BKTree(MetricSpace<T> metricSpace, Trie trie) {
        this.metricSpace = metricSpace;
        BKTree.trie = trie;
    }
    

    /**
     * 根据某一个集合元素创建BK树
     * @param ms
     * @param elems
     * @return
     */
    public static <E> BKTree<E> mkBKTree(MetricSpace<E> ms, Collection<E> elems) {
        BKTree<E> bkTree = new BKTree<E>(ms);
        for (E elem : elems) {
            bkTree.put(elem);
        }
        return bkTree;
    }
    
    
    /**
     * 根据某一个集合元素创建BK树
     * @param ms
     * @param elems
     * @return
     */
    public static <E> BKTree<E> mkBKTree(MetricSpace<E> ms, Trie trie, 
    		Collection<E> elems) {
        BKTree<E> bkTree = new BKTree<E>(ms, trie);
        for (E elem : elems) {
            bkTree.put(elem);
        }
        return bkTree;
    }
    

    /**
     * BK树中添加元素
     * @param term
     */
    public void put(T term) {
        if (root == null) { // 集合中第一个元素为根节点
            root = new Node<T>(term);
        } else {
            root.add(metricSpace, term);
        }
    }
    
    
	private void sort(List<Element<T>> result) {
		Collections.sort(result, new Comparator<Element<T>>() {
            @Override
            public int compare(Element<T> src, Element<T> dst) {
                if (src.hot >= dst.hot) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
	}
    
    
    /**
     * 查询相似元素
     * @param term    待查询的元素
     * @param radius  相似的距离范围
     * @return        满足距离范围的所有元素
     */
    public List<T> query(T term, double radius) {
        List<Element<T>> results = new ArrayList<Element<T>>();
        if (root != null) {
            root.query(metricSpace, term, radius, results);
        }

        sort(results);
        
        List<T> ret = new ArrayList<T>();
        for (Element<T> e : results) {
        	ret.add(e.data);
        }
        
        return ret;
    }
    
    
    /**
     * 查询相似元素
     * @param term    待查询的元素
     * @param radius  相似的距离范围
     * @return        满足距离范围的所有元素
     */
    public List<Element<T>> search(T term, double radius) {
        List<Element<T>> results = new ArrayList<Element<T>>();
        if (root != null) {
            root.query(metricSpace, term, radius, results);
        }
        return results;
    }

    
    private static final class Node<T> {
        private final T value;
        private final Map<Double, Node<T>> children;  // map 存储子节点信息
        private final double hot;
        
        public Node(T term) {
            this.value = term;
            this.children = new HashMap<Double, BKTree.Node<T>>();
            this.hot = BKTree.trie.searchKey((String)term);
        }
        
        
        public void add(MetricSpace<T> ms, T value) {
            // value与父节点的距离
            Double distance = ms.distance(this.value, value);
            // 距离为0，表示元素相同，返回
            if (distance == 0) {
                return;
            }
            
            // 从父节点的子节点中查找child，满足距离为distance
            Node<T> child = children.get(distance); // 按照编辑距离值查找子节点
            if (child == null) {
                // 若距离父节点为distance的子节点不存在，则直接添加一个新的子节点
                children.put(distance, new Node<T>(value));
            } else {
                // 若距离父节点为distance子节点存在，则递归的将value添加到该子节点下
                child.add(ms, value);
            }
        }
        
        
        public void query(MetricSpace<T> ms, T term, double radius, List<Element<T>> results) {
            double distance = ms.distance(this.value, term);
            // 与父节点的距离小于阈值，则添加到结果集中，并继续向下寻找
            if (distance <= radius) {
                results.add(new Element<T>(this.value, this.hot));
            }
            
            // 子节点的距离在最小距离和最大距离之间的。
            // 由度量空间的d(x,y) + d(y,z) >= d(x,z)这一定理，有查找的value与子节点的距离范围如下：
            // min = {1,distance -radius}, max = distance + radius
            for (double i = Math.max(distance - radius, 1); i <= distance + radius; i += 0.5) {
                Node<T> child = children.get(i);
                // 递归调用
                if (child != null) {
                    child.query(ms, term, radius, results);
                }
            }
        }
    }
}
