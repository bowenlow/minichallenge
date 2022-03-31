package com.challenge.project;

import java.util.List;

public class ResponseMessage {
    private String message;
    private int success;
    private List results;

    public ResponseMessage(String m, int s,List r)
    {
        this.message = m;
        this.success = s;
        this.results = r;
    }
    public String getMessage() {
        return message;
    }
    public int getSuccess() { return success; }
    public List getResults() { return results; }
    public void setMessage(String message) {
        this.message = message;
    }

}
