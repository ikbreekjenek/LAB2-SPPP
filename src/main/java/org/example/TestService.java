package org.example;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Deprecated
    public void deprecatedMethod() {
        System.out.println("Deprecated method executed");
    }

    public void normalMethod() {
        System.out.println("Normal method executed");
    }

    public void longRunningMethod() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("Long running method executed");
    }
}
