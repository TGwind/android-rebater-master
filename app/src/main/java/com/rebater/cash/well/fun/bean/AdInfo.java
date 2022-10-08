package com.rebater.cash.well.fun.bean;

public class AdInfo {
    public int id, award, ad_type, adad_status, app_sub, down_type, reward_type, contact_type, partner_id, h5_type, is_hot;
    public String img_hot, title, desc, down_pkg, image_url, image_max, image_max1, image_max2, image_max3, tracking_link, contact_url, contact_pkg, app_id, app_key, h5_url, h5_pkg;
    public Steps gp;

    public static class Steps {
        public int step1_award, step1_time, step1_type, step1_status, step2_day, step2_award, step2_time, step2_type, step2_status, step3_award, step3_time, step3_day, step3_type, step3_status, step4_award, step4_time, step4_day, step4_type, step4_status, step5_award, step5_time, step5_day, step5_type, step5_status, step2_reward_type, step3_reward_type, step4_reward_type, step5_reward_type;
        public String step1_title, step1_desc, step2_title, step2_desc, step3_title,
                step3_desc, step4_title, step4_desc, step5_title, step5_desc;
    }
}
