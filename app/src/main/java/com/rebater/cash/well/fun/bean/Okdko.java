package com.rebater.cash.well.fun.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Okdko implements Parcelable {
    public String code,connect,url,desc,button_text,button_image,icom,pkg,open_pkg;
    public int show_pent,id,is_open_type;

    public Okdko(Parcel in) {
        code = in.readString();
        connect = in.readString();
        url = in.readString();
        desc = in.readString();
        button_text = in.readString();
        button_image = in.readString();
        icom = in.readString();
        pkg = in.readString();
        open_pkg = in.readString();
        show_pent = in.readInt();
        id = in.readInt();
        is_open_type = in.readInt();
    }

    public static final Creator<Okdko> CREATOR = new Creator<Okdko>() {
        @Override
        public Okdko createFromParcel(Parcel in) {
            return new Okdko(in);
        }

        @Override
        public Okdko[] newArray(int size) {
            return new Okdko[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(connect);
        dest.writeString(url);
        dest.writeString(desc);
        dest.writeString(button_text);
        dest.writeString(button_image);
        dest.writeString(icom);
        dest.writeString(pkg);
        dest.writeString(open_pkg);
        dest.writeInt(show_pent);
        dest.writeInt(id);
        dest.writeInt(is_open_type);
    }
}
