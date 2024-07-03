package com.threedollar;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = {CommunityApiApplication.class})
public abstract class IntegrationTest {

}
