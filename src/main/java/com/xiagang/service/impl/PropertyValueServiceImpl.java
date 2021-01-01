package com.xiagang.service.impl;

import com.xiagang.bean.Product;
import com.xiagang.bean.PropertyValue;
import com.xiagang.dao.PropertyValueDao;
import com.xiagang.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("pvService")
public class PropertyValueServiceImpl implements PropertyValueService {
    private PropertyValueDao propertyValueDao;

    @Autowired
    public PropertyValueServiceImpl(PropertyValueDao propertyValueDao) {
        this.propertyValueDao = propertyValueDao;
    }

    @Override
    public int addPropertyValue(PropertyValue pv) {
        return propertyValueDao.insertPropertyValue(pv);
    }

    @Override
    public List<PropertyValue> getPropertyValue(Product p) {
        return propertyValueDao.selectPropertyValueByProduct(p);
    }

    @Override
    public int modifyPropertyValue(PropertyValue pv) {
        return propertyValueDao.updatePropertyValue(pv);
    }
}
