package client.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.exception.ExceptionRest;
import core.filter.FilterInterceptor.PublicEndpoint;
import core.filter.SessionRequest;
import core.mvc.MvcConfiguration;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private SessionRequest sessionRequest;

    @GetMapping("/public/ok")
    @PublicEndpoint
    public ResponseEntity<Map<String,String>> publicOk(){
        return sameTestResponse("publicOk", false);
    }

    @GetMapping("/public/error")
    @PublicEndpoint
    public ResponseEntity<Map<String,String>> publicError(){
        return sameTestResponse("publicError", true);
    }

    @GetMapping("/api/ok")
    public ResponseEntity<Map<String,String>> apiOk(){
        return sameTestResponse("apiOk", false);
    }

    
    @GetMapping("/api/error")
    public ResponseEntity<Map<String,String>> apiError(){
        return sameTestResponse("apiError", true);
    }

    private ResponseEntity<Map<String,String>> sameTestResponse(String nameMethod, boolean throwError){

        ExceptionRest.throwInternalServerIF("error in %s".formatted(nameMethod), throwError);

        return ResponseEntity.ok(Map.of(
            "method", nameMethod,
            "message", "OK",
            MvcConfiguration.HEADER_NAME_X_ANY_KEY_AUTH_ID , sessionRequest == null || sessionRequest.getAnyKeyAuthId() == null ? "" : sessionRequest.getAnyKeyAuthId()
        ));
    }
    
}
