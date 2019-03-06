package ru.stqa.pft.soap;

import net.webservicex.GeoIP;
import net.webservicex.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIPServiceTests {

    @Test
    public void testMyIP(){

        // валится, потому что wsdl к несуществующему сервису
        // найти существующий сервис и перегенерить классы командой "gradlew wsdl2java"
        GeoIP geoIP = new GeoIPService().getGeoIPServiceSoap12().getGeoIP("95.79.136.89");
        assertEquals(geoIP.getCountryCode(), "RUS");

    }
}
