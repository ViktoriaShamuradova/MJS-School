package by.epam.shamuradova.gradle.task1;

import org.apache.commons.lang3.math.NumberUtils;
import java.math.BigDecimal;

public class StringUtils {

    public boolean isPositiveNumber(String str) {

    	if(NumberUtils.isCreatable(str)){
    		//BigDecimal b = NumberUtils.toScaledBigDecimal(str);
    		BigDecimal b = new BigDecimal(str);

    		return b.compareTo(BigDecimal.ZERO) > 0;


    	}
        return false;

    }

    public static void main(String[] args) {
        System.out.println(new StringUtils().isPositiveNumber("-5"));
    }
}
