package com.tanchengjin.oauth2.modules.article;

import java.util.List;
import java.util.stream.Collectors;

public class Tree<T extends Tree.ITree> {
    private List<T> dataList;

    public Tree(List<T> data) {
        this.dataList = data;
    }

    public List<T> tree() {
        List<T> data = this.dataList.stream().filter(f -> f.getParentId() == 0).collect(Collectors.toList());
        return this.handleTree(this.dataList, data);
    }

    private List<T> handleTree(List<T> source, List<T> dataList) {
        if (dataList != null && dataList.size() >= 1) {
            dataList.forEach(item -> {
                List<T> children = source.stream().filter(f -> (item.getParentId() != null && item.getParentId() != 0) && (f.getParentId() == item.getId())).collect(Collectors.toList());
                item.setChildren(children);
                this.handleTree(source, children);
            });
        }
        return dataList;
    }

    public interface ITree<T> {

        public Long getId();

        public String getName();

        public Long getParentId();

        public boolean setChildren(List<T> children);
    }
}
