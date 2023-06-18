package com.psm.petcare.service.impl;


import com.psm.petcare.dao.OrderDAO;
import com.psm.petcare.dao.PetProductDAO;
import com.psm.petcare.entity.OrderVO;
import com.psm.petcare.entity.PetProduct;
import com.psm.petcare.entity.PetProductVO;
import com.psm.petcare.service.PetProductService;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PetProductServiceImpl implements PetProductService {

    @Autowired
    private PetProductDAO productDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Override
    public ResultVO addProduct(PetProduct petProduct) {
        petProduct.setCreateTime(new Date());
        petProduct.setUpdateTime(new Date());
        petProduct.setDiscount(10);
        petProduct.setSalePrice(petProduct.getActualPrice());

        int i = productDAO.insertProduct(petProduct);
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "Pet product added", null);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed to add", null);
        }
    }

    @Override
    public ResultVO listProduct(String storeId) {

        int sid = Integer.parseInt(storeId);
        List<PetProduct> listPetProduct = productDAO.getListPetProduct(sid);
        return new ResultVO(RespondStatus.NO, "List of product", listPetProduct);
    }

    @Override
    public ResultVO deleteProduct(String productId) {
        int pid = Integer.parseInt(productId);
        int i = productDAO.deleteProduct(pid);
        if(i > 0){
            return new ResultVO(RespondStatus.OK, "Pet product deleted", null);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed to delete", null);
        }
    }

    @Override
    public ResultVO getProduct(String productId) {
        int pid = Integer.parseInt(productId);
        PetProduct petProduct = productDAO.queryProductById(pid);

        if(petProduct !=null){
            return new ResultVO(RespondStatus.OK, "Pet product queried", petProduct);

        }else{
            return new ResultVO(RespondStatus.NO, "Failed to query", null);
        }
    }

    @Override
    public ResultVO editProduct(PetProduct product) {
        if(product == null){
            return new ResultVO(RespondStatus.OK, "Pet product cannot be null", null);
        }else{

            product.setUpdateTime(new Date());
            int i = productDAO.updateProduct(product);
            if(i > 0){
                return new ResultVO(RespondStatus.OK, "Pet product edited", null);

            }else{
                return new ResultVO(RespondStatus.NO, "Failed to edit", null);
            }
        }
    }

    @Override
    public ResultVO getAllProduct() {

        List<PetProductVO> petProductVOS = productDAO.queryAllProduct();
        return new ResultVO(RespondStatus.OK, "all product", petProductVOS);

    }

    @Override
    public ResultVO getProductByProductId(String pid) {
        int proId = Integer.parseInt(pid);

        PetProductVO petProductVO = productDAO.queryProductByProductId(proId);
        return new ResultVO(RespondStatus.NO, "a product", petProductVO);
    }

    @Override
    public ResultVO getNewestProduct() {
        List<PetProductVO> petProducts = productDAO.selectNewProducts();
        return new ResultVO(RespondStatus.OK, "3 new products", petProducts);
    }

    @Override
    public ResultVO getBestSaleProduct() {
        List<OrderVO> orderVOS = orderDAO.queryBestSaleProduct();
        return new ResultVO(RespondStatus.OK, "3 best sale products", orderVOS);
    }


}
