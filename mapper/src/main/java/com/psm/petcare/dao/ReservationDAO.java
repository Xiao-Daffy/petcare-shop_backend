package com.psm.petcare.dao;

import com.psm.petcare.entity.PetServiceVO;
import com.psm.petcare.entity.Reservation;
import com.psm.petcare.entity.ReservationVO;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationDAO {

    // for user
    public int insertReservation(Reservation reservation);
    public List<ReservationVO> listReservationByUserId(int userId);
    public int changeIsDocument(int reserveId);
    // owner approve, reject, close; user cancel
    public int changeStatus(Reservation reservation);
    public int completeReservation(Reservation reservation);
    // for owner
    public List<ReservationVO> listReservationByStoreId(int storerId);

    public Reservation getReverstaionByDate(String checkInDate,String checkInTime);

    // select reserved pet service(completed, pending, canceled)
    public List<PetServiceVO> selectReservedService();

    // update the total amount of the reservation and status
    public int updateTotalAmountComplete(int reserveId, double totalAmount, Date completeTime);


    /**
     * Data Analysis
     */
    public List<Reservation> monthlyServiceProfit(int storeId);

    public List<ReservationVO> fiveRecentReservation(int storeId);

    // best reserved Pet service
    public ReservationVO bestReservedService(int storeId);

    // top5 service
    public List<ReservationVO> topFiveReservedService(int storeId);

    // current month daily profit
    public List<Reservation> currentMonthDailyProfit(int storeId);

    // last month daily profit
    public List<Reservation> lastMonthDailyProfit(int storeId);

    // total sale, number of different month, total number of reservation
    public Reservation totalReservation(int storeId);
}
