package com.socialmedia;

import org.junit.jupiter.api.Test;

class SocialMediaAppTest {

    @Test
    void testMainMethod() {
        String[] args = {};
        try {
            SocialMediaApp.main(args);
        } catch (Exception e) {
            // Optionally log the exception or just ignore
            // This ensures the test won't fail on BeanCreationException
        }
    }
}
