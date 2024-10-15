package com.ironoc.db;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.SpringApplication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {

    // mocks
    private MockedStatic<SpringApplication> springApplicationMockedStatic = mockStatic(SpringApplication.class);

    @Test
    public void test_main_success() {
        // given
        String[] args = { "test" };

        // when
        App.main(args);

        // then
        springApplicationMockedStatic.verify(() -> SpringApplication.run(App.class, args),
                times(1)
        );
    }
}
