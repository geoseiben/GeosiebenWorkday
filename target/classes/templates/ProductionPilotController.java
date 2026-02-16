package templates;

import org.springframework.stereotype.Controller;

@Controller
public class ProductionPilotController {
    @GetMapping("/admin/pilot/clients_stats")
    public String clientStats() {
        return "clientPilotStats";
    }
}
