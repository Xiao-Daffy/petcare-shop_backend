package com.psm.petcare.service.impl;

import com.psm.petcare.dao.StoreDAO;
import com.psm.petcare.dao.UserDAO;
import com.psm.petcare.entity.Store;
import com.psm.petcare.entity.User;
import com.psm.petcare.service.StoreService;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private StoreDAO storeDAO;

    @Override
    public ResultVO createStore(Store store) {

        // 1. check user if it's 'user' or 'owner'
        // user: can open new store
        // owner: cannot
        User user = userDAO.queryUserById(store.getUserId());
        if(user==null){
            return new ResultVO(RespondStatus.NO, "User cannot found", null);
        }else{

            if(user.getUserType().equals("owner")){
                return new ResultVO(RespondStatus.NO, "User already open store", null);
            }else {

                // 2. change user type from 'user' to 'owner'
                int i1 = userDAO.updateUserType("owner", store.getUserId());
                if (i1 > 0) {
                    // 2. agree to open store
                    store.setStoreStatus(1);
                    store.setCreateTime(new Date());
                    store.setUpdateTime(new Date());
                    int i = storeDAO.insertStore(store);
                    if (i >0) {
                        return new ResultVO(RespondStatus.OK, "Created successfully", null);
                    } else {
                        return new ResultVO(RespondStatus.NO, "Failed to create", null);
                    }
                }else{
                    return new ResultVO(RespondStatus.NO, "User failed to convert owner", null);
                }
            }
        }

    }

    @Override
    public ResultVO editStore(Store store) {
        store.setUpdateTime(new Date());
        int i = storeDAO.updateStore(store);
        if(i >0){
            return new ResultVO(RespondStatus.OK, "Store updated", null);
        }else{
            return new ResultVO(RespondStatus.NO, "Failed to update", null);
        }

    }

    @Override
    public ResultVO deleteStore(Store store) {

        // change the user type
        int uid = store.getUserId();
        int i = userDAO.updateUserType("user", uid);
        if(i>0){
            int i1 = storeDAO.deactiveStore(store.getStoreId());
            if(i1 > 0){
                return new ResultVO(RespondStatus.OK, "Store deleted", null);
            }else{
                return new ResultVO(RespondStatus.NO, "Failed to delete", null);
            }
        }else{
            return new ResultVO(RespondStatus.NO, "User not found", null);
        }

    }

    @Override
    public ResultVO getStoreByUid(String userId) {
        int uid = Integer.parseInt(userId);
        Store store = storeDAO.queryStoreById(uid);
        if(store == null){
            return new ResultVO(RespondStatus.NO, "Store not found", null);
        }else{
            return new ResultVO(RespondStatus.OK, "Store found", store);
        }
    }

    @Override
    public ResultVO getStoreBySid(String storeId) {
        int sid = Integer.parseInt(storeId);
        Store store = storeDAO.queryStoreByStoreId(sid);
        if(store == null){
            return new ResultVO(RespondStatus.NO, "Store not found", null);
        }else{
            return new ResultVO(RespondStatus.OK, "Store found", store);
        }
    }

    @Override
    public ResultVO getRandomStore() {

        List<Store> stores = storeDAO.selectRandomStore();
        return new  ResultVO(RespondStatus.OK, "random stores", stores);
    }

    @Override
    public ResultVO getAllStores() {
        List<Store> stores = storeDAO.listAllStore();
        return new  ResultVO(RespondStatus.OK, "all stores", stores);
    }
}
