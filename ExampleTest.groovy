import Example

class ExampleTest extends GroovyTestCase {
    void testInitialiserWithoutArg() {
        def object = new Example()
        assertEquals(object.message, "placeholder")
    }

    void testInitialiserWithArg() {
        def object = new Example("test")
        assertEquals(object.message, "test")
    }
}
