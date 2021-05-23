package com.gb.patterns.retyrpattern.retries;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.gb.patterns.retyrpattern.constants.Constants.SUCCESS;

@Component
@Slf4j
public class RetryWithDelayBackoffAndJitter {

    @Retryable(value = {Exception.class, IndexOutOfBoundsException.class}, maxAttempts = 4,
            backoff = @Backoff(delay = 1000, multiplier = 2, random = true))
    public String doRemoteCall() throws Exception {

        Random random = new Random();
        int i = random.nextInt();
        someServiceCall(i);
        return SUCCESS;
    }

    private void someServiceCall(int num) throws Exception {
        if (num % 3 == 0) {
            return;
        } else {
            log.error("failure");
            throw new Exception("simulating failure");
        }
    }
}
