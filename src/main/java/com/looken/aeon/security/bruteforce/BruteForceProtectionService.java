package com.looken.aeon.security.bruteforce;

public interface BruteForceProtectionService {

    void registerLoginFailure(String login);

    void resetBruteForceCounter(String login);
}
