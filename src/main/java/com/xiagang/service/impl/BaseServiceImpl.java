package com.xiagang.service.impl;

import com.xiagang.bean.*;
import com.xiagang.dao.OrderItemDao;
import com.xiagang.dao.ProductDao;
import com.xiagang.dao.ProductImageDao;
import com.xiagang.dao.ReviewDao;
import com.xiagang.service.BaseService;

import com.xiagang.utils.ImageUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("base")
class BaseServiceImpl implements BaseService {
    private ProductDao productDao;
    private OrderItemDao orderItemDao;
    private ProductImageDao productImageDao;
    private ReviewDao reviewDao;

    @Autowired
    public BaseServiceImpl(ProductDao pDao, OrderItemDao oiDao, ProductImageDao piDao, ReviewDao reviewDao) {
        productDao = pDao;
        orderItemDao = oiDao;
        productImageDao = piDao;
        this.reviewDao = reviewDao;
    }

    @Override
    public void fillCategory(Category c) {
        List<Product> products = productDao.selectProductByCategory(c);//productService.getProducts(c);
        products.forEach(this::fillProduct);
        c.setProducts(products);
        int start, count=5;
        List<List<Product>> productsByRow = new ArrayList<>();
        for(start=0; start < products.size(); start += count) {
            List<Product> ps = new ArrayList<>(count);
            for(int i=0; i < count && start+i < products.size(); i++) {
                ps.add(products.get(start+i));
            }
            productsByRow.add(ps);
        }
        c.setProductsByRow(productsByRow);
    }

    @Override
    public void fillOrder(Order order) {
        List<OrderItem> ois = orderItemDao.selectOrderItemByOrder(order);
        ois.forEach(oi -> fillProduct(oi.getProduct()));
        order.setOrderItems(ois);
    }

    @Override
    public void fillProduct(Product p) {
        List<ProductImage> pisAll = productImageDao.selectProductImageByProduct(p);
        List<ProductImage> pisSingle = productImageDao.selectProductImageByProductType(p, ProductImage.SINGLE);
        List<ProductImage> pisDetail = productImageDao.selectProductImageByProductType(p, ProductImage.DETAIL);
        p.setProductImages(pisAll);
        p.setProductSingleImages(pisSingle);
        p.setProductDetailImages(pisDetail);
        if(!pisAll.isEmpty())
            p.setFirstProductImage(pisAll.get(0));
        p.setReviewCount(reviewDao.selectReviewCount(p));
        p.setSaleCount(orderItemDao.selectSaleCount(p));
    }

    @Override
    public boolean uploadImage(InputStream is, File file) {
        boolean ok = true;
        try {
            if(null != is && 0 != is.available()){
                try(FileOutputStream fos = new FileOutputStream(file)){
                    byte b[] = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = is.read(b))) {
                        fos.write(b, 0, length);
                    }
                    fos.flush();
                    //通过如下代码，把文件保存为jpg格式
                    BufferedImage img = ImageUtil.change2jpg(file);
                    ImageIO.write(img, "jpg", file);
                    ok = true;
                } catch(Exception e){
                    ok = false;
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            ok = false;
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
        InputStream is = null;
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 设置上传文件的大小限制为10M
            factory.setSizeThreshold(1024 * 10240);

            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (!item.isFormField()) {
                    // item.getInputStream() 获取上传文件的输入流
                    is = item.getInputStream();
                } else {
                    String paramName = item.getFieldName();
                    String paramValue = item.getString();
                    paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
                    params.put(paramName, paramValue);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }
}
