package com.psm.petcare.dao;

import com.psm.petcare.entity.Store;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreDAO {

    // insert store
    public int insertStore(Store store);

    public Store queryStoreById(int userId);

    public Store queryStoreByStoreId(int storeId);

    // delete store -- > change the status only
    public int deactiveStore(int storeId);


    // update store
    public int updateStore(Store store);

    // randomly select 3 stores displaying in index page
    public List<Store> selectRandomStore();

    // list all store
    public List<Store> listAllStore();
}
