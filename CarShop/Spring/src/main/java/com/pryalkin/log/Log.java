package com.pryalkin.log;

public class Log {

    private Long start;
    private Long end;
    private Long completed;

    public Log() {
    }

    public void setStart() {
        this.start =  System. currentTimeMillis();
    }

    public void setEnd() {
        this.end = System. currentTimeMillis();
    }

    public void getCompleted(String method) {
        this.completed = end - start;
        System.out.println("Метод " + method + " был выполнен за " + completed + " мс");
    }

}
