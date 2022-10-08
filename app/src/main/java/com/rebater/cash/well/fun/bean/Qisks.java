package com.rebater.cash.well.fun.bean;

import java.util.List;

public class Qisks {
    public Level level;
    public List<Deal> deal;
    public class Level{
        public int integral,set_points;
    }

    public class Deal{
        public float amount;
        public String currency;
    }
}
