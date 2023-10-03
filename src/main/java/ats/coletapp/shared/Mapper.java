package ats.coletapp.shared;

public interface Mapper<S, T> {
    T map(S source);
}
