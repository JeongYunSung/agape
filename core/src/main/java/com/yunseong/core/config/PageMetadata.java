package com.yunseong.core.config;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageMetadata<T> {

    private final int size;
    private final int number;
    private final int totalSize;
    private final List<T> t;

    public PageMetadata(Page<T> page) {
        this.size = page.getSize();
        this.number = page.getNumber();
        this.totalSize = page.getTotalPages();
        this.t = page.getContent();
    }
}
