package com.xiagang.service.impl;

import com.xiagang.bean.Category;
import com.xiagang.bean.Property;
import com.xiagang.dao.PropertyDao;
import com.xiagang.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("propertyService")
public class PropertyServiceImpl implements PropertyService {
    private PropertyDao propertyDao;

    @Autowired
    public PropertyServiceImpl(PropertyDao propertyDao) {
        this.propertyDao = propertyDao;
    }

    @Override
    public int addProperty(Property pt) {
        if(pt.getCategory() != null) {
            return propertyDao.insertProperty(pt);
        }
        return 0;
    }

    @Override
    public Property getProperty(Integer id) {
        return propertyDao.selectPropertyById(id);
    }

    @Override
    public List<Property> getProperty(Category c) {
        return propertyDao.selectPropertyByCategory(c);
    }

    @Override
    public int modifyProperty(Property pt) {
        return propertyDao.updateProperty(pt);
    }

    @Override
    public int deleteProperty(Integer id) {
        return propertyDao.deleteProperty(id);
    }
}
