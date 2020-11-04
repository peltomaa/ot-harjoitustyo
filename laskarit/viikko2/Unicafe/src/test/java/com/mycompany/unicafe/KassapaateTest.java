package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    Kassapaate kassapaate;
    Maksukortti kortti;

    int edullisesti = 240;
    int maukkaasti = 400;
    int reiluMaksu = 500;
    int liianVähänMaksu = 100;

    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
        kortti = new Maksukortti(10);
    }

    @Test
    public void aloitus() {
        assertEquals(kassapaate.kassassaRahaa(), 100000);
        assertEquals(kassapaate.edullisiaLounaitaMyyty(), 0);
        assertEquals(kassapaate.maukkaitaLounaitaMyyty(), 0);
    }

    @Test
    public void testi1() {
        int kassa = kassapaate.kassassaRahaa();
        int palautus1 = kassapaate.syoEdullisesti(reiluMaksu);

        assertEquals(palautus1, reiluMaksu - edullisesti);
        assertEquals(kassapaate.kassassaRahaa(), kassa + edullisesti);

        int palautus2 = kassapaate.syoMaukkaasti(reiluMaksu);

        assertEquals(palautus2, reiluMaksu - maukkaasti);
        assertEquals(kassapaate.kassassaRahaa(), kassa + edullisesti + maukkaasti);
    }

    @Test
    public void testi2() {
        kassapaate.syoEdullisesti(reiluMaksu);
        assertEquals(kassapaate.edullisiaLounaitaMyyty(), 1);
        kassapaate.syoMaukkaasti(reiluMaksu);
        assertEquals(kassapaate.maukkaitaLounaitaMyyty(), 1);
    }

    @Test
    public void testi3() {
        assertEquals(kassapaate.syoEdullisesti(liianVähänMaksu), liianVähänMaksu);
        assertEquals(kassapaate.edullisiaLounaitaMyyty(), 0);

        assertEquals(kassapaate.syoMaukkaasti(liianVähänMaksu), liianVähänMaksu);
        assertEquals(kassapaate.maukkaitaLounaitaMyyty(), 0);
    }

    @Test
    public void korttiTestiEdullisesti() {
        int kassa = kassapaate.kassassaRahaa();

        int saldo = 300;
        final int saldoOstoksenJalkeen = saldo - edullisesti;
        resetoiKortti(saldo);

        assertTrue(kassapaate.syoEdullisesti(kortti));

        System.out.println(kassapaate.kassassaRahaa());

        assertEquals(kortti.saldo(), saldoOstoksenJalkeen);
        assertEquals(kassapaate.edullisiaLounaitaMyyty(), 1);
        assertEquals(kassapaate.kassassaRahaa(), kassa);

        assertFalse(kassapaate.syoEdullisesti(kortti));
        assertEquals(kortti.saldo(), saldoOstoksenJalkeen);
        assertEquals(kassapaate.kassassaRahaa(), kassa);
    }

    @Test
    public void korttiTestiMaukkaasti() {
        int kassa = kassapaate.kassassaRahaa();

        int saldo = 500;
        final int saldoOstoksenJalkeen = saldo - maukkaasti;
        resetoiKortti(saldo);

        assertTrue(kassapaate.syoMaukkaasti(kortti));

        System.out.println(kassapaate.kassassaRahaa());

        assertEquals(kortti.saldo(), saldoOstoksenJalkeen);
        assertEquals(kassapaate.maukkaitaLounaitaMyyty(), 1);
        assertEquals(kassapaate.kassassaRahaa(), kassa);

        assertFalse(kassapaate.syoMaukkaasti(kortti));
        assertEquals(kortti.saldo(), saldoOstoksenJalkeen);
        assertEquals(kassapaate.kassassaRahaa(), kassa);
    }

    @Test
    public void lataaKorttia() {
        int kassa = kassapaate.kassassaRahaa();
        resetoiKortti(0);
        int lataus = 200;
        kassapaate.lataaRahaaKortille(kortti, 200);
        assertEquals(kassapaate.kassassaRahaa(), kassa + lataus);
        assertEquals(kortti.saldo(), lataus);

        kassapaate.lataaRahaaKortille(kortti, -1);
        assertEquals(kassapaate.kassassaRahaa(), kassa + lataus);
        assertEquals(kortti.saldo(), lataus);
    }

    private void resetoiKortti(int raha) {
        kortti = new Maksukortti(raha);
    }

}
