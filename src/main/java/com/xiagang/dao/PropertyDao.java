package com.xiagang.dao;

import com.xiagang.bean.Category;
import com.xiagang.bean.Property;

import java.util.List;

public interface PropertyDao {
    int insertProperty(Property pt);

    Property selectPropertyById(Integer id);
    List<Property> selectPropertyByName(String name);
    List<Property> selectPropertyByCategory(Category c);

    int updateProperty(Property pt);

    int deleteProperty(Integer id);
}
