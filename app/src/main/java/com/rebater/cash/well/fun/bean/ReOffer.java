package com.rebater.cash.well.fun.bean;

public class ReOffer {
    public DetailsInfo offerInfo;
    public String points;
    public static class DetailsInfo{
        public int id,award,ad_type,down_type,reward_type,step1_award,step1_time,step1_type,step1_status,step2_award,step2_time,step2_type
                ,step2_status,step3_award,step3_time,step3_type, step3_status,step4_award,step4_time,step4_type,step4_status,step5_award
                ,step5_time,step5_type,step5_status,is_open_type,step2_day,step3_day,step4_day,step5_day,step2_reward_type,step3_reward_type,step4_reward_type,step5_reward_type;
        public String  title,desc,down_pkg,step1_title,step1_desc,step2_title,step2_desc,step3_title,
                step3_desc,step4_title,step4_desc,step5_title,step5_desc,image_url,tracking_link,offer_pkg,icon,click_url,url,images,open_pkg,requirements,image_max;
    }
}
