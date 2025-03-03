package com.livk.autoconfigure.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.google.common.collect.Lists;

import java.util.Collection;

/**
 * <p>
 * TypeExcelReadListener
 * </p>
 *
 * @author livk
 * @date 2022/10/21
 */
public abstract class TypeExcelReadListener<T> implements ExcelReadListener<T> {

    private final Collection<T> dataExcels = Lists.newArrayList();

    @Override
    public void invoke(T info, AnalysisContext context) {
        dataExcels.add(info);
    }

    @Override
    public Collection<T> getCollectionData() {
        return dataExcels;
    }
}
