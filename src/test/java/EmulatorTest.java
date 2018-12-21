import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmulatorTest {

    Emulator testEmulator = new Emulator(new GuiController());

    @Test
    public void getUuid() {
        testEmulator.setUuid("emulatortest");
        assertEquals("emulatortest", testEmulator.getUuid());

        testEmulator.setUuid("testEmulator test");
        assertEquals("testEmulator test", testEmulator.getUuid());
    }

    @Test
    public void urlEncode() {
        assertEquals("testEmulator+test", testEmulator.urlEncode("testEmulator test"));
        assertEquals("%23emulatortest%21", testEmulator.urlEncode("#emulatortest!"));
    }

    @Test
    public void formQueryStringForDevice() {
        testEmulator.setUuid("emulatortest");
        assertEquals("label=emulator+test&zwave_class_generic=thermostat123&thingUID=123&serial=emulatortest&undefined=", testEmulator.formQueryStringForDevice("emulator test", "thermostat123", "123"));
    }
}