package org.sdoroshenko;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Permission;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    private SecurityManager originalSecurityManager;

    @BeforeEach
    public void setup() {
        originalSecurityManager = System.getSecurityManager();
        System.setSecurityManager(new NoExitSecurityManager());
    }

    @AfterEach
    public void tearDown() {
        System.setSecurityManager(originalSecurityManager);
    }

    @Test
    void GIVEN_validCommand_WHEN_main_THEN_return0() {
        try {
            String[] args = {"check", "pom.xml", "-a=MD-5"};
            App.main(args);
        } catch (ExitException e) {
            assertEquals(0, e.status);
        }
    }

    @Test
    void GIVEN_invalidCommand_WHEN_main_THEN_return1() {
        try {
            String[] args = {"invalidCommand", "pom.xml", "-a=MD-5"};
            App.main(args);
        } catch (ExitException e) {
            assertEquals(1, e.status);
        }
    }

    protected static class ExitException extends SecurityException {
        public final int status;
        public ExitException(int status) {
            super("No system exit!");
            this.status = status;
        }
    }

    private static class NoExitSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(Permission perm) {
            // allow anything.
        }

        @Override
        public void checkPermission(Permission perm, Object context) {
            // allow anything.
        }

        @Override
        public void checkExit(int status) {
            super.checkExit(status);
            throw new ExitException(status);
        }
    }
}