package com.gb.patterns.retyrpattern.rest;

import com.gb.patterns.retyrpattern.SimpleRetry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.gb.patterns.retyrpattern.Constants.FAILURE;

@RestController
@RequestMapping("/api/v1/retry-pattern")
public class RetryController {
    private final SimpleRetry simpleRetry;

    public RetryController(SimpleRetry simpleRetry) {
        this.simpleRetry = simpleRetry;
    }

    @RequestMapping(path = "/simple", method = RequestMethod.POST)
    public ResponseEntity<?> simpleRetry(@RequestBody Map<?, ?> params) {
        String result = simpleRetry.doRemoteCall();
        if (FAILURE.equalsIgnoreCase(result))
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
