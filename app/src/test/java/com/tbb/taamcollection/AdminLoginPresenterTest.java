package com.tbb.taamcollection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
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
        doAnswer(invocation -> {
            AdminLoginModel.AuthCallback callback = invocation.getArgument(2);
            callback.onFailure();
            return null;
        }).when(model).authenticateLogin(eq(username), eq(password), any(AdminLoginModel.AuthCallback.class));
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        presenter.authenticateLogin(username, password);
        verify(view).setIsValid(false);
    }

    @Test
    public void testAuthenticateLoginTrue() {
        String username = "correctusername";
        String password = "coolpassword";
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        //when(model.authenticateLogin(username, password)).thenReturn(true);
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
        String username = "";
        String password = "good password";
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        presenter.checkEmpty(username, password);
        verify(view).setEmptyPassUser(true);
    }

    @Test
    public void testCheckEmptyPassEmpty() {
        String username = "good username";
        String password = "";
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        presenter.checkEmpty(username, password);
        verify(view).setEmptyPassUser(true);
    }

    @Test
    public void testCheckEmptyGood() {
        String username = "good username";
        String password = "good password";
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        presenter.checkEmpty(username, password);
        verify(view).setEmptyPassUser(false);
    }

    @Test
    public void testDoLogicGood() {
        String username = "good username";
        String password = "good password";
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        //when(model.authenticateLogin(username, password)).thenReturn(true);
        when(view.getParentFragmentManager()).thenReturn(fragmentManager);
        when(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction);
        presenter.doLogic(username, password, view);
        verify(view, times(2)).setEmptyPassUser(false);
        verify(view, times(2)).setIsValid(true);
    }

    @Test
    public void testDoLogicEmpty() {
        String username = "";
        String password = "good password";
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        presenter.doLogic(username, password, view);
        verify(view).setEmptyPassUser(false);
        verify(view).setIsValid(true);
        verify(view).setEmptyPassUser(true);
    }

    @Test
    public void testDoLogicbadLogin() {
        String username = "bad username";
        String password = "bad password";
        AdminLoginPresenter presenter = new AdminLoginPresenter(view, model);
        //when(model.authenticateLogin(username, password)).thenReturn(false);
        when(view.getParentFragmentManager()).thenReturn(fragmentManager);
        when(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction);
        presenter.doLogic(username, password, view);
        verify(view, times(2)).setEmptyPassUser(false);
        verify(view).setIsValid(true);
        verify(view).setIsValid(false);
    }
}
