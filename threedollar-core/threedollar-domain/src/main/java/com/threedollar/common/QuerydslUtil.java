package com.threedollar.common;

import com.querydsl.core.types.Predicate;
import jakarta.annotation.Nullable;

import java.util.function.Supplier;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QuerydslUtil {

    @Nullable
    public static Predicate dynamicQuery(boolean isAvailable, Supplier<Predicate> supplier) {
        if (!isAvailable) {
            return null;
        }
        return supplier.get();

    }

}
