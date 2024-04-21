package com.ankhnotes.utils;

import com.ankhnotes.vo.HotArticleVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于封装Bean和VO的转换过程, 便于复用
 */
public class BeanCopyUtils {

    private BeanCopyUtils(){}

    //单个Bean之间的类型转换(将source对象转换为vClass对象返回)
    //source: 原数组, vClass: 指定的转换类型
    public static <V> V copyBean(Object source, Class<V> vClass) {
        V result = null;
        try {
            result = vClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        assert result != null;
        BeanUtils.copyProperties(source, result);
        return result;
    }

    //Bean数组之间的类型转换(将批量source对象转换为vClass对象数组返回)
    //source: 原数组, vClass: 指定的转换类型
    public static <O,V> List<V> copyBeanList(List<O> source, Class<V> vClass){
        List<V> targetList = new ArrayList<>();

        //将source数组中所有元素转化为vClass类型, 保存为List<V>
        source.forEach( x -> targetList.add(copyBean(x, vClass)));

        return targetList;
    }

}
