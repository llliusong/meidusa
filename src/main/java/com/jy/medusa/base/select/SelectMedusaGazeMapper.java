
package com.jy.medusa.base.select;

import com.jy.medusa.provider.BaseSelectProvider;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 通用mapper 多条件查询
 * @param <T> 不能为空
 * @author neo
 */
public interface SelectMedusaGazeMapper<T> {

    /**
     * 根据多条件查询数据 like查询 或者是 between查询   and 连接各条件
     * @param params
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "selectMedusaGaze")
    @ResultMap("BaseResultMap")
    List<T> showMedusaGaze(Object... params);
}