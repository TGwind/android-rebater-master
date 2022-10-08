package com.rebater.cash.well.fun.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Kixiba implements Parcelable {
        public int id,offer_id,award;
        public String offer_pkg,title,desc,requirements,click_url,icon;

    public Kixiba(Parcel in) {
            id = in.readInt();
            offer_id = in.readInt();
            award = in.readInt();
            offer_pkg = in.readString();
            title = in.readString();
            desc = in.readString();
            requirements = in.readString();
            click_url = in.readString();
            icon = in.readString();
        }

        public static final Creator<Kixiba> CREATOR = new Creator<Kixiba>() {
            @Override
            public Kixiba createFromParcel(Parcel in) {
                return new Kixiba(in);
            }

            @Override
            public Kixiba[] newArray(int size) {
                return new Kixiba[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeInt(offer_id);
            dest.writeInt(award);
            dest.writeString(offer_pkg);
            dest.writeString(title);
            dest.writeString(desc);
            dest.writeString(requirements);
            dest.writeString(click_url);
            dest.writeString(icon);
        }

}
