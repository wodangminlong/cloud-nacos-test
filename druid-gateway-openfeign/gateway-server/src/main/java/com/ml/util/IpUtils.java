package com.ml.util;

import com.alibaba.cloud.commons.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * get requests ip address
 *
 * @author Administrator
 * @date 2021/10/23 10:20
 */
@Slf4j
public class IpUtils {

    private IpUtils() {

    }

    public static String getIpAddr(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ipAddress = headers.getFirst("X-Forwarded-For");
        String unknownString = "unknown";
        boolean ok = StringUtils.isBlank(ipAddress) || unknownString.equalsIgnoreCase(ipAddress);
        if (ok) {
            ipAddress = headers.getFirst("Proxy-Client-IP");
        }
        if (ok) {
            ipAddress = headers.getFirst("WL-Proxy-Client-IP");
        }
        if (ok) {
            ipAddress = getIpAddress(request);
        }
        // more than one ip address , first ip address is real ip address, get first ip address
        String splitString = ",";
        if (ipAddress != null && ipAddress.contains(splitString)) {
            ipAddress = ipAddress.split(",")[0];
        }
        return ipAddress;
    }

    private static String getIpAddress(ServerHttpRequest request) {
        String ipAddress = null;
        InetSocketAddress inetSocketAddress = request.getRemoteAddress();
        if (Objects.nonNull(inetSocketAddress)) {
            ipAddress = inetSocketAddress.getAddress().getHostAddress();
            String localhostIpV4 = "127.0.0.1";
            String localhostIpV6 = "0:0:0:0:0:0:0:1";
            if (localhostIpV4.equals(ipAddress) || localhostIpV6.equals(ipAddress)) {
                try {
                    InetAddress inert = InetAddress.getLocalHost();
                    ipAddress = inert.getHostAddress();
                } catch (UnknownHostException e) {
                    log.error("get ip address by ipv4 or ipv6 has error: ip address{}, error {}", ipAddress, e);
                }
            }
        }
        return ipAddress;
    }

}
