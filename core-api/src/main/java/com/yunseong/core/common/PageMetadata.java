package com.yunseong.core.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageMetadata<T> {

    private int size;
    private int number;
    private int totalSize;
    private List<T> content;
}
