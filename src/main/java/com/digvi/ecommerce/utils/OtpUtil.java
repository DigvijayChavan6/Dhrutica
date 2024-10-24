package com.digvi.ecommerce.utils;

import java.util.Random;

public class OtpUtil {
    public static String generateOtp(){
        int otpLength=6;
        StringBuilder otp=new StringBuilder(otpLength);
        Random random=new Random();
        for(int i=0; i<otpLength; ++i){
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
