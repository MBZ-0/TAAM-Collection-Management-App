package com.tbb.taamcollection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminLoginPresenterTest {

    @Mock
    AdminLoginModel model;

    @Mock
    AdminLoginView view;

    @Test
    public void exampleTest() {
        assertEquals(1, 1);
    }
}
