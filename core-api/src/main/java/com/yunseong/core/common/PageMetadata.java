package com.yunseong.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageMetadata<T> {

    private final int size;
    private final int number;
    private final int totalSize;
    private final List<T> t;
}
