package com.indigo.iam.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 树形结构构建工具类
 *
 * @author MaxYun
 * @since 2025/12/29
 */
public class TreeBuilder {

    /**
     * 构建树形结构
     *
     * @param list              原始列表
     * @param rootId            根节点ID
     * @param getIdFunc         获取ID的函数
     * @param getParentIdFunc   获取父ID的函数
     * @param setChildrenFunc   设置子节点的函数
     * @param <T>               节点类型
     * @param <ID>              ID类型
     * @return 树形结构列表
     */
    public static <T, ID> List<T> buildTree(
            List<T> list,
            ID rootId,
            Function<T, ID> getIdFunc,
            Function<T, ID> getParentIdFunc,
            BiConsumer<T, List<T>> setChildrenFunc) {

        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取根节点
        List<T> rootNodes = list.stream()
                .filter(node -> {
                    ID parentId = getParentIdFunc.apply(node);
                    if (parentId == null) {
                        return true;
                    }
                    return (rootId != null && rootId.equals(parentId)) ||
                           (rootId == null && (parentId.equals(0L) || parentId.equals(0)));
                })
                .collect(Collectors.toList());

        // 递归构建树
        for (T rootNode : rootNodes) {
            buildChildren(rootNode, list, getIdFunc, getParentIdFunc, setChildrenFunc);
        }

        return rootNodes;
    }

    /**
     * 递归构建子节点
     */
    private static <T, ID> void buildChildren(
            T parent,
            List<T> allNodes,
            Function<T, ID> getIdFunc,
            Function<T, ID> getParentIdFunc,
            BiConsumer<T, List<T>> setChildrenFunc) {

        ID parentId = getIdFunc.apply(parent);

        // 查找所有子节点
        List<T> children = allNodes.stream()
                .filter(node -> {
                    ID nodeParentId = getParentIdFunc.apply(node);
                    return nodeParentId != null && nodeParentId.equals(parentId);
                })
                .collect(Collectors.toList());

        if (!children.isEmpty()) {
            setChildrenFunc.accept(parent, children);
            // 递归构建子节点的子节点
            for (T child : children) {
                buildChildren(child, allNodes, getIdFunc, getParentIdFunc, setChildrenFunc);
            }
        }
    }

    /**
     * 将树形结构扁平化为列表
     *
     * @param tree            树形结构列表
     * @param getChildrenFunc 获取子节点的函数
     * @param <T>             节点类型
     * @return 扁平化列表
     */
    public static <T> List<T> flattenTree(List<T> tree, Function<T, List<T>> getChildrenFunc) {
        List<T> result = new ArrayList<>();
        if (tree == null || tree.isEmpty()) {
            return result;
        }

        for (T node : tree) {
            result.add(node);
            List<T> children = getChildrenFunc.apply(node);
            if (children != null && !children.isEmpty()) {
                result.addAll(flattenTree(children, getChildrenFunc));
            }
        }

        return result;
    }

    /**
     * 查找树中的节点
     *
     * @param tree            树形结构列表
     * @param targetId        目标ID
     * @param getIdFunc       获取ID的函数
     * @param getChildrenFunc 获取子节点的函数
     * @param <T>             节点类型
     * @param <ID>            ID类型
     * @return 找到的节点，未找到返回null
     */
    public static <T, ID> T findNode(
            List<T> tree,
            ID targetId,
            Function<T, ID> getIdFunc,
            Function<T, List<T>> getChildrenFunc) {

        if (tree == null || tree.isEmpty() || targetId == null) {
            return null;
        }

        for (T node : tree) {
            if (targetId.equals(getIdFunc.apply(node))) {
                return node;
            }

            List<T> children = getChildrenFunc.apply(node);
            if (children != null && !children.isEmpty()) {
                T found = findNode(children, targetId, getIdFunc, getChildrenFunc);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
    }

    /**
     * 获取节点路径
     *
     * @param tree            树形结构列表
     * @param targetId        目标ID
     * @param getIdFunc       获取ID的函数
     * @param getChildrenFunc 获取子节点的函数
     * @param <T>             节点类型
     * @param <ID>            ID类型
     * @return 从根节点到目标节点的路径列表
     */
    public static <T, ID> List<T> getNodePath(
            List<T> tree,
            ID targetId,
            Function<T, ID> getIdFunc,
            Function<T, List<T>> getChildrenFunc) {

        List<T> path = new ArrayList<>();
        findNodePath(tree, targetId, getIdFunc, getChildrenFunc, path);
        return path;
    }

    /**
     * 递归查找节点路径
     */
    private static <T, ID> boolean findNodePath(
            List<T> nodes,
            ID targetId,
            Function<T, ID> getIdFunc,
            Function<T, List<T>> getChildrenFunc,
            List<T> path) {

        if (nodes == null || nodes.isEmpty() || targetId == null) {
            return false;
        }

        for (T node : nodes) {
            path.add(node);

            if (targetId.equals(getIdFunc.apply(node))) {
                return true;
            }

            List<T> children = getChildrenFunc.apply(node);
            if (children != null && !children.isEmpty()) {
                if (findNodePath(children, targetId, getIdFunc, getChildrenFunc, path)) {
                    return true;
                }
            }

            path.remove(path.size() - 1);
        }

        return false;
    }
}
