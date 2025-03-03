package com.livk.autoconfigure.mapstruct.converter;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * MapstructService
 * </p>
 *
 * @author livk
 * @date 2022/6/9
 */
public interface MapstructService {

    <S, T> T convert(S source, Class<T> targetClass);

    default <S, T> Stream<T> convert(Collection<S> sources, Class<T> targetClass) {
        if (sources == null || sources.isEmpty()) {
            return Stream.empty();
        }
        return sources.stream().map(source -> convert(source, targetClass));
    }

    default <S, T> List<T> convertList(Collection<S> sources, Class<T> targetClass) {
        return convert(sources, targetClass).collect(Collectors.toList());
    }

    default <S, T> List<T> convertUnmodifiableList(Collection<S> sources, Class<T> targetClass) {
        return convert(sources, targetClass).toList();
    }

    default <S, T> Set<T> convertSet(Collection<S> sources, Class<T> targetClass) {
        return convert(sources, targetClass).collect(Collectors.toSet());
    }

    default <S, T> Set<T> convertUnmodifiableSet(Collection<S> sources, Class<T> targetClass) {
        return convert(sources, targetClass).collect(Collectors.toUnmodifiableSet());
    }
}
