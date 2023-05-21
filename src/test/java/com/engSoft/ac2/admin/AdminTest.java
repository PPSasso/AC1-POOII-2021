package com.engSoft.ac2.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.engSoft.ac2.domain.model.Admin;
import com.engSoft.ac2.domain.model.BaseUser;
import com.engSoft.ac2.domain.model.Event;

public class AdminTest {
    
    private Admin admin;
    
    @BeforeEach
    public void setUp() {
        admin = new Admin(1, "Pedro Paulo", "pedro.paulo@exemplo.com", "123456789");
    }
    
    @Test
    public void testGetId() {
        assertEquals(1, admin.getId());
    }
    
    @Test
    public void testGetName() {
        assertEquals("Pedro Paulo", admin.getName());
    }
    
    @Test
    public void testSetName() {
        admin.setName("Thiago Sanches");
        assertEquals("Thiago Sanches", admin.getName());
    }
    
    @Test
    public void testGetEmail() {
        assertEquals("pedro.paulo@exemplo.com", admin.getEmail());
    }
    
    @Test
    public void testSetEmail() {
        admin.setEmail("thiago.sanches@exemplo.com");
        assertEquals("thiago.sanches@exemplo.com", admin.getEmail());
    }
    
    @Test
    public void testGetPhoneNumber() {
        assertEquals("123456789", admin.getPhoneNumber());
    }
    
    @Test
    public void testSetPhoneNumber() {
        admin.setPhoneNumber("987654321");
        assertEquals("987654321", admin.getPhoneNumber());
    }
    
    @Test
    public void testAddEvent() {
        Event event = new Event();
        admin.addEvent(event);
        assertTrue(admin.getEvents().contains(event));
    }
    
    @Test
    public void testEquals() {
        Admin otherAdmin = new Admin(1, "Pedro Paulo", "pedro.paulo@exemplo.com", "123456789");
        assertTrue(admin.equals(otherAdmin));
    }
    
    @Test
    public void testEqualsNotSameId() {
        Admin otherAdmin = new Admin(2, "Pedro Paulo", "pedro.paulo@exemplo.com", "123456789");
        assertFalse(admin.equals(otherAdmin));
    }
    
    @Test
    public void testEqualsDifferentClass() {
        BaseUser baseUser = new BaseUser(1,"Pedro Paulo","pedro.paulo@exemplo.com");
        assertFalse(admin.equals(baseUser));
    }
    
    @Test
    public void testHashCode() {
        Admin otherAdmin = new Admin(1, "Pedro Paulo", "pedro.paulo@exemplo.com", "123456789");
        assertEquals(admin.hashCode(), otherAdmin.hashCode());
    }
    
    @Test
    public void testHashCodeDifferentId() {
        Admin otherAdmin = new Admin(2, "Pedro Paulo", "pedro.paulo@exemplo.com", "123456789");
        assertNotEquals(admin.hashCode(), otherAdmin.hashCode());
    }
}


