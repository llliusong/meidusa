
package com.jy.medusa.base.insert;

import com.jy.medusa.provider.BaseInsertProvider;
import com.jy.medusa.utils.SystemConfigs;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectKey;

/**
 * 通用Mapper接口,插入 UUID 主键
 * @param <T> 不能为空
 * @author neo
 */
public interface InsertSelectiveUUIDMapper<T> {

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     * @param record
     * @return
     */
    @InsertProvider(type = BaseInsertProvider.class, method = "insertSelective")
    @SelectKey(statement = "SELECT uuid()", keyProperty = SystemConfigs.PRIMARY_KEY, keyColumn = SystemConfigs.PRIMARY_KEY, before = true, resultType = String.class)
    int insertSelectiveUUID(T record);
}