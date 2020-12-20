package com.xiagang.dao;

import com.xiagang.bean.Product;
import com.xiagang.bean.Property;
import com.xiagang.bean.PropertyValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PropertyValueDao {
    int insertPropertyValue(PropertyValue pv);

    PropertyValue selectPropertyValueById(Integer id);
    PropertyValue selectPropertyValueByProductProperty(@Param("p") Product p, @Param("pt") Property pt);
    List<PropertyValue> selectPropertyValueByProduct(Product p);

    int updatePropertyValue(PropertyValue pv);

    int deletePropertyValue(Integer id);
}
