package com.tinieblas.tokomegawa.remote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tinieblas.tokomegawa.data.remote.LoginRepositoryImp;
import com.tinieblas.tokomegawa.data.remote.SignUpRepositoryImp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginRepositoryImpTest {

    String email = "correoactualizado@gmail.com";
    String password = "darkangelo";
    String uid = "gXstERrHIzZzYBPdgPBRTtUuAda2";
    LoginRepositoryImp repository;
    String correoActualizado = "correoActualizado@gmail.com";

    @Before
    public void setup(){
        //Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //FirebaseApp.initializeApp(appContext);
        repository = new LoginRepositoryImp();
    }
    @Test
    public void getEmailUser() {
        Boolean isLogged = repository.login(email, password);
        assertEquals(true, isLogged);
    }

    @Test
    public void getUIDUser() {
        String uidUser = repository.getUIDUser();
        assertEquals(uid, uidUser);
    }

    @Test
    public void login(){
        Boolean isLogged = repository.login(email, password);
        assertTrue(isLogged);
    }

    @Test
    public void updateEmail() {
        String updateEmail = repository.updateEmail(correoActualizado);
        assertEquals("User email address updated.", updateEmail);
    }



/*
    @Test
    public void testCreateAccount() {
        String result = repositoryRegistro.createUser(emailRegistro, passwordContraseña);
        assertEquals(result);
    }


    public boolean createAccount(String email, String password) {
        try {
            String userId = repositoryRegistro.createUser(emailRegistro, passwordContraseña);
            return userId != null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
*/

}