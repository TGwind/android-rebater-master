package com.rebater.cash.well.fun.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Odfi3a implements Parcelable {
    public int id,step,award,down_type,reward_type,step1_award,step1_time,step1_type,step1_status,step2_award,step2_time,step2_type
            ,step2_status,step3_award,step3_time,step3_type, step3_status,step4_award,step4_time,step4_type,step4_status,step5_award
            ,step5_time,step5_type,step5_status,step2_day,step3_day,step4_day,step5_day,step2_reward_type,step3_reward_type,step4_reward_type,step5_reward_type;
    public String  tpl_id,step1_title,step1_desc,step2_title,step2_desc,step3_title,
            step3_desc,step4_title,step4_desc,step5_title,step5_desc,image_url,title,desc,down_pkg,tracking_link,uptime,image_max;

    public Odfi3a(Parcel in) {
        id=in.readInt();
        step=in.readInt();
        award = in.readInt();
        down_type = in.readInt();
        reward_type = in.readInt();
        step1_award = in.readInt();
        step1_time = in.readInt();
        step1_type = in.readInt();
        step1_status = in.readInt();
        step2_award = in.readInt();
        step2_time = in.readInt();
        step2_type = in.readInt();
        step2_status = in.readInt();
        step3_award = in.readInt();
        step3_time = in.readInt();
        step3_type = in.readInt();
        step3_status = in.readInt();
        step4_award = in.readInt();
        step4_time = in.readInt();
        step4_type = in.readInt();
        step4_status = in.readInt();
        step5_award = in.readInt();
        step5_time = in.readInt();
        step5_type = in.readInt();
        step5_status = in.readInt();
        step2_day=in.readInt();
        step3_day=in.readInt();
        step4_day=in.readInt();
        step5_day=in.readInt();
        step2_reward_type=in.readInt();
        step3_reward_type=in.readInt();
        step4_reward_type=in.readInt();
        step5_reward_type=in.readInt();
        tpl_id= in.readString();
        step1_title = in.readString();
        step1_desc = in.readString();
        step2_title = in.readString();
        step2_desc = in.readString();
        step3_title = in.readString();
        step3_desc = in.readString();
        step4_title = in.readString();
        step4_desc = in.readString();
        step5_title = in.readString();
        step5_desc = in.readString();
        image_url = in.readString();
        title = in.readString();
        desc = in.readString();
        down_pkg = in.readString();
        tracking_link = in.readString();
        uptime=in.readString();
        image_max=in.readString();
    }

    public static final Creator<Odfi3a> CREATOR = new Creator<Odfi3a>() {
        @Override
        public Odfi3a createFromParcel(Parcel in) {
            return new Odfi3a(in);
        }

        @Override
        public Odfi3a[] newArray(int size) {
            return new Odfi3a[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(step);
        dest.writeInt(award);
        dest.writeInt(down_type);
        dest.writeInt(reward_type);
        dest.writeInt(step1_award);
        dest.writeInt(step1_time);
        dest.writeInt(step1_type);
        dest.writeInt(step1_status);
        dest.writeInt(step2_award);
        dest.writeInt(step2_time);
        dest.writeInt(step2_type);
        dest.writeInt(step2_status);
        dest.writeInt(step3_award);
        dest.writeInt(step3_time);
        dest.writeInt(step3_type);
        dest.writeInt(step3_status);
        dest.writeInt(step4_award);
        dest.writeInt(step4_time);
        dest.writeInt(step4_type);
        dest.writeInt(step4_status);
        dest.writeInt(step5_award);
        dest.writeInt(step5_time);
        dest.writeInt(step5_type);
        dest.writeInt(step5_status);
        dest.writeInt(step2_day);
        dest.writeInt(step3_day);
        dest.writeInt(step4_day);
        dest.writeInt(step5_day);
        dest.writeInt(step2_reward_type);
        dest.writeInt(step3_reward_type);
        dest.writeInt(step4_reward_type);
        dest.writeInt(step5_reward_type);
        dest.writeString(tpl_id);
        dest.writeString(step1_title);
        dest.writeString(step1_desc);
        dest.writeString(step2_title);
        dest.writeString(step2_desc);
        dest.writeString(step3_title);
        dest.writeString(step3_desc);
        dest.writeString(step4_title);
        dest.writeString(step4_desc);
        dest.writeString(step5_title);
        dest.writeString(step5_desc);
        dest.writeString(image_url);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(down_pkg);
        dest.writeString(tracking_link);
        dest.writeString(uptime);
        dest.writeString(image_max);
    }
}
