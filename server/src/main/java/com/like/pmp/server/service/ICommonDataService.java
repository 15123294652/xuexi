package com.like.pmp.server.service;

import java.util.Set;

/**
 * @author like
 * @date 2022年05月12日 19:49
 */
public interface ICommonDataService {

    Set<Long> getCurrUserDataDeptIds();

    String getCurrUserDataDeptStr();
}
