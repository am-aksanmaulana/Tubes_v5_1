package am.aksanmaulana.gmail.com.tubes_v5_1.helper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class KursRupiahConvert {

    public KursRupiahConvert() {
    }

    public String convertPrice(double priceProduct){
        String result="";

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        result = kursIndonesia.format(priceProduct);
        //System.out.printf("Harga Rupiah: %s %n", kursIndonesia.format(harga));
        //tvBalanceDuitKu.setText("" + kursIndonesia.format(Double.valueOf(D.balanceDuitKu)));

        return result;
    }

}
