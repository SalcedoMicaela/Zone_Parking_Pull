package ec.edu.espe.zone_core.zone_core.utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
@RequiredArgsConstructor
public class ClientInfoUtil {

    private final HttpServletRequest request;

    public String getClientIp() {
        String xfHeader = request.getHeader("X-Forwarded-For");

        if (xfHeader != null && !xfHeader.isBlank()) {
            return xfHeader.split(",")[0];
        }

        String ip = request.getRemoteAddr();

        // Si es localhost IPv6, devolver IPv4 real
        if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
            return getLocalIpv4();
        }

        return ip;
    }

    public String getClientHost() {
        String host = request.getRemoteHost();

        if ("0:0:0:0:0:0:0:1".equals(host) || "::1".equals(host)) {
            return getHostName();
        }

        return host;
    }

    private String getLocalIpv4() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }

    private String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }
}
