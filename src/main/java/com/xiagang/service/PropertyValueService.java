package com.xiagang.service;

import com.xiagang.bean.Product;
import com.xiagang.bean.PropertyValue;

import java.util.List;

public interface PropertyValueService {
    List<PropertyValue> getPropertyValue(Product p);

    int modifyPropertyValue(PropertyValue pv);
}
