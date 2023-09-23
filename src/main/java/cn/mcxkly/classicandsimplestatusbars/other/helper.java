package cn.mcxkly.classicandsimplestatusbars.other;

public class helper {
    public static String KeepOneDecimal(float d) {
        if (d > 0) {
            d = (float) (Math.ceil(d * 10) / 10);
            String s = String.valueOf(d);
            return s.replace(".0", "");
        } else {
            return "";
        }
    }
}