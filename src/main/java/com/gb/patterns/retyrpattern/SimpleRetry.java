package com.gb.patterns.retyrpattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleRetry {

    public String doRemoteCall() {
        int maxRetries = 5;
        int currentAttempt = 1;
        do {
            try {
                someServiceCall(currentAttempt);
            } catch (Exception e) {
                log.error("upstream failure");
                currentAttempt++;
            }

        } while (currentAttempt < maxRetries);
        if (currentAttempt == maxRetries) {
            return "failure";
        }
        return "success";
    }

    private void someServiceCall(int num) throws Exception {
        if (num % 3 == 0) {
            return;
        } else {
            throw new Exception("simulating failure");
        }
    }
}
