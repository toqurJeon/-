package com.ssafy.moiroomserver.area.constant;

public enum MetropolitanNumber {

    SEOUL(1),
    BUSAN(2),
    DAEGU(3),
    INCHEON(4),
    DAEJEON(5),
    ULSAN(6),
    SEJONG(7),
    GYEONGGI(8),
    GANGWON(9),
    CHUNGBUK(10),
    CHUNGNAM(11),
    JEONBUK(12),
    JEONNAM(13),
    GYEONGBUK(14),
    GYEONGNAM(15),
    JEJU(16);

    private final int value;

    MetropolitanNumber(int value) {
        this.value = value;
    }

}
