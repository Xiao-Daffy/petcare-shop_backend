package com.psm.controller;


import com.psm.petcare.entity.OrderVO;
import com.psm.petcare.entity.Pet;
import com.psm.petcare.entity.Reservation;
import com.psm.petcare.entity.ReservationVO;
import com.psm.petcare.service.OrderService;
import com.psm.petcare.service.PetService;
import com.psm.petcare.service.ReservationService;
import com.psm.petcare.vo.RespondStatus;
import com.psm.petcare.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin
public class DataAnalysisController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private PetService petService;
    /**
     * This is the controller deal with data analysis on dashboard
     *
     */

    /**
     *
     * @param storeId
     * @return monthly product sale, and monthly pet care service sale
     */
    @GetMapping("/monthly/{sid}")
    public ResultVO getMonthlyProfit(@PathVariable("sid") String storeId){
        HashMap<String, Object> result = new HashMap<>();

        // get product month sale
        List<OrderVO> productMontlySale = orderService.getProductMontlySale(storeId);
        result.put("product", productMontlySale);

        // get pet service month sale
        List<Reservation> petServiceMontlySale = reservationService.getPetServiceMontlySale(storeId);
        result.put("service", petServiceMontlySale);
        return new ResultVO(RespondStatus.OK, "monthly", result);
    }

    /**
     *
     * @param storeId
     * @return get 5 records of recent orders and 5 recent reservations
     */
    @GetMapping("/recent/{sid}")
    public ResultVO getRecentOrderReservation(@PathVariable("sid") String storeId){
        HashMap<String, Object> result = new HashMap<>();
        List<OrderVO> fiveRecentOrders = orderService.getFiveRecentOrders(storeId);
        List<ReservationVO> fiveRecentReservation = reservationService.getFiveRecentReservation(storeId);
        result.put("order", fiveRecentOrders);
        result.put("reservation", fiveRecentReservation);

        return new ResultVO(RespondStatus.OK, "recent orders and reservations", result);
    }

    /**
     *
     * @param storeId
     * @return get
     */
    @GetMapping("/best/{sid}")
    public ResultVO getBestProductService(@PathVariable("sid") String storeId){


        HashMap<String, Object> result = new HashMap<>();
        // pet service
        ReservationVO bestReservedService = reservationService.getBestReservedService(storeId);
        // pet product
        OrderVO bestSoldProduct = orderService.getBestSoldProduct(storeId);
        result.put("service", bestReservedService);
        result.put("product", bestSoldProduct);
        return new ResultVO(RespondStatus.OK, "best orders and reservations", result);
    }

    /**
     *
     * @param storeId
     * @return last month and current month profit compare
     */
    @GetMapping("/compare/{sid}")
    public ResultVO getLastCurrentMonthProfit(@PathVariable("sid") String storeId){
        HashMap<String, Object> result = new HashMap<>();
        List<Double> lastMonthDailyReservationProfit = reservationService.getLastMonthDailyReservationProfit(storeId);
        List<Double> currentMonthDailyReservationProfit = reservationService.getCurrentMonthDailyReservationProfit(storeId);

        List<Double> currentMonthDailyOrderProfit = orderService.getCurrentMonthDailyOrderProfit(storeId);
        List<Double> lastMonthDailyOrderProfit = orderService.getLastMonthDailyOrderProfit(storeId);

        result.put("lastReservation", lastMonthDailyReservationProfit);
        result.put("currentReservation", currentMonthDailyReservationProfit);
        result.put("lastOrder", lastMonthDailyOrderProfit);
        result.put("currentOrder", currentMonthDailyOrderProfit);
        return new ResultVO(RespondStatus.OK, "best orders and reservations", result);
    }


    /**
     *
     * @param storeId
     * @return get top 5 selled product and service
     */
    @GetMapping("/top/{sid}")
    public ResultVO getTopProductService(@PathVariable("sid") String storeId){

        HashMap<String, Object> result = new HashMap<>();
        // pet service
        List<ReservationVO> topFiveService = reservationService.getTopFiveService(storeId);

        // pet product
        List<OrderVO> fiveTopProduct = orderService.getFiveTopProduct(storeId);

        result.put("service", topFiveService);
        result.put("product", fiveTopProduct);
        return new ResultVO(RespondStatus.OK, "5 top orders and reservations", result);
    }


    /**
     *
     * @param storeId
     * @return Pet numbers with it's species
     */
    @GetMapping("/pets/{sid}")
    public ResultVO getPets(@PathVariable("sid") String storeId){

        return null;
    }


    /**
     *
     * @param storeId
     * @return get the number of product sold and service reserved, and total product sale, service
     */
    @GetMapping("/type/{sid}")
    public ResultVO getSaleType(@PathVariable("sid") String storeId){

        return null;
    }

    /**
     *
     * @param storeId
     * @return get the pet species
     */
    @GetMapping("/pet/{sid}")
    public ResultVO getPetType(@PathVariable("sid") String storeId){
        List<Pet> petType = petService.getPetType(storeId);

        return new ResultVO(RespondStatus.OK, "pet type", petType);
    }

    /**
     *
     * @param storeId
     * @return get the total sale, number of bought, number of different month
     */
    @GetMapping("/total/{sid}")
    public ResultVO getAllTotal(@PathVariable("sid") String storeId){

        HashMap<String, Object> result = new HashMap<>();
        OrderVO totalSale = orderService.getTotalSale(storeId);

        Reservation totalReservation = reservationService.getTotalSale(storeId);

        // months for calculating sale
        int months = totalSale.getProductId() >= totalReservation.getNumOfPet()? totalSale.getProductId(): totalReservation.getNumOfPet();
        result.put("order", totalSale);
        result.put("reservation", totalReservation);
        result.put("months", months);

        return new ResultVO(RespondStatus.OK, "pet type", result);
    }

}
