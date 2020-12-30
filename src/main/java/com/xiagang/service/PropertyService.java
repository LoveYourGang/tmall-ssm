package com.xiagang.service;

import com.xiagang.bean.Category;
import com.xiagang.bean.Property;

import java.util.List;

public interface PropertyService {
    int addProperty(Property pt);

    Property getProperty(Integer id);
    List<Property> getProperty(Category c);

    int modifyProperty(Property pt);

    int deleteProperty(Integer id);
}
