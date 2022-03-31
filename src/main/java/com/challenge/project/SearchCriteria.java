package com.challenge.project;

public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;

    public SearchCriteria(String k, String op, Object v){
        key = k;
        operation = op;
        value = v;
    }
    public String getOperation() {
        return operation;
    }

    public String getKey(){
        return key;
    }

    public Object getValue() {
        return value;
    }
}
