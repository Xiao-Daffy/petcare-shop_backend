package com.psm.petcare.service;

import com.psm.petcare.entity.Store;
import com.psm.petcare.vo.ResultVO;

public interface StoreService {

    // open new store
    public ResultVO createStore(Store store);

    // edit store info
    public ResultVO editStore(Store store);

    // delete store -- not actual delete, it's just change the status
    public ResultVO deleteStore(Store store);

    // get a store by store id
    public ResultVO getStoreByUid(String userId);

    // get a store by store id
    public ResultVO getStoreBySid(String storeId);


    // select random store
    public ResultVO getRandomStore();

    public ResultVO getAllStores();
}
