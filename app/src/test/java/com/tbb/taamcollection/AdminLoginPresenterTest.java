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

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

@RunWith(MockitoJUnitRunner.class)
public class AdminLoginPresenterTest {

    @Mock
    AdminLoginModel model;

    @Mock
    AdminLoginView view;

    @Mock
    FragmentManager fragmentManager;

    @Mock
    FragmentTransaction fragmentTransaction;

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
        String username = "correctusername";
        String password = "coolpassword";
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        when(model.authenticateLogin(username, password)).thenReturn(true);
        presenter.authenticateLogin(username, password);
        verify(view).setIsValid(true);
    }

    @Test
    public void testLoadHome() {
        when(view.getParentFragmentManager()).thenReturn(fragmentManager);
        when(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction);
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        presenter.loadHome(view);
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
