
package com.jy.medusa.base.delete;

import com.jy.medusa.provider.BaseDeleteProvider;
import org.apache.ibatis.annotations.DeleteProvider;

/**
 * 通用Mapper接口,删除
 * @param <T> 不能为空
 * @author neo
 */
public interface DeleteByPrimaryKeyMapper<T> {

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     * @param key
     * @return
     */
    @DeleteProvider(type = BaseDeleteProvider.class, method = "deleteByPrimaryKey")
    int deleteByPrimaryKey(Object key);

}