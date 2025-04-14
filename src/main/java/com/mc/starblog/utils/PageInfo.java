package com.mc.starblog.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageInfo<T> {

    private List<T> content;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean last;

}
