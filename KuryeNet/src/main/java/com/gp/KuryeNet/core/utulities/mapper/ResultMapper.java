package com.gp.KuryeNet.core.utulities.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;

public final class ResultMapper {

    private ResultMapper() {
    }

    public static <T, R> DataResult<?> mapIfSuccess(DataResult<T> result, Function<T, R> mapper) {
        if (result == null || !result.isSuccess()) {
            return result;
        }
        R mapped = mapper.apply(result.getData());
        return new SuccessDataResult<>(mapped, result.getMessage());
    }

    public static <T, R> DataResult<?> mapListIfSuccess(DataResult<List<T>> result, Function<T, R> mapper) {
        if (result == null || !result.isSuccess()) {
            return result;
        }
        List<T> data = result.getData();
        List<R> mapped = data == null
                ? null
                : data.stream().map(mapper).collect(Collectors.toList());
        return new SuccessDataResult<>(mapped, result.getMessage());
    }
}
