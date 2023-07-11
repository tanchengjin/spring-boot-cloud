package com.tanchengjin.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public class Mocker {
    public static String generateInsertSQL(LinkedHashMap<String, Object> map) {
        int size = map.size();
//        String[] keys = map.keySet().toArray(new String[size]);
        StringBuilder valuesBuilder = new StringBuilder();
        StringBuilder fieldBuilder = new StringBuilder();
        for (String key : map.keySet()) {
            fieldBuilder.append(key);
            fieldBuilder.append(",");
            Object value = map.get(key);
            if (value instanceof Integer || value instanceof Long || value instanceof Double || value instanceof Float || value instanceof Short) {
                valuesBuilder.append(String.valueOf(value));
            } else {
                valuesBuilder.append(String.format("'%s'", value));
            }
            valuesBuilder.append(",");
        }
        String values = valuesBuilder.substring(0, valuesBuilder.length() - 1);
        String fields = fieldBuilder.substring(0, fieldBuilder.length() - 1);
//        String fields = StringUtils.join(keys, ",");
        String sqlInsert = "INSERT INTO table(%s) VALUES(%s)";
        return String.format(sqlInsert, fields, values);
    }

    public static void main(String[] args) {

        for (int i = 0; i < 30; i++) {
            System.out.println(randomDate("2023-05-13 06:00:00", "2023-06-13 06:00:00"));
        }
        LinkedHashMap<String, Object> stringObjectHashMap = new LinkedHashMap<>();
        stringObjectHashMap.put("id", 123);
        stringObjectHashMap.put("villageId", 123);
        stringObjectHashMap.put("name", "姓名");
        System.out.println(generateInsertSQL(stringObjectHashMap));
    }


    private static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);
            Date end = format.parse(endDate);

            if (start.getTime() >= end.getTime()) {
                return null;
            }
            long date = random(start.getTime(), end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }
}
