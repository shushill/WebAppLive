package com.sushil.project.common.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Util {

    public static void main(String args[]){
        PasswordEncoder pe = new BCryptPasswordEncoder();
        System.out.println(pe.encode("sushil"));
    }

}
