package com.gb.patterns.retyrpattern.retries;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.gb.patterns.retyrpattern.constants.Constants.FAILURE;
import static com.gb.patterns.retyrpattern.constants.Constants.SUCCESS;

@Slf4j
@Component
public class SimpleRetry {

    public String doRemoteCall() {
        int maxRetries = 5;
        int currentAttempt = 1;
        do {
            try {
                someServiceCall(currentAttempt);
                return SUCCESS;
            } catch (Exception e) {
                log.error("upstream failure");
                currentAttempt++;
            }

        } while (currentAttempt < maxRetries);
        if (currentAttempt == maxRetries) {
            return FAILURE;
        }
        return SUCCESS;
    }

    private void someServiceCall(int num) throws Exception {
        if (num % 3 == 0) {
            return;
        } else {
            throw new Exception("simulating failure");
        }
    }
}
