package br.com.currencyconverter.infra.identifiers;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.Serial;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Tag("ut")
class IdentifiersTest {

    private static final String RAW_UUID = "623b9913-494d-4aff-973b-2edc0137523b";

    public static List<Class<? extends WrapperId<?>>> identifiers() {
        return List.of(
                TransactionConversionId.class,
                UserId.class,
                UserRoleId.class
        );
    }

    @ParameterizedTest
    @MethodSource("identifiers")
    void generate(Class<? extends WrapperId<?>> klass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = klass.getMethod("generate");

        WrapperId<?> id = (WrapperId<?>) method.invoke(null);

        assertNotNull(id);
        assertEquals(klass, id.getClass());
    }

    @ParameterizedTest
    @MethodSource("identifiers")
    void from(Class<? extends WrapperId<?>> klass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = klass.getMethod("from", String.class);

        WrapperId<?> id = (WrapperId<?>) method.invoke(null, RAW_UUID);

        assertNotNull(id);
        assertEquals(klass, id.getClass());
        assertEquals(RAW_UUID, id.asString());
    }

    @ParameterizedTest
    @MethodSource("identifiers")
    void hash(Class<? extends WrapperId<?>> klass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method generateMethod = klass.getMethod("generate");

        WrapperId<?> firstId = (WrapperId<?>) generateMethod.invoke(null);
        WrapperId<?> secondId = (WrapperId<?>) generateMethod.invoke(null);

        Method fromMethod = klass.getMethod("from", String.class);
        WrapperId<?> thirdId = (WrapperId<?>) fromMethod.invoke(null, firstId.asString());


        assertNotEquals(firstId.hashCode(), secondId.hashCode());
        assertNotEquals(secondId.hashCode(), thirdId.hashCode());

        assertEquals(firstId.hashCode(), firstId.hashCode());
        assertEquals(secondId.hashCode(), secondId.hashCode());
        assertEquals(thirdId.hashCode(), thirdId.hashCode());

        assertEquals(firstId.hashCode(), thirdId.hashCode());
    }

    @ParameterizedTest
    @MethodSource("identifiers")
    void isEquals(Class<? extends WrapperId<?>> klass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method generateMethod = klass.getMethod("generate");

        WrapperId<?> firstId = (WrapperId<?>) generateMethod.invoke(null);
        WrapperId<?> secondId = (WrapperId<?>) generateMethod.invoke(null);

        Method fromMethod = klass.getMethod("from", String.class);
        WrapperId<?> thirdId = (WrapperId<?>) fromMethod.invoke(null, firstId.asString());

        BasicId fourthId = new BasicId(firstId.asString());
        
        assertNotNull(firstId);

        assertNotEquals(firstId, fourthId);
        assertNotEquals(fourthId, firstId);

        assertNotEquals(secondId, fourthId);
        assertNotEquals(fourthId, secondId);
        
        assertNotEquals(thirdId, fourthId);
        assertNotEquals(fourthId, thirdId);

        assertNotEquals(firstId, secondId);
        assertNotEquals(secondId, firstId);
        
        assertNotEquals(secondId, thirdId);
        assertNotEquals(thirdId, secondId);

        assertEquals(firstId, thirdId);
        assertEquals(thirdId, firstId);
    }

    private static final class BasicId extends WrapperId<UUID> {

        @Serial
        private static final long serialVersionUID = -7101667854371153567L;

        private final UUID id;

        private BasicId(String id) {
            this.id = UUID.fromString(id);
        }

        @Override
        public UUID getId() {
            return id;
        }
    }
}
