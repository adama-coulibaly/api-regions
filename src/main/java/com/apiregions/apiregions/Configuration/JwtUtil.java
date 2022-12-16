package com.apiregions.apiregions.Configuration;

public class JwtUtil {
    public  static   final long EXPIRE_TOKEN = 20*60*1000;
    public  static final long REFRESH_TOKEN = 240*60*1000;
    public  static final String SECRET = "MonSecret";
    public  static final String AUTHORIZATION = "Authorization";
    public  static final String PREFIX = "Bearer ";
}
