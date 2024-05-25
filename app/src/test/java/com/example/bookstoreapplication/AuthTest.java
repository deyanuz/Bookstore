package com.example.bookstoreapplication;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class AuthTest {

    private FirebaseAuth mockAuth;
    private FirebaseFirestore mockFirestore;
    private FirebaseUser mockUser;
    private Auth auth;
    private MockedStatic<FirebaseAuth> mockedFirebaseAuth;
    private MockedStatic<FirebaseFirestore> mockedFirebaseFirestore;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockAuth = mock(FirebaseAuth.class);
        mockFirestore = mock(FirebaseFirestore.class);
        mockUser = mock(FirebaseUser.class);

        mockedFirebaseAuth = mockStatic(FirebaseAuth.class);
        mockedFirebaseAuth.when(FirebaseAuth::getInstance).thenReturn(mockAuth);

        mockedFirebaseFirestore = mockStatic(FirebaseFirestore.class);
        mockedFirebaseFirestore.when(FirebaseFirestore::getInstance).thenReturn(mockFirestore);

        // Set up the mock behavior
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.getUid()).thenReturn("test_uid");

        auth = new Auth(mockAuth, mockFirestore,mockUser);
    }

    @After
    public void tearDown() {
        mockedFirebaseAuth.close();
        mockedFirebaseFirestore.close();
    }

    @Test
    public void testGetInstance() {
        try (MockedStatic<Auth> mockedAuth = mockStatic(Auth.class)) {
            Auth instance1 = Auth.getInstance();
            Auth instance2 = Auth.getInstance();
            assertSame(instance1, instance2);
        }
    }

    @Test
    public void testGetAuth() {
        assertEquals(mockAuth, auth.getAuth());
    }

    @Test
    public void testGetFirestore() {
        assertEquals(mockFirestore, auth.getFirestore());
    }

    @Test
    public void testGetUserUid() {
        String uid = auth.getUserUid();
        assertEquals("test_uid", uid);
    }
}
