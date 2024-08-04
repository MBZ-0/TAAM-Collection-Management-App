package com.tbb.taamcollection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.view.View;
import android.widget.TextView;

@RunWith(MockitoJUnitRunner.class)
public class AdminLoginPresenterTest {

    @Mock
    AdminLoginModel model;

    @Mock
    AdminLoginView view;

    @Test
    public void testAuthenticateLoginFalse() {
        String username = "notacorrectusername";
        String password = "stinkypassword";
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        when(model.authenticateLogin(username, password)).thenReturn(false);
        presenter.authenticateLogin(username, password);
        verify(view).setIsValid(false);
    }

    @Test
    public void testAuthenticateLoginTrue() {

    }

    @Test
    public void testLoadHome() {

    }

    @Test
    public void testCheckEmptyUserEmpty() {

    }

    @Test
    public void testCheckEmptyPassEmpty() {

    }

    @Test
    public void testCheckEmptyGood() {

    }
}
