package ec.edu.espe.zone_core.zone_core.utils;

import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;

@Component
public class NetworkUtil {

    public String getServerMac() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            if (network == null || network.getHardwareAddress() == null) {
                return "UNKNOWN";
            }

            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();

            for (byte b : mac) {
                sb.append(String.format("%02X:", b));
            }

            return sb.substring(0, sb.length() - 1);

        } catch (Exception e) {
            return "UNKNOWN";
        }
    }
}
