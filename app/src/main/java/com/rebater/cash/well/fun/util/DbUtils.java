package com.rebater.cash.well.fun.util;

import android.content.Context;

import com.rebater.cash.well.fun.bean.PayDbDetail;
import com.rebater.cash.well.fun.bean.WithdrawalPanel;
import com.rebater.cash.well.fun.green.GreenDaoUtils;
import com.rebater.cash.well.fun.green.PayDbDetailDao;

import java.util.ArrayList;
import java.util.List;

public class DbUtils {

    public static List<WithdrawalPanel.PayDetail> getTable(Context context) {
        try {
            PayDbDetailDao payDbDetailDao = GreenDaoUtils.getSingleTon(context).getDaoSession().getPayDbDetailDao();
            if (payDbDetailDao != null) {
                List<PayDbDetail> list = payDbDetailDao.loadAll();
                if (list != null && list.size() > 0) {
                    LogInfo.e("--s--" + list.size());
                    List<WithdrawalPanel.PayDetail> payDetailList = new ArrayList<>();
                    for (PayDbDetail payDbDetail : list) {
                        WithdrawalPanel.PayDetail payDetail = new WithdrawalPanel.PayDetail();
                        payDetail.id = payDbDetail.getPayId();
                        payDetail.cash_amount = payDbDetail.getCash_amount();
                        payDetail.desc = payDbDetail.getDesc();
                        payDetailList.add(payDetail);
                    }
                    if (payDetailList.size() > 0) {
                        return payDetailList;
                    }
                } else {
                    LogInfo.e("--nodb--");

                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
        return null;
    }

    public static int getConversion(Context context) {
        try {
            PayDbDetailDao payDbDetailDao = GreenDaoUtils.getSingleTon(context).getDaoSession().getPayDbDetailDao();
            if (payDbDetailDao != null) {
                List<PayDbDetail> list = payDbDetailDao.loadAll();
                if (list != null && list.size() > 0) {
                    int conversion = list.get(0).getConversion();
                    LogInfo.e(conversion + "--s--" + list.size());
                    return conversion;
                } else {
                    LogInfo.e("--nodb--");

                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
        return 1;
    }

    public static void insertPayInfo(Context context, List<WithdrawalPanel.PayDetail> list, int conversion) {
        try {
            PayDbDetailDao payDbDetailDao = GreenDaoUtils.getSingleTon(context).getDaoSession().getPayDbDetailDao();
            if (payDbDetailDao != null) {
                List<PayDbDetail> payDbDetails = new ArrayList<>();
                for (WithdrawalPanel.PayDetail payDetail : list) {
                    PayDbDetail payDbDetail = new PayDbDetail();
                    payDbDetail.setPayId(payDetail.id);
                    payDbDetail.setCash_amount(payDetail.cash_amount);
                    payDbDetail.setCreateTime(System.currentTimeMillis());
                    payDbDetail.setConversion(conversion);
                    payDbDetail.setPay_type(payDetail.pay_type);
                    payDbDetail.setDesc(payDetail.desc);
                    payDbDetail.setIcon(payDetail.icon);
                    payDbDetail.setTitle(payDetail.title);
                    payDbDetail.setDate(ProjectTools.getCurrDateFormat());
                    payDbDetails.add(payDbDetail);
                }
                if (payDbDetails.size() > 0) {
                    payDbDetailDao.insertInTx(payDbDetails);
                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    public static void deletePayInfo(Context context) {
        try {
            PayDbDetailDao payDbDetailDao = GreenDaoUtils.getSingleTon(context).getDaoSession().getPayDbDetailDao();
            if (payDbDetailDao != null) {
                payDbDetailDao.deleteAll();
                LogInfo.e("okd");
            }
        } catch (Exception | Error e) {
            LogInfo.e(e.toString());
        }
    }
}
