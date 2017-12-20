package se.devcode.csign.api.converter;

@FunctionalInterface
public interface Converter<F,T> {
    T convert(F from);
}
