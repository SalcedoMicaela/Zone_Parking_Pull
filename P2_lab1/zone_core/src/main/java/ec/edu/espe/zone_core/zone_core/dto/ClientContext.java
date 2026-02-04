package ec.edu.espe.zone_core.zone_core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientContext {

    private String ip;
    private String host;
    private String mac;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (ip != null) {
            map.put("ip", ip);
        }
        if (host != null) {
            map.put("host", host);
        }
        if (mac != null) {
            map.put("mac", mac);
        }
        return map;
    }
}
