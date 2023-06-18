package com.psm.petcare.dao;

import com.psm.petcare.entity.PetProduct;
import com.psm.petcare.entity.PetProductVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetProductDAO {

    // insert product info
    public int insertProduct(PetProduct petProduct);

    // get list of product
    public List<PetProduct> getListPetProduct(int storeId);

    // delete
    public int deleteProduct(int productId);

    // get a product
    public PetProduct queryProductById(int productId);

    // update
    public int updateProduct(PetProduct product);

    // all product -- user side
    public List<PetProductVO> queryAllProduct();

    public PetProductVO queryProductByProductId(int productId);

    // update the stock
    public int updateStockById(int productId, int stock);

    // select 3 new product
    public List<PetProductVO> selectNewProducts();

}
