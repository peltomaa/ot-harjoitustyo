package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertNotNull(kortti);
    }

    @Test
    public void lataus() {
        int lataus = 100;
        int saldo = kortti.saldo() + lataus;
        kortti.lataaRahaa(lataus);
        assertEquals(kortti.saldo(), saldo);
    }

    @Test
    public void toStringTesti() {
        kortti.lataaRahaa(100);
        assertEquals(kortti.toString(), "saldo: 1.10");
    }

    @Test
    public void otto() {
        assertTrue(kortti.otaRahaa(10));
        assertFalse(kortti.otaRahaa(100));
    }
}
