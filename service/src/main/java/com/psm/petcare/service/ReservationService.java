package com.psm.petcare.service;

import com.psm.petcare.entity.OrderVO;
import com.psm.petcare.entity.Reservation;
import com.psm.petcare.entity.ReservationVO;
import com.psm.petcare.vo.ResultVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ReservationService {

    // make reservation
    public ResultVO makingReservation(Reservation reservation);

    // view reservation, pet service, store information from user
    public ResultVO listReservationFromUserView(String userId);


    public ResultVO cancelReservation(String rid); // reservation id

    /*
        Owner
    */

    // view reservation, pet service, store information from user
    public ResultVO listReservationFromOwnerView(String storeId);

    public ResultVO approveReservation(String rid);// reservation id
    public ResultVO closeReservation(String rid);// reservation id
    public ResultVO rejectReservation(String rid);// reservation id

    public ResultVO editTotalAmountStatus(String rid, double totalAmount);// reservation id


    /**
     * Data Analysis
     *
     */
    public List<Reservation> getPetServiceMontlySale(String storeId);
    public List<ReservationVO> getFiveRecentReservation(String storeId);
    public ReservationVO getBestReservedService(String storeId);
    public List<ReservationVO> getTopFiveService(String storeId);
    public List<Double> getLastMonthDailyReservationProfit(String storeId);
    public List<Double> getCurrentMonthDailyReservationProfit(String storeId);
    public Reservation getTotalSale(String storeId);
}
