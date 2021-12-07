package com.ml.util;

import com.alibaba.cloud.commons.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author dml
 * @date 2021/12/7 9:46
 */
@Slf4j
public class IpUtils {

    private IpUtils() {

    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        String unknownString = "unknown";
        boolean ok = StringUtils.isBlank(ipAddress) || unknownString.equalsIgnoreCase(ipAddress);
        if (ok) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ok) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ok) {
            ipAddress = request.getRemoteAddr();
        }
        // more than one ip address , first ip address is real ip address, get first ip address
        String splitString = ",";
        if (ipAddress != null && ipAddress.contains(splitString)) {
            ipAddress = ipAddress.split(",")[0];
        }
        String localhostIpV4 = "127.0.0.1";
        if (localhostIpV4.equals(ipAddress)) {
            try {
                ipAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error("getIpAddress has error: ", e);
            }
        }
        return ipAddress;
    }
}
