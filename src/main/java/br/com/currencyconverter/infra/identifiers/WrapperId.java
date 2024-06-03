package br.com.currencyconverter.infra.identifiers;

import java.io.Serial;
import java.io.Serializable;
import java.util.StringJoiner;

import static java.util.Objects.isNull;

public abstract class WrapperId<T extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 7539620175259666485L;

    public static final String ATTRIBUTE = "id";

    public abstract T getId();

    public String asString() {
        return isNull(getId()) ? null : getId().toString();
    }

    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("id=" + getId())
                .toString();
    }
}
