package com.xiagang.service.impl;

import com.xiagang.bean.ProductImage;
import com.xiagang.dao.ProductDao;
import com.xiagang.dao.ProductImageDao;
import com.xiagang.service.BaseService;
import com.xiagang.service.ProductImageService;
import com.xiagang.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service("piService")
public class ProductImageServiceImpl implements ProductImageService {
    private ProductImageDao productImageDao;
    private ProductDao productDao;
    private BaseService base;

    @Autowired
    public ProductImageServiceImpl(ProductImageDao productImageDao, ProductDao productDao, BaseService base) {
        this.productImageDao = productImageDao;
        this.productDao = productDao;
        this.base = base;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,readOnly = false)
    public int addProductImage(HttpServletRequest request, Map<String, String> params) {
        //Map<String, String> params = new HashMap<>();
        InputStream is = base.parseUpload(request, params);
        ProductImage pi = new ProductImage();
        pi.setProduct(productDao.selectProductById(Integer.valueOf(params.get("pid"))));
        pi.setType(params.get("type"));
        int res = productImageDao.insertProductImage(pi);
        if(res < 1)
            return res;
        res = uploadImage(request, is, pi);
        return res;
    }

    @Override
    public ProductImage getProductImage(Integer id) {
        return productImageDao.selectProductImageById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,readOnly = false)
    public int deleteProductImage(HttpServletRequest request, Integer id) {
        ProductImage pi = productImageDao.selectProductImageById(id);
        String type = pi.getType();
        String path, path_small, path_middle;
        if(ProductImage.SINGLE.equals(type)) {
            path = request.getServletContext().getRealPath("img/productSingle");
            path_small = request.getServletContext().getRealPath("img/productSingle_small");
            path_middle = request.getServletContext().getRealPath("img/productSingle_middle");
            File file = new File(path, pi.getId()+".jpg");
            File file_small = new File(path_small, pi.getId()+".jpg");
            File file_middle = new File(path_middle, pi.getId()+".jpg");
            file.delete();
            file_small.delete();
            file_middle.delete();
        } else {
            path = request.getServletContext().getRealPath("img/productDetail");
            //path_small = null;
            //path_middle = null;
            File file = new File(path, pi.getId()+".jpg");
            file.delete();
        }
        System.out.println("成功删除");

        return productImageDao.deleteProductImage(id);
    }

    private int uploadImage(HttpServletRequest request, InputStream is, ProductImage pi) {
        int res = 1;
        String path, path_small, path_middle, type = pi.getType();
        if(ProductImage.SINGLE.equals(type)) {
            path = request.getServletContext().getRealPath("img/productSingle");
            path_small = request.getServletContext().getRealPath("img/productSingle_small");
            path_middle = request.getServletContext().getRealPath("img/productSingle_middle");
        } else {
            path = request.getServletContext().getRealPath("img/productDetail");
            path_small = null;
            path_middle = null;
        }

        String name = pi.getId() + ".jpg";
        File file = new File(path, name);
        file.getParentFile().mkdirs();
        try {
            if(null!=is && 0!=is.available()){
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

                    if(ProductImage.SINGLE.equals(type)) {
                        File file_small = new File(path_small, name);
                        File file_middle = new File(path_middle, name);

                        ImageUtil.resizeImage(file, 56, 56, file_small);
                        ImageUtil.resizeImage(file, 217, 190, file_middle);
                    }
                } catch(Exception e){
                    res = -1;
                    e.printStackTrace();
                }
            } else {
                res = 0;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            res = -1;
            e.printStackTrace();
        }
        return res;
    }
}
