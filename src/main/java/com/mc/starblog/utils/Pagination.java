package com.mc.starblog.utils;

import lombok.Data;

@Data
public class Pagination {

    private int page;
    private int pageSize;
    private long total;
    private int totalPages;
    private boolean hasNextPage;

    public Pagination(int page, int pageSize, long total) {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize 必须大于 0");
        }
        if (total < 0) {
            throw new IllegalArgumentException("total 不能为负数");
        }

        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.totalPages = (total == 0) ? 0 : (int) Math.ceil((double) total / pageSize);
        this.hasNextPage = page < totalPages;
    }
}
