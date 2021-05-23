package com.gb.patterns.retyrpattern.rest;

import com.gb.patterns.retyrpattern.retries.RetryWithDelay;
import com.gb.patterns.retyrpattern.retries.RetryWithDelayBackoffAndJitter;
import com.gb.patterns.retyrpattern.retries.SimpleRetry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.gb.patterns.retyrpattern.constants.Constants.FAILURE;

@RestController
@RequestMapping("/api/v1/retry-pattern")
public class RetryController {
    private final SimpleRetry simpleRetry;
    private final RetryWithDelay retryWithDelay;
    private final RetryWithDelayBackoffAndJitter retryWithDelayBackoffAndJitter;

    public RetryController(SimpleRetry simpleRetry, RetryWithDelay retryWithDelay,
                           RetryWithDelayBackoffAndJitter retryWithDelayBackoffAndJitter) {
        this.simpleRetry = simpleRetry;
        this.retryWithDelay = retryWithDelay;
        this.retryWithDelayBackoffAndJitter = retryWithDelayBackoffAndJitter;
    }

    @RequestMapping(path = "/simple", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> simpleRetry(@RequestBody Map<?, ?> params) {
        String result = simpleRetry.doRemoteCall();
        if (FAILURE.equalsIgnoreCase(result))
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @RequestMapping(path = "/delay", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> delayedRetry(@RequestBody Map<?, ?> params) {
        String result = null;
        try {
            result = retryWithDelay.doRemoteCall();
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @RequestMapping(path = "/backedoff", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<?> backedOffRetry(@RequestBody Map<?, ?> params) {
        String result = null;
        try {
            result = retryWithDelayBackoffAndJitter.doRemoteCall();
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}
