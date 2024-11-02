import org.springframework.stereotype.Controller; 
import org.springframework.stereotype.RestController; 
import org.springframework.stereotype.RequestMapping; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/jsonrpc")
public class JsonRpcController {

    private final MathService mathService;

    @Autowired
    public JsonRpcController(MathService mathService) {
        this.mathService = mathService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> handleJsonRpc(@RequestBody Map<String, Object> request) {
        String method = (String) request.get("method");
        List<Integer> params = (List<Integer>) request.get("params");
        int id = (Integer) request.get("id");

        int result;
        switch (method) {
            case "add":
                result = mathService.add(params.get(0), params.get(1));
                break;
            case "subtract":
                result = mathService.subtract(params.get(0), params.get(1));
                break;
            default:
                return ResponseEntity.badRequest().body(Map.of("jsonrpc", "2.0", "error", "Method not found", "id", id));
        }

        return ResponseEntity.ok(Map.of("jsonrpc", "2.0", "result", result, "id", id));
    }
}
