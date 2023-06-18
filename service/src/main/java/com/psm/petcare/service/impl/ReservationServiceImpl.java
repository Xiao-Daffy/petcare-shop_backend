package com.psm.petcare.service.impl;

import com.psm.petcare.dao.ReservationDAO;
import com.psm.petcare.entity.Reservation;
import com.psm.petcare.entity.ReservationVO;
import com.psm.petcare.service.ReservationService;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationDAO reservationDAO;

    @Override
    public ResultVO makingReservation(Reservation reservation) {

        // check whether the reservation is reserved
        Reservation reverstaionByDate = reservationDAO.getReverstaionByDate(reservation.getCheckInDate(), reservation.getCheckInTime());
        if(reverstaionByDate!= null){
            return new ResultVO(RespondStatus.NO, "The time slot is already reserved, please choose other", null);
        }else{
            reservation.setReserveStatus("Pending");
            int i = reservationDAO.insertReservation(reservation);
            if(i>0){
                return new ResultVO(RespondStatus.OK, "made reservation", null);
            }else{
                return new ResultVO(RespondStatus.NO, "Failed to make reservation", null);
            }
        }
        }


    @Override
    public ResultVO listReservationFromUserView(String userId) {
        int uid = Integer.parseInt(userId);
        List<ReservationVO> reservationVOS = reservationDAO.listReservationByUserId(uid);
        return new ResultVO(RespondStatus.OK, "List of reservation",reservationVOS);
    }

    @Override
    public ResultVO cancelReservation(String rid) {
        int reId = Integer.parseInt(rid);

        Reservation reservation = new Reservation();
        reservation.setReserveId(reId);
        reservation.setCancelTime(new Date());
        reservation.setReserveStatus("Canceled");
        int i = reservationDAO.changeStatus(reservation);
        if(i >0){
            return new ResultVO(RespondStatus.OK, "Reservation canceled", null);
        }else{
            return new ResultVO(RespondStatus.NO, "Failed to canceled", null);
        }

    }

    @Override
    public ResultVO listReservationFromOwnerView(String storeId) {

        int sid = Integer.parseInt(storeId);
        List<ReservationVO> reservationVOS = reservationDAO.listReservationByStoreId(sid);

        return new ResultVO(RespondStatus.OK, "List of reservation",reservationVOS);
    }

    /*
            Confirmed/Complete: update CompleteTime(new Date())
            Reject/Cancel: update CancelTime(new Date())
     */
    @Override
    public ResultVO approveReservation(String rid) {

        int reid = Integer.parseInt(rid);
        Reservation reservation = new Reservation();
        reservation.setReserveId(reid);
        reservation.setCompleteTime(new Date());
        reservation.setReserveStatus("Confirmed");
        int i = reservationDAO.changeStatus(reservation);

        if(i >0){
            return new ResultVO(RespondStatus.OK, "Reservation confirmed", null);
        }else{
            return new ResultVO(RespondStatus.NO, "Failed to confirm", null);
        }


    }

    @Override
    public ResultVO closeReservation(String rid) {
        //  public int changeStatus(Reservation reservation);
        int reid = Integer.parseInt(rid);
        Reservation reservation = new Reservation();
        reservation.setReserveId(reid);
        reservation.setReserveStatus("Completed");
        reservation.setCompleteTime(new Date()); // it's close time
        int i = reservationDAO.completeReservation(reservation);
        if(i >0){
            return new ResultVO(RespondStatus.OK, "Reservation completed", null);
        }else{
            return new ResultVO(RespondStatus.NO, "Failed to complete", null);
        }


    }

    @Override
    public ResultVO rejectReservation(String rid) {

        //  public int changeStatus(Reservation reservation);
        int reid = Integer.parseInt(rid);
        Reservation reservation = new Reservation();
        reservation.setReserveId(reid);
        reservation.setReserveStatus("Rejected");
        reservation.setCancelTime(new Date()); // it's close time
        int i = reservationDAO.changeStatus(reservation);
        if(i >0){
            return new ResultVO(RespondStatus.OK, "Reservation rejected", null);
        }else{
            return new ResultVO(RespondStatus.NO, "Failed to reject", null);
        }
    }

    @Override
    public ResultVO editTotalAmountStatus(String rid, double totalAmount) {
        int reid = Integer.parseInt(rid);
        int i = reservationDAO.updateTotalAmountComplete(reid, totalAmount, new Date());
        if(i >0){
            return new ResultVO(RespondStatus.OK, "Reservation complete", null);
        }else{
            return new ResultVO(RespondStatus.NO, "Failed to complete", null);
        }

    }

    @Override
    public List<Reservation> getPetServiceMontlySale(String storeId) {
        int sid = Integer.parseInt(storeId);
        List<Reservation> reservations = reservationDAO.monthlyServiceProfit(sid);
        return reservations;
    }

    @Override
    public List<ReservationVO> getFiveRecentReservation(String storeId) {
        int sid = Integer.parseInt(storeId);
        List<ReservationVO> reservationVOS = reservationDAO.fiveRecentReservation(sid);
        return reservationVOS;
    }

    @Override
    public ReservationVO getBestReservedService(String storeId) {
        int sid = Integer.parseInt(storeId);
        ReservationVO reservationVO = reservationDAO.bestReservedService(sid);
        return reservationVO;
    }

    @Override
    public List<ReservationVO> getTopFiveService(String storeId) {
        int sid = Integer.parseInt(storeId);
        List<ReservationVO> reservationVOS = reservationDAO.topFiveReservedService(sid);
        return reservationVOS;
    }

    @Override
    public List<Double> getLastMonthDailyReservationProfit(String storeId) {

        int sid = Integer.parseInt(storeId);
        List<Reservation> reservations = reservationDAO.lastMonthDailyProfit(sid);
        List<Double> profit = new ArrayList<>(Collections.nCopies(31, 0.0));

        for(int i =0; i < reservations.size(); i++){
            Reservation reservation = reservations.get(i);
            int index = Integer.parseInt(reservation.getCheckInDate())-1;
            profit.set(index, reservation.getTotalAmount());
        }

        return profit;
    }

    @Override
    public List<Double> getCurrentMonthDailyReservationProfit(String storeId) {
        int sid = Integer.parseInt(storeId);
        List<Reservation> reservations = reservationDAO.currentMonthDailyProfit(sid);
        List<Double> profit = new ArrayList<>(Collections.nCopies(31, 0.0));

        for(int i =0; i < reservations.size(); i++){
            Reservation reservation = reservations.get(i);
            int index = Integer.parseInt(reservation.getCheckInDate())-1;
            profit.set(index, reservation.getTotalAmount());
        }

        return profit;
    }

    /**
     *
     * @param storeId
     * @return total sale, total number of reservation, number of different month
     */
    @Override
    public Reservation getTotalSale(String storeId) {
        Reservation reservation = reservationDAO.totalReservation(Integer.parseInt(storeId));
        return reservation;
    }


}
