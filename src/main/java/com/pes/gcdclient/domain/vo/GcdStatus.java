package com.pes.gcdclient.domain.vo;

import org.springframework.util.StringUtils;

import static java.util.Objects.isNull;

public enum GcdStatus {
    COMPLETED, NOT_COMPLETED, ERROR;

    public static GcdStatus getStatus(Long result, String error) {
        if (!StringUtils.isEmpty(error)) {
            return GcdStatus.ERROR;
        }

        if (isNull(result)) {
            return GcdStatus.NOT_COMPLETED;
        }

        return GcdStatus.COMPLETED;
    }
}
