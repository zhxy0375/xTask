package org.xtask.tool;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Description: 与IP地址相关的UTIL
 */
public class IPTool {

    /**
     * 获得本机IP地址 集合 （多网卡多ip的情况）
     *
     * @return List<String>
     */
    public static List<String> getLocalIp() {
        List<String> ipList = new ArrayList<String>();
        Pattern pattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        try {
            Enumeration<NetworkInterface> networkList = NetworkInterface
                    .getNetworkInterfaces();
            while (networkList.hasMoreElements()) {
                Enumeration<InetAddress> list = networkList.nextElement().getInetAddresses();
                while (list.hasMoreElements()) {
                    String ip = list.nextElement().getHostAddress();
                    if (pattern.matcher(ip).matches()) {
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipList;
    }


}
