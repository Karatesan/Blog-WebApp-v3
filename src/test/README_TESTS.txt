@Mock

@Mock is provided by the Mockito framework. It creates a mock instance of the field it annotates. This is typically used in unit tests to mock the dependencies of the class under test.
Usage

    Scope: Mockito mocks are used within the scope of the test class where they are defined.
    Purpose: Mock dependencies to isolate the class under test.
    Behavior: When a method of a mocked object is called, Mockito can be configured to return predefined results.

@InjectMocks

@InjectMocks is also provided by the Mockito framework. It is used to automatically inject mock objects (annotated with @Mock) into the class under test. This helps in setting up the object graph for the class under test.
Usage

    Scope: Used within the test class to set up the object under test.
    Purpose: Automatically injects all mocks marked with @Mock into the class annotated with @InjectMocks.
    Behavior: Simplifies the setup of the class under test by automatically injecting its dependencies.

@ExtendWith(SpringExtension.class)

    Purpose: Similar to @RunWith(SpringRunner.class), but used with JUnit 5 (Jupiter). It integrates the Spring TestContext Framework into JUnit 5.
    Usage: Use this annotation at the class level when you need to test your Spring Boot application with JUnit 5.