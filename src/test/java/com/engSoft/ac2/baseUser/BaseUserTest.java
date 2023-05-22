package com.engSoft.ac2.baseUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.engSoft.ac2.domain.model.Admin;
import com.engSoft.ac2.domain.model.BaseUser;

public class BaseUserTest {
    
    private BaseUser baseUser;
    
    @BeforeEach
    public void setUp() {
        baseUser = new BaseUser(1, "Pedro Paulo", "pedro.paulo@exemplo.com");
    }
    
    @Test
    public void testGetId() {
        assertEquals(1, baseUser.getId());
    }
    
    @Test
    public void testGetName() {
        assertEquals("Pedro Paulo", baseUser.getName());
    }
    
    @Test
    public void testSetName() {
        baseUser.setName("Thiago Sanches");
        assertEquals("Thiago Sanches", baseUser.getName());
    }
    
    @Test
    public void testGetEmail() {
        assertEquals("pedro.paulo@exemplo.com", baseUser.getEmail());
    }
    
    @Test
    public void testSetEmail() {
        baseUser.setEmail("thiago.sanches@exemplo.com");
        assertEquals("thiago.sanches@exemplo.com", baseUser.getEmail());
    }
    
    @Test
    public void testEquals() {
        BaseUser otherUser = new BaseUser(1, "Pedro Paulo", "pedro.paulo@exemplo.com");
        assertTrue(baseUser.equals(otherUser));
    }
    
    @Test
    public void testEqualsNotSameId() {
        BaseUser otherUser = new BaseUser(2, "Pedro Paulo", "pedro.paulo@exemplo.com");
        assertFalse(baseUser.equals(otherUser));
    }
    
    @Test
    public void testEqualsDifferentClass() {
        Admin admin = new Admin(1, "Pedro Paulo", "pedro.paulo@exemplo.com", "123456789");
        assertFalse(baseUser.equals(admin));
    }
    
    @Test
    public void testHashCode() {
        BaseUser otherUser = new BaseUser(1, "Pedro Paulo", "pedro.paulo@exemplo.com");
        assertEquals(baseUser.hashCode(), otherUser.hashCode());
    }
    
    @Test
    public void testHashCodeDifferentId() {
        BaseUser otherUser = new BaseUser(2, "John Doe", "john.doe@example.com");
        assertNotEquals(baseUser.hashCode(), otherUser.hashCode());
    }
}
