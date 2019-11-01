package info.lzzy.connstants;

import info.lzzy.service.scoket.SocketServer;

public class ApiConstants {

    /**
     * 登录验证超时时间
     */
    public static final long EFFICACY_OF_TIME=1000*5L;
    /**
     * 请求会话key超时时间
     */
    public static final long DIALOGUE_TIME=1000*600L;
    
    public static SocketServer socketServer;
}
